package org.benchmarkdp.toolevaluator.output;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.tool.ITool;

public interface IOutputWriter {

	public void save(DocumentElements values, String testName, String testFile, String toolName, String toolResPath);
	
}
