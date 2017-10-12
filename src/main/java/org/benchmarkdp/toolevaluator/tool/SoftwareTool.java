	package org.benchmarkdp.toolevaluator.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.Text;
import org.benchmarkdp.toolevaluator.tool.parser.IParser;

public class SoftwareTool extends AbstractTool {

	private String toolName;

	private String pathToolFolder; 
	
	private String pathResults;
	
	private IParser parser;
	
	List<String> formatsSupported;

	public SoftwareTool() {

	}

	public SoftwareTool(String tName, String pathText, String resultsPath, IParser par, List<String> fS) {
		toolName = tName;
		parser = par;
		pathToolFolder = pathText;
		pathResults = resultsPath;
		formatsSupported = fS;
	}

	public String getToolName() {
		return toolName;
	}

	public boolean canProcess(String format) {
		return formatsSupported.contains(format);
	}
	
	public List<Text> getTextElements(String testCase, String testFormat, String fileExstension) {
		
		String path = pathToolFolder + "/" + testCase + "." + fileExstension;
		//System.out.println("PATH =" + path);
		File f = new File(path);
		List<Text> lTxt = new ArrayList<Text>(); 
		if (f.exists()) {
			String s = readFileToString(f);
			lTxt = parser.parseToTextElements(s, testFormat);
		}
	
		return lTxt;
	}

	public String getTextPath() {
		return pathToolFolder;
	}

	public String getResultsPath() {
		return pathResults;
	}

	public DocumentElements getDocumentElements(String testCase, String testFormat, String fileExtension) {
	
		DocumentElements toolOutput = new DocumentElements();
		String path = pathToolFolder + "/" + testCase + "." + fileExtension;
		//System.out.println("PATH=" + path);
		File f = new File(path);
		if (f.exists()) {
			String s = readFileToString(f);
			parser.parse(s, testFormat);
			toolOutput.setAllText(parser.getAllWords());
			toolOutput.setLines(parser.getLines());
			toolOutput.setWordHistogram(parser.getWordHistogram());
			toolOutput.setWordPositions(parser.getWordsPositions());			
		} else {
			toolOutput.setAllText(null);
			toolOutput.setLines(null);
			toolOutput.setWordHistogram(null);
			toolOutput.setWordPositions(null);
		}
		
		
		return toolOutput;
	}
}
