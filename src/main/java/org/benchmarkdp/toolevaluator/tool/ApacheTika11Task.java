package org.benchmarkdp.toolevaluator.tool;

import java.io.File;
import java.util.Arrays;

import org.benchmarkdp.toolevaluator.EvaluationProc;
import org.benchmarkdp.toolevaluator.extraction.ExtractionProc;
import org.benchmarkdp.toolevaluator.tool.parser.GenericParser;
import org.benchmarkdp.toolevaluator.tool.parser.GroundTruthParser;
import org.benchmarkdp.toolevaluator.utils.Utils;

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
		command = command + " -t " + inputFile + " > " + outputFile;
		ExtractionProc eproc = new ExtractionProc(new String[] { "bash", "-c", command }, outputFile, this);
		return eproc;
	}

	@Override
	public EvaluationProc getEvaluationProc() {

		String toolOutput = ep.getFullFolderPath() + ep.getToolOutputFolder();
		String resultsOutput = ep.getFullFolderPath() + ep.getResultsFolder();
		ITool tika11 = new SoftwareTool("Apache Tika v1.1", toolOutput + "/ApacheTika1_1/text",
				resultsOutput + "/ApacheTika1_1/results", new GenericParser(), Arrays.asList("docx", "odt", "pdf"));

		String docFile = tc.getGeneratedDocument();
		docFile = docFile.substring(docFile.lastIndexOf(File.separator));
		ITool gtTool = new GroundTruthTool("GroundTruth", ep.getGroundTruthFolder(), null, new GroundTruthParser());
		EvaluationProc evalProc = new EvaluationProc(docFile, tika11.getToolName(), tika11.getResultsPath(), gtTool,
				tika11);
		return evalProc;
	}

}
