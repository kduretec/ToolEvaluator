package org.benchmarkdp.toolevaluator;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.loader.Loader;
import org.benchmarkdp.toolevaluator.tool.SoftwareTool;
import org.benchmarkdp.toolevaluator.tool.parser.ApacheTikaParser;
import org.benchmarkdp.toolevaluator.tool.parser.GroundTruthParser;
import org.junit.Test;

public class LoaderTest {

	@Test
	public void Test1() {
		Loader lod = new Loader();

		SoftwareTool gTool = new SoftwareTool("GroundTruth", "src/test/resources/GroundTruth", null,  new GroundTruthParser(), null);
		SoftwareTool tTool = new SoftwareTool("ApacheTika", "src/test/resources/ApacheTika/text", null, new ApacheTikaParser(), null);

		String testCase = "testCase1";

		DocumentElements gtElements = lod.getGroundTruth(testCase, "xml", gTool);
		DocumentElements tiElements = lod.getToolOutput(testCase, "pdf", tTool);
		assertTrue(gtElements.getNumElements() == 2);
		assertTrue(gtElements.getElement(0).getTextElement().getID().compareTo("12345678") == 0);
		assertTrue(gtElements.getElement(1).getTextElement().getID().compareTo("678543") == 0);

		assertTrue(tiElements.getNumElements() == 2);

	}
}
