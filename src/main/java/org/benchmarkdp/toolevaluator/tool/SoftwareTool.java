package org.benchmarkdp.toolevaluator.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.benchmarkdp.toolevaluator.elements.Text;
import org.benchmarkdp.toolevaluator.tool.parser.IParser;

public class SoftwareTool extends AbstractTool {

	private String toolName;

	private String pathToolFolder; 
	
	private IParser parser;

	public SoftwareTool() {

	}

	public SoftwareTool(String tName, String path, IParser par) {
		toolName = tName;
		parser = par;
		pathToolFolder = path; 
	}

	public String getToolName() {
		return toolName;
	}

	public List<Text> getTextElements(String testCase) {
		
		String path = pathToolFolder + "/" + testCase + ".txt";
		File f = new File(path);
		List<Text> lTxt = new ArrayList<Text>(); 
		if (f.exists()) {
			String s = readFileToString(f);
			lTxt = parser.parse(s);
		}
	
		return lTxt;
	}

}
