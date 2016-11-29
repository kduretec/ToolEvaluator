package org.benchmarkdp.toolevaluator;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.benchmarkdp.toolevaluator.measure.IMeasure;
import org.benchmarkdp.toolevaluator.measure.WERMeasure;
import org.benchmarkdp.toolevaluator.tool.ITool;
import org.benchmarkdp.toolevaluator.tool.SoftwareTool;
import org.benchmarkdp.toolevaluator.tool.parser.ApacheTikaParser;
import org.benchmarkdp.toolevaluator.tool.parser.GroundTruthParser;
import org.junit.Test;

public class EvaluatorTest {

	
	@Test
	public void Test1() {
		
		
		Evaluator evaluator = new Evaluator(); 
		
		List<ITool> tools = new ArrayList<ITool>();
		tools.add(new SoftwareTool("ApacheTika", "src/test/resources/ApacheTika/text", "src/test/resources/ApacheTika/results", new ApacheTikaParser()));
		evaluator.setTools(tools);
		
		List<IMeasure> measures = new ArrayList<IMeasure>();
		measures.add(new WERMeasure());
		evaluator.setMeasures(measures);
		
		ITool gTool = new SoftwareTool("GroundTruth", "src/test/resources/GroundTruth", null, new GroundTruthParser());
		evaluator.setGroundTruthTool(gTool);
		
		evaluator.setDocumentsPath("src/test/resources/Documents");
		
		evaluator.evaluate();
		
		File f = new File("src/test/resources/ApacheTika/results");
		assertTrue(f.exists());
		
	}
}
