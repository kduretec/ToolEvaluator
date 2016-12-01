package org.benchmarkdp.toolevaluator;

import java.util.List;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.GroundTruthElement;
import org.benchmarkdp.toolevaluator.elements.Text;
import org.benchmarkdp.toolevaluator.elements.ToolElement;
import org.benchmarkdp.toolevaluator.tool.ITool;

/**
 * A class responsible of creating two DocumentElements objects latter used by
 * the matching algorithm
 * 
 * @author kresimir
 *
 */
public class Loader {

	String testCaseGT = "";
	DocumentElements groundTruth;

	String testCaseTool = "";
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
			List<Text> lT = gtTool.getTextElements(testCase, "xml");
			for (Text t : lT) {
				groundTruth.addElement(new GroundTruthElement(t));
			}
			testCaseGT = testCase;
		}

		return groundTruth;
	}

	public DocumentElements getToolOutput(String testCase, ITool tool) {
		toolOutput = new DocumentElements();
		List<Text> lT = tool.getTextElements(testCase, "txt");
		for (Text t : lT) {
			toolOutput.addElement(new ToolElement(t));
		}

		return toolOutput;
	}

}
