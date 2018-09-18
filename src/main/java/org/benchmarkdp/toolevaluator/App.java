package org.benchmarkdp.toolevaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.benchmarkdp.toolevaluator.measure.IMeasure;
import org.benchmarkdp.toolevaluator.measure.PercCorMeasure;
import org.benchmarkdp.toolevaluator.measure.TextIntegrityMeasure;
import org.benchmarkdp.toolevaluator.measure.TextOrderMeasure;
import org.benchmarkdp.toolevaluator.measure.TextWERLinearMeasure;
import org.benchmarkdp.toolevaluator.tool.GroundTruthTool;
import org.benchmarkdp.toolevaluator.tool.ITool;
import org.benchmarkdp.toolevaluator.tool.SoftwareTool;
import org.benchmarkdp.toolevaluator.tool.parser.GenericParser;
import org.benchmarkdp.toolevaluator.tool.parser.GroundTruthParser;
import org.benchmarkdp.toolevaluator.utils.Utils;

import benchmarkdp.datagenerator.testcase.TestCase;

/**
 * Main class
 *
 */
public class App {

	private EvaluatorParallel evaluator;

	public App() {
		evaluator = new EvaluatorParallel();
		evaluator.setDocumentsPath(Utils.mainPath + Utils.experimentName + "/Documents");
		initializeMeasures();
		initializeTools();
	}

	public static void main(String[] args) {
		
		TestCase tc = new TestCase();
		tc.getFitsFile();
		if (args.length == 3) {
			System.out.println("Setting new parameters " + args[0] + " " + args[1] + " " + args[2]);
			Utils.mainPath = args[0];
			Utils.experimentName = args[1];
			Utils.numberOfProcessor = Integer.parseInt(args[2]);
			
			Utils.toolOutput = Utils.mainPath + Utils.experimentName + "/ToolOutput";
			Utils.groundTruthPath = Utils.mainPath + Utils.experimentName + "/GroundTruth/Text";
			Utils.resultsOutput = Utils.mainPath + Utils.experimentName + "/Results/Tools";
			
		}

		App application = new App();
		application.run();
	}

	public void run() {
		evaluator.evaluate();
	}

	private void initializeMeasures() {
		List<IMeasure> measures = new ArrayList<IMeasure>();
		// measures.add(new WERMeasure());
		// measures.add(new PercCorMeasure());
		// measures.add(new OrderMeasure());
		// measures.add(new IntegrityMeasure());
		// measures.add(new TextHistogramDiffMeasure());
		measures.add(new TextWERLinearMeasure());
		measures.add(new PercCorMeasure());
		measures.add(new TextOrderMeasure());
		measures.add(new TextIntegrityMeasure());
		evaluator.setMeasures(measures);
	}

	private void initializeTools() {

		ITool gtTool = new GroundTruthTool("GroundTruth", Utils.groundTruthPath, null, new GroundTruthParser());
		evaluator.setGroundTruthTool(gtTool);

		List<ITool> tools = new ArrayList<ITool>();
		ITool tika11 = new SoftwareTool("Apache Tika v1.1", Utils.toolOutput + "/ApacheTika1_1/text",
				Utils.resultsOutput + "/ApacheTika1_1/results", new GenericParser(),
				Arrays.asList("docx", "odt", "pdf"));
		ITool tika12 = new SoftwareTool("Apache Tika v1.2", Utils.toolOutput + "/ApacheTika1_2/text",
				Utils.resultsOutput + "/ApacheTika1_2/results", new GenericParser(),
				Arrays.asList("docx", "odt", "pdf"));
		ITool tika113 = new SoftwareTool("Apache Tika v1.13", Utils.toolOutput + "/ApacheTika1_13/text",
				Utils.resultsOutput + "/ApacheTika1_13/results", new GenericParser(),
				Arrays.asList("docx", "odt", "pdf"));
		// ITool textUtil = new SoftwareTool("TextUtil", toolOutput +
		// "/TextUtil/text", toolOutput + "/TextUtil/results",
		// new ApacheTikaParser());
		ITool docToText = new SoftwareTool("DocToText", Utils.toolOutput + "/DocToText/text",
				Utils.resultsOutput + "/DocToText/results", new GenericParser(), Arrays.asList("docx", "odt", "pdf"));
		ITool abiWord = new SoftwareTool("AbiWord", Utils.toolOutput + "/AbiWord/text",
				Utils.resultsOutput + "/AbiWord/results", new GenericParser(), Arrays.asList("docx", "odt", "pdf"));
		// ITool libreOffice = new SoftwareTool("LibreOffice", toolOutput +
		// "/LibreOffice/text", toolOutput + "/LibreOffice/results",
		// new LibreOfficeParser());
		ITool xpdf = new SoftwareTool("Xpdf", Utils.toolOutput + "/Xpdf/text", Utils.resultsOutput + "/Xpdf/results",
				new GenericParser(), Arrays.asList("pdf"));
		ITool icecite = new SoftwareTool("icecite", Utils.toolOutput + "/icecite/text",
				Utils.resultsOutput + "/icecite/results", new GenericParser(), Arrays.asList("pdf"));
		// ITool xpdfmain = new SoftwareTool("Xpdf", toolOutput +
		// "/Xpdfmain/text", toolOutput + "/Xpdfmain/results",
		// new GenericParser(), Arrays.asList("pdf"));
		tools.add(tika11);
		tools.add(tika12);
		tools.add(tika113);
		// tools.add(textUtil);
		tools.add(docToText);
		tools.add(abiWord);
		// tools.add(libreOffice);
		// tools.add(xpdf);
		// tools.add(icecite);
		// tools.add(xpdfmain);
		evaluator.setTools(tools);
	}
}
