package org.benchmarkdp.toolevaluator.tool;

import java.io.File;
import java.util.Arrays;

import org.benchmarkdp.toolevaluator.EvaluationProc;
import org.benchmarkdp.toolevaluator.extraction.ExtractionProc;
import org.benchmarkdp.toolevaluator.tool.parser.GenericParser;
import org.benchmarkdp.toolevaluator.tool.parser.GroundTruthParser;

import benchmarkdp.datagenerator.properties.ExperimentProperties;
import benchmarkdp.datagenerator.testcase.TestCase;

public class XpdfTask extends AbstractToolTask{

	protected String tool="/home/duretec/Programs/xpdf-tools-linux-4.00/bin64/pdftotext"; 
	protected String toolName="Xpdf"; 
	protected String toolNameNice="Xpdf v4.0"; 
	
	public XpdfTask(ExperimentProperties ep, TestCase tc) {
		super(ep, tc);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ExtractionProc getExtractionProc() {

		String command = "";
		//String tool = "/home/duretec/Programs/tika-app-1.1.jar";
		command = command + tool;
		String inputFile = ep.getFullFolderPath() + tc.getGeneratedDocument();
		String outputFile = ep.getFullFolderPath() + ep.getToolOutputFolder() + "/" + toolName + "/text/";
		File oF = new File(outputFile);
		if (!oF.exists()) {
			oF.mkdirs();
		}
		outputFile = outputFile + tc.getTestCaseName() + ".txt";
		oF = new File(outputFile);
		if (oF.exists()) {
			return null;
		}
		command = command + " -layout " + inputFile + " " + outputFile;
		//System.out.println(command);
		ExtractionProc eproc = new ExtractionProc(new String[] { "bash", "-c", command }, outputFile, this);
		return eproc;
	}

	@Override
	public EvaluationProc getEvaluationProc() {

		String toolOutput = ep.getFullFolderPath() + ep.getToolOutputFolder();
		String resultsOutput = ep.getFullFolderPath() + ep.getResultsFolder();
		String groundTruth = ep.getFullFolderPath() + ep.getTextFolder();
		String outputFile = resultsOutput + "/" + toolName + "/results/" + tc.getTestCaseName() + ".xml";
		File oF = new File(outputFile);
		if (oF.exists()) {
			return null;
		}
		ITool tika = new SoftwareTool(toolNameNice, toolOutput + "/"+ toolName + "/text",
				resultsOutput + "/" + toolName + "/results", new GenericParser(), Arrays.asList("pdf"));

		String docFile = tc.getGeneratedDocument();
		docFile = docFile.substring(docFile.lastIndexOf(File.separator) + 1);
		ITool gtTool = new GroundTruthTool("GroundTruth", groundTruth, null, new GroundTruthParser());
		EvaluationProc evalProc = new EvaluationProc(docFile, tika.getToolName(), tika.getResultsPath(), gtTool,
				tika);
		return evalProc;
	}

}
