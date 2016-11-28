package org.benchmarkdp.toolevaluator;

import java.util.List;

import org.benchmarkdp.toolevaluator.tool.ITool;

/**
 * A class responsible of creating two DocumentElements objects latter used by
 * the matching algorithm
 * 
 * @author kresimir
 *
 */
public class Loader {

	String testCaseGT = null;
	DocumentElements groundTruth;

	String testCaseTool = null;
	DocumentElements toolOutput;

	public Loader() {

	}

	public DocumentElements getGroundTruth(String testCase, ITool gtTool) {
		if (testCase.compareTo(testCaseGT) == 0) {
			for (int i = 0; i < groundTruth.getNumElements(); i++) {
				groundTruth.getElement(i).clearMeasures();
			}
		} else {
			groundTruth = new DocumentElements();
			List<Text> lT = gtTool.getTextElements(testCase);
			for (Text t: lT) {
				groundTruth.addElement(new GroundTruthElement(t));
			}
			testCaseGT = testCase;
		}
		
		return groundTruth;
	}

	public DocumentElements getToolOutput(String testCase, ITool tool) {
		if (testCase.compareTo(testCaseTool) != 0) {
			toolOutput = new DocumentElements();
			List<Text> lT = tool.getTextElements(testCase);
			for (Text t: lT) {
				toolOutput.addElement(new ToolElement(t));
			}
			testCaseTool = testCase;
		}
		
		return toolOutput;
	}

}
