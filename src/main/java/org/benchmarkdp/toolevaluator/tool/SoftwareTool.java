	package org.benchmarkdp.toolevaluator.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.benchmarkdp.toolevaluator.elements.Text;
import org.benchmarkdp.toolevaluator.tool.parser.IParser;

public class SoftwareTool extends AbstractTool {

	private String toolName;

	private String pathToolFolder; 
	
	private String pathResults;
	
	private IParser parser;

	public SoftwareTool() {

	}

	public SoftwareTool(String tName, String pathText, String resultsPath, IParser par) {
		toolName = tName;
		parser = par;
		pathToolFolder = pathText;
		pathResults = resultsPath;
	}

	public String getToolName() {
		return toolName;
	}

	public List<Text> getTextElements(String testCase, String fileExstension) {
		
		String path = pathToolFolder + "/" + testCase + "." + fileExstension;
		File f = new File(path);
		List<Text> lTxt = new ArrayList<Text>(); 
		if (f.exists()) {
			String s = readFileToString(f);
			lTxt = parser.parse(s, fileExstension);
		}
	
		return lTxt;
	}

	public String getTextPath() {
		return pathToolFolder;
	}

	public String getResultsPath() {
		return pathResults;
	}

}
