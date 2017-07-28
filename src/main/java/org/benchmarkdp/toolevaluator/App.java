package org.benchmarkdp.toolevaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.benchmarkdp.toolevaluator.measure.IMeasure;
import org.benchmarkdp.toolevaluator.measure.PercCorMeasure;
import org.benchmarkdp.toolevaluator.measure.TextHistogramDiffMeasure;
import org.benchmarkdp.toolevaluator.measure.TextIntegrityMeasure;
import org.benchmarkdp.toolevaluator.measure.TextOrderMeasure;
import org.benchmarkdp.toolevaluator.measure.TextWERMeasure;
import org.benchmarkdp.toolevaluator.tool.GroundTruthTool;
import org.benchmarkdp.toolevaluator.tool.ITool;
import org.benchmarkdp.toolevaluator.tool.SoftwareTool;
import org.benchmarkdp.toolevaluator.tool.parser.GenericParser;
import org.benchmarkdp.toolevaluator.tool.parser.GroundTruthParser;

/**
 * Hello world!
 *
 */
public class App {

	private Evaluator evaluator;

	//MAC path 
	private String mainPath = "/Users/kresimir/Dropbox/Work/Projects/BenchmarkDP/benchmarking/publications/JSS/Generated/";

	//Linux path 
	//private String mainPath = "/home/kresimir/Dropbox/Work/Projects/BenchmarkDP/benchmarking/publications/JSS/Generated/";
	
	private String toolOutput = mainPath + "ToolOutput";

	public App() {
		evaluator = new Evaluator();
		evaluator.setDocumentsPath(mainPath + "Documents");
		initializeMeasures();
		initializeTools();
	}

	public static void main(String[] args) {
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
		measures.add(new TextHistogramDiffMeasure());
		measures.add(new TextWERMeasure());
		measures.add(new PercCorMeasure());
		measures.add(new TextOrderMeasure());
		measures.add(new TextIntegrityMeasure());
		evaluator.setMeasures(measures);
	}

	private void initializeTools() {

		ITool gtTool = new GroundTruthTool("GroundTruth", mainPath + "GroundTruth/Text", null, new GroundTruthParser());
		evaluator.setGroundTruthTool(gtTool);

		List<ITool> tools = new ArrayList<ITool>();
		ITool tika11 = new SoftwareTool("Apache Tika v1.1", toolOutput + "/ApacheTika1_1/text",
				toolOutput + "/ApacheTika1_1/results", new GenericParser(), Arrays.asList("docx","odt","pdf"));
		ITool tika12 = new SoftwareTool("Apache Tika v1.2", toolOutput + "/ApacheTika1_2/text",
				toolOutput + "/ApacheTika1_2/results", new GenericParser(), Arrays.asList("docx","odt","pdf"));
		ITool tika113 = new SoftwareTool("Apache Tika v1.13", toolOutput + "/ApacheTika1_13/text",
				toolOutput + "/ApacheTika1_13/results", new GenericParser(), Arrays.asList("docx","odt","pdf"));
		// ITool textUtil = new SoftwareTool("TextUtil", toolOutput +
		// "/TextUtil/text", toolOutput + "/TextUtil/results",
		// new ApacheTikaParser());
		ITool docToText = new SoftwareTool("DocToText", toolOutput + "/DocToText/text",
				toolOutput + "/DocToText/results", new GenericParser(), Arrays.asList("docx","odt","pdf"));
		 ITool abiWord = new SoftwareTool("AbiWord", toolOutput +
		 "/AbiWord/text", toolOutput + "/AbiWord/results", new GenericParser(), Arrays.asList("docx","odt","pdf"));
		// ITool libreOffice = new SoftwareTool("LibreOffice", toolOutput +
		// "/LibreOffice/text", toolOutput + "/LibreOffice/results",
		// new LibreOfficeParser());
		ITool xpdf = new SoftwareTool("Xpdf", toolOutput + "/Xpdf/text", toolOutput + "/Xpdf/results",
				new GenericParser(), Arrays.asList("pdf"));
		ITool icecite = new SoftwareTool("icecite", toolOutput + "/icecite/text", toolOutput + "/icecite/results",
				new GenericParser(), Arrays.asList("pdf"));
		//ITool xpdfmain = new SoftwareTool("Xpdf", toolOutput + "/Xpdfmain/text", toolOutput + "/Xpdfmain/results",
		//		new GenericParser(), Arrays.asList("pdf"));
		// tools.add(tika11);
		// tools.add(tika12);
		// tools.add(tika113);
		// tools.add(textUtil);
		// tools.add(docToText);
		tools.add(abiWord);
		// tools.add(libreOffice);
		// tools.add(xpdf);
		//tools.add(icecite);
		 //tools.add(xpdfmain);
		evaluator.setTools(tools);
	}
}
