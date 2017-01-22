package org.benchmarkdp.toolevaluator;

import java.util.ArrayList;
import java.util.List;

import org.benchmarkdp.toolevaluator.measure.IMeasure;
import org.benchmarkdp.toolevaluator.measure.IntegrityMeasure;
import org.benchmarkdp.toolevaluator.measure.OrderMeasure;
import org.benchmarkdp.toolevaluator.measure.PercCorMeasure;
import org.benchmarkdp.toolevaluator.measure.WERMeasure;
import org.benchmarkdp.toolevaluator.tool.ITool;
import org.benchmarkdp.toolevaluator.tool.SoftwareTool;
import org.benchmarkdp.toolevaluator.tool.parser.ApacheTikaParser;
import org.benchmarkdp.toolevaluator.tool.parser.GroundTruthParser;

/**
 * Hello world!
 *
 */
public class App {

	private Evaluator evaluator;

	private String mainPath = "/Users/kresimir/Dropbox/Work/Projects/BenchmarkDP/benchmarking/publications/JSS/Generated/";

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
		measures.add(new WERMeasure());
		measures.add(new PercCorMeasure());
		measures.add(new OrderMeasure());
		measures.add(new IntegrityMeasure());
		evaluator.setMeasures(measures);
	}

	private void initializeTools() {

		ITool gtTool = new SoftwareTool("GroundTruth", mainPath + "GroundTruth/Text", null, new GroundTruthParser());
		evaluator.setGroundTruthTool(gtTool);

		List<ITool> tools = new ArrayList<ITool>();
		ITool tika11 = new SoftwareTool("ApacheTika_11", toolOutput + "/ApacheTika1_1/text",
				toolOutput + "/ApacheTika1_1/results", new ApacheTikaParser());
//		ITool tika12 = new SoftwareTool("ApacheTika_12", toolOutput + "/ApacheTika1_2/text",
//				toolOutput + "/ApacheTika1_2/results", new ApacheTikaParser());
//		ITool tika113 = new SoftwareTool("ApacheTika_113", toolOutput + "/ApacheTika1_13/text",
//				toolOutput + "/ApacheTika1_13/results", new ApacheTikaParser());
//		ITool textUtil = new SoftwareTool("TextUtil", toolOutput + "/TextUtil/text", toolOutput + "/TextUtil/results",
//				new ApacheTikaParser());
//		ITool docToText = new SoftwareTool("DocToText", toolOutput + "/DocToText/text",
//				toolOutput + "/DocToText/results", new DocToTextParser());
//		ITool abiWord = new SoftwareTool("AbiWord", toolOutput + "/AbiWord/text", toolOutput + "/AbiWord/results",
//				new AbiWordParser());
//		ITool libreOffice = new SoftwareTool("LibreOffice", toolOutput + "/LibreOffice/text", toolOutput + "/LibreOffice/results",
//				new LibreOfficeParser());
		tools.add(tika11);
//		tools.add(tika12);
//		tools.add(tika113);
//		tools.add(textUtil);
//		tools.add(docToText);
//		tools.add(abiWord);
//		tools.add(libreOffice);
		evaluator.setTools(tools);
	}
}
