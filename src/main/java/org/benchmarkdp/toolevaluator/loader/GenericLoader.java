package org.benchmarkdp.toolevaluator.loader;

import java.util.List;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.GroundTruthElement;
import org.benchmarkdp.toolevaluator.elements.Text;
import org.benchmarkdp.toolevaluator.tool.ITool;

public class GenericLoader implements ILoader {

	String testCaseGT = "";
	DocumentElements groundTruth;

	String testCaseTool = "";
	DocumentElements toolOutput;

	public GenericLoader() {

	}

	public DocumentElements getGroundTruth(String testCase, String extension, ITool gtTool) {
		if (testCase.compareTo(testCaseGT) == 0) {
			for (int i = 0; i < groundTruth.getNumElements(); i++) {
				groundTruth.getElement(i).clearMatch();
			}
		} else {
			groundTruth = gtTool.getDocumentElements(testCase, extension, "xml");
		}

		return groundTruth;
	}

	public DocumentElements getToolOutput(String testCase, String extension, ITool tool) {
		toolOutput = tool.getDocumentElements(testCase, extension, "txt");
		return toolOutput;
	}

}
