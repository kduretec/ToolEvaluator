package org.benchmarkdp.toolevaluator.tool;

import java.io.File;
import java.util.List;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.GroundTruthElement;
import org.benchmarkdp.toolevaluator.elements.Text;
import org.benchmarkdp.toolevaluator.elements.ToolElement;
import org.benchmarkdp.toolevaluator.tool.parser.IParser;

public class GroundTruthTool extends SoftwareTool{

	private String toolName;

	private String pathToolFolder; 
	
	private String pathResults;
	
	private IParser parser;

	public GroundTruthTool() {

	}

	public GroundTruthTool(String tName, String pathText, String resultsPath, IParser par) {
		toolName = tName;
		parser = par;
		pathToolFolder = pathText;
		pathResults = resultsPath;
	}
	
	public boolean canProcess(String format) {
		if (format.compareTo("xml")==0) {
			return true;
		}
		return false;
	}
	
	public DocumentElements getDocumentElements(String testCase, String testFormat, String fileExtension) {
		
		DocumentElements toolOutput = new DocumentElements();
		String path = pathToolFolder + "/" + testCase + "." + fileExtension;
		File f = new File(path);
		if (f.exists()) {
			String s = readFileToString(f);
			List<Text> txtEl = parser.parseToTextElements(s, testFormat);
			for (Text t : txtEl) {
				toolOutput.addElement(new GroundTruthElement(t));
			}		
			toolOutput.setWordHistogram(parser.getWordHistogram());		
		}
		
		return toolOutput;
	}
	
}
