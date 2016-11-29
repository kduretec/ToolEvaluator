package org.benchmarkdp.toolevaluator.tool;

import java.util.List;

import org.benchmarkdp.toolevaluator.elements.Text;

public interface ITool {

	public String getToolName(); 
	
	public List<Text> getTextElements(String testCase);
	
}
