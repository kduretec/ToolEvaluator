package org.benchmarkdp.toolevaluator.loader;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.tool.ITool;

public interface ILoader {

	public DocumentElements getGroundTruth(String testCase, String testFormat,  ITool gtTool);
	
	public DocumentElements getToolOutput(String testCase, String testFormat, ITool tool);
	
}
