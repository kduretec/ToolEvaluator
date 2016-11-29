package org.benchmarkdp.toolevaluator;

import static org.junit.Assert.*;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.tool.SoftwareTool;
import org.benchmarkdp.toolevaluator.tool.parser.ApacheTikaParser;
import org.benchmarkdp.toolevaluator.tool.parser.GroundTruthParser;
import org.junit.Test;

public class LoaderTest {

	@Test
	public void Test1() {
		Loader lod = new Loader();

		SoftwareTool gTool = new SoftwareTool("GroundTruth", "src/test/resources/GroundTruth", new GroundTruthParser());
		SoftwareTool tTool = new SoftwareTool("ApacheTika", "src/test/resources/ApacheTika", new ApacheTikaParser());

		String testCase = "testCase1";

		DocumentElements gtElements = lod.getGroundTruth(testCase, gTool);
		DocumentElements tiElements = lod.getToolOutput(testCase, tTool);
		assertTrue(gtElements.getNumElements() == 2);
		assertTrue(gtElements.getElement(0).getTextElement().getID().compareTo("12345678") == 0);
		assertTrue(gtElements.getElement(1).getTextElement().getID().compareTo("678543") == 0);

		assertTrue(tiElements.getNumElements() == 2);

	}
}
