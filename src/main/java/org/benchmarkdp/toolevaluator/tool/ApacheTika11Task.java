package org.benchmarkdp.toolevaluator.tool;

import java.io.File;

import org.benchmarkdp.toolevaluator.extraction.ExtractionProc;

import benchmarkdp.datagenerator.properties.ExperimentProperties;
import benchmarkdp.datagenerator.testcase.TestCase;

public class ApacheTika11Task extends AbstractToolTask {

	
	
	public ApacheTika11Task(ExperimentProperties ep, TestCase tc) {
		super(ep, tc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ExtractionProc getExtractionProc() {

		String toolName = "ApacheTika_1_1";
		String command = "java -jar ";
		String tool = "/home/duretec/Programs/tika-app-1.1.jar";
		command = command + tool; 
		String inputFile = ep.getFullFolderPath() + tc.getGeneratedDocument();
		String outputFile = ep.getFullFolderPath() + ep.getToolOutputFolder() + "/" + toolName + "/";
		File oF = new File(outputFile);
		if (!oF.exists()) {
			oF.mkdirs();
		}
		outputFile = outputFile + tc.getTestCaseName() + ".txt";
		oF = new File(outputFile);
		if (oF.exists()) {
			return null;
		}
		command = command + " -t " + inputFile + " > " + outputFile; 
		ExtractionProc eproc = new ExtractionProc(new String[] {"bash", "-c", command}, outputFile, this);
		return eproc;
	}
}
