package org.benchmarkdp.toolevaluator.tool;

import java.util.List;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.Text;

public interface ITool {

	public String getToolName(); 
	
	public String getTextPath();
	
	public String getResultsPath();
	
	public boolean canProcess(String format);
	
	public List<Text> getTextElements(String testCase, String testFormat, String fileExstension);
	
	public DocumentElements getDocumentElements(String testCase, String testFormat, String fileExtension);
	
}
