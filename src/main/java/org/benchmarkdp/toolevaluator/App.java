package org.benchmarkdp.toolevaluator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.benchmarkdp.toolevaluator.measure.IMeasure;
import org.benchmarkdp.toolevaluator.measure.PercCorMeasure;
import org.benchmarkdp.toolevaluator.measure.TextIntegrityMeasure;
import org.benchmarkdp.toolevaluator.measure.TextOrderMeasure;
import org.benchmarkdp.toolevaluator.measure.TextWERLinearMeasure;
import org.benchmarkdp.toolevaluator.tool.GroundTruthTool;
import org.benchmarkdp.toolevaluator.tool.ITool;
import org.benchmarkdp.toolevaluator.tool.IToolTask;
import org.benchmarkdp.toolevaluator.tool.SoftwareTool;
import org.benchmarkdp.toolevaluator.tool.parser.GenericParser;
import org.benchmarkdp.toolevaluator.tool.parser.GroundTruthParser;
import org.benchmarkdp.toolevaluator.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import benchmarkdp.datagenerator.generator.utils.ZipUtil;
import benchmarkdp.datagenerator.properties.ExperimentProperties;
import benchmarkdp.datagenerator.properties.PropertiesHandler;
import benchmarkdp.datagenerator.testcase.TestCaseContainer;
import benchmarkdp.datagenerator.testcase.TestCaseHandler;

/**
 * Main class
 *
 */
public class App {

	private static Logger log = LoggerFactory.getLogger(App.class);

	private static String mainFolder = "/home/duretec/Experiments/";
	private static String FolderIn = mainFolder + "TaskIn/";
	private static String FolderOut = mainFolder + "TaskOut/";

	private Options options;

	// private EvaluatorParallel evaluator;

	public App() {
		initializeCMD();
		// evaluator = new EvaluatorParallel();
		// evaluator.setDocumentsPath(Utils.mainPath + Utils.experimentName +
		// "/Documents");
		// initializeMeasures();
		// initializeTools();
	}

	public static void main(String[] args) {

		/*
		 * if (args.length == 3) { System.out.println("Setting new parameters "
		 * + args[0] + " " + args[1] + " " + args[2]); Utils.mainPath = args[0];
		 * Utils.experimentName = args[1]; Utils.numberOfProcessor =
		 * Integer.parseInt(args[2]);
		 * 
		 * Utils.toolOutput = Utils.mainPath + Utils.experimentName +
		 * "/ToolOutput"; Utils.groundTruthPath = Utils.mainPath +
		 * Utils.experimentName + "/GroundTruth/Text"; Utils.resultsOutput =
		 * Utils.mainPath + Utils.experimentName + "/Results/Tools";
		 * 
		 * }
		 */

		App application = new App();
		application.run(args);
	}

	public void run(String[] args) {
		log.info("Starting the app");

		CommandLine cmd = parseArgs(args);

		String experimentName = cmd.getOptionValue("e");
		String pp = prepareExperiment(experimentName);

		if (pp != null) {
			PropertiesHandler ph = new PropertiesHandler();
			ExperimentProperties pr = ph.loadProperties(pp);
			log.info("Experiment name " + pr.getExperimentName());
			log.info("Folder " + pr.getFullFolderPath());
			log.info("Experiment state " + pr.getExperimentState());
			String state = pr.getExperimentState();
			TestCaseContainer tCC = null;
			TestCaseHandler tch = new TestCaseHandler();
			tCC = tch.load(pr, false);
			log.info("Loaded " + tCC.getTestCases().size() + " test cases");
			EvaluatorService eService = new EvaluatorService();
			if (cmd.hasOption("p")) {
				int numProc = Integer.parseInt(cmd.getOptionValue("p"));
				eService.setNumProc(numProc);
			}
			ToolTaskLoader toolLoader = new ToolTaskLoader();
			List<IToolTask> tasks = toolLoader.getTasks(pr, tCC);
			log.info("Task loaded " + tasks.size());
			for (Iterator<IToolTask> tskIterator = tasks.iterator(); tskIterator.hasNext();) {
				IToolTask tsk = tskIterator.next();
				Runnable runT = null;
				if (cmd.hasOption("t")) {
					runT = tsk.getExtractionProc();
				} else {
					runT = tsk.getEvaluationProc();
				}
				if (runT == null) {
					tskIterator.remove();
				} else {
					eService.addRunnable(runT);
				}
			}
			eService.execute();
		} else {
			log.warn("Unknown experiment, EXITING");
		}
	}

	private void initializeCMD() {
		options = new Options();

		Option experimentName = Option.builder("e").hasArg(true).desc("experiment name").build();
		Option procNumber = Option.builder("p").hasArg(true).desc("number of processes").build();
		Option textExtraction = Option.builder("t").hasArg(false).desc("extrcat text").build();
		options.addOption(experimentName);
		options.addOption(procNumber);
		options.addOption(textExtraction);
	}

	private CommandLine parseArgs(String[] args) {
		CommandLineParser cmdLineParser = new DefaultParser();
		CommandLine commandLine = null;
		try {
			commandLine = cmdLineParser.parse(options, args);
		} catch (ParseException parseException) {
			log.info("ERROR: Unable to parse command-line arguments " + Arrays.toString(args) + " due to: "
					+ parseException);
		}
		return commandLine;
	}

	private String prepareExperiment(String experimentName) {

		String pathMExp = mainFolder + "tmp/" + experimentName;
		String propPath = pathMExp + "/properties.xml";
		File propFile = new File(propPath);
		if (propFile.exists()) {
			log.info("Experiment folder already prepared");
			return propPath;
		}
		String zipPath = FolderIn + experimentName + ".zip";
		File zipFile = new File(zipPath);
		if (zipFile.exists()) {
			try {
				String pathM = mainFolder + "tmp/";
				ZipUtil.unzipFile(zipPath, pathM);
				File fExp = new File(pathMExp);
				/*
				 * if (fExp.exists()) { File tO = new File(pathMExp +
				 * "/ToolOutput/"); tO.mkdir(); File res = new File(pathMExp +
				 * "/Results/"); res.mkdir(); }
				 */

				propFile = new File(propPath);
				if (propFile.exists()) {
					log.info("Experiment folder prepared");
					return propPath;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
			log.info("ZIP file " + zipFile.getAbsolutePath() + " not found");
			return null;
		}
	}

	/*
	 * private void initializeMeasures() { List<IMeasure> measures = new
	 * ArrayList<IMeasure>(); // measures.add(new WERMeasure()); //
	 * measures.add(new PercCorMeasure()); // measures.add(new OrderMeasure());
	 * // measures.add(new IntegrityMeasure()); // measures.add(new
	 * TextHistogramDiffMeasure()); measures.add(new TextWERLinearMeasure());
	 * measures.add(new PercCorMeasure()); measures.add(new TextOrderMeasure());
	 * measures.add(new TextIntegrityMeasure());
	 * evaluator.setMeasures(measures); }
	 */
	/*
	 * private void initializeTools() {
	 * 
	 * ITool gtTool = new GroundTruthTool("GroundTruth", Utils.groundTruthPath,
	 * null, new GroundTruthParser()); evaluator.setGroundTruthTool(gtTool);
	 * 
	 * List<ITool> tools = new ArrayList<ITool>(); ITool tika11 = new
	 * SoftwareTool("Apache Tika v1.1", Utils.toolOutput +
	 * "/ApacheTika1_1/text", Utils.resultsOutput + "/ApacheTika1_1/results",
	 * new GenericParser(), Arrays.asList("docx", "odt", "pdf")); ITool tika12 =
	 * new SoftwareTool("Apache Tika v1.2", Utils.toolOutput +
	 * "/ApacheTika1_2/text", Utils.resultsOutput + "/ApacheTika1_2/results",
	 * new GenericParser(), Arrays.asList("docx", "odt", "pdf")); ITool tika113
	 * = new SoftwareTool("Apache Tika v1.13", Utils.toolOutput +
	 * "/ApacheTika1_13/text", Utils.resultsOutput + "/ApacheTika1_13/results",
	 * new GenericParser(), Arrays.asList("docx", "odt", "pdf")); // ITool
	 * textUtil = new SoftwareTool("TextUtil", toolOutput + // "/TextUtil/text",
	 * toolOutput + "/TextUtil/results", // new ApacheTikaParser()); ITool
	 * docToText = new SoftwareTool("DocToText", Utils.toolOutput +
	 * "/DocToText/text", Utils.resultsOutput + "/DocToText/results", new
	 * GenericParser(), Arrays.asList("docx", "odt", "pdf")); ITool abiWord =
	 * new SoftwareTool("AbiWord", Utils.toolOutput + "/AbiWord/text",
	 * Utils.resultsOutput + "/AbiWord/results", new GenericParser(),
	 * Arrays.asList("docx", "odt", "pdf")); // ITool libreOffice = new
	 * SoftwareTool("LibreOffice", toolOutput + // "/LibreOffice/text",
	 * toolOutput + "/LibreOffice/results", // new LibreOfficeParser()); ITool
	 * xpdf = new SoftwareTool("Xpdf", Utils.toolOutput + "/Xpdf/text",
	 * Utils.resultsOutput + "/Xpdf/results", new GenericParser(),
	 * Arrays.asList("pdf")); ITool icecite = new SoftwareTool("icecite",
	 * Utils.toolOutput + "/icecite/text", Utils.resultsOutput +
	 * "/icecite/results", new GenericParser(), Arrays.asList("pdf")); // ITool
	 * xpdfmain = new SoftwareTool("Xpdf", toolOutput + // "/Xpdfmain/text",
	 * toolOutput + "/Xpdfmain/results", // new GenericParser(),
	 * Arrays.asList("pdf")); tools.add(tika11); tools.add(tika12);
	 * tools.add(tika113); // tools.add(textUtil); tools.add(docToText);
	 * tools.add(abiWord); // tools.add(libreOffice); // tools.add(xpdf); //
	 * tools.add(icecite); // tools.add(xpdfmain); evaluator.setTools(tools); }
	 */
}
