package org.benchmarkdp.toolevaluator;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.loader.GenericLoader;
import org.benchmarkdp.toolevaluator.loader.ILoader;
import org.benchmarkdp.toolevaluator.matcher.IMatcher;
import org.benchmarkdp.toolevaluator.matcher.TextMatcherLinear;
import org.benchmarkdp.toolevaluator.measure.IMeasure;
import org.benchmarkdp.toolevaluator.output.IOutputWriter;
import org.benchmarkdp.toolevaluator.output.XMLOutputWriter;
import org.benchmarkdp.toolevaluator.tool.ITool;

public class Evaluator {

	private String documentPath;

	private ITool groundTruthTool;

	private List<ITool> tools;

	private List<IMeasure> pMeasures;

	private ILoader loader;

	private IMatcher matcher;

	private IOutputWriter output;

	public Evaluator() {
		loader = new GenericLoader();
		matcher = new TextMatcherLinear();
		output = new XMLOutputWriter();
	}

	public void setDocumentsPath(String path) {
		documentPath = path;
	}

	public void setTools(List<ITool> t) {
		tools = t;
	}

	public void setMeasures(List<IMeasure> measures) {
		pMeasures = measures;
	}

	public void setGroundTruthTool(ITool gt) {
		groundTruthTool = gt;
	}

	public void evaluate() {

		String[] testNames = getNames();
		System.out.println("In total " + testNames.length + " detected");
		long totalStart = System.nanoTime();
		int totalTestCases = testNames.length;
		int currentTestCase = 0;
		for (String testFile : testNames) {
			currentTestCase++;
			//if (testFile.compareTo("sample_45702.docx") != 0)
			//	continue;
			System.out.println("Processing TestCase " + testFile);
			long startTime = System.nanoTime();
			String testName = testFile.substring(0, testFile.lastIndexOf("."));
			String extension = testFile.substring(testFile.lastIndexOf(".") + 1, testFile.length());

			for (ITool tool : tools) {
				//System.out.println("Tool: " + tool.getToolName()); 
				if (tool.canProcess(extension)) {
					DocumentElements gtElements = loader.getGroundTruth(testName, extension, groundTruthTool);
					DocumentElements toElements = loader.getToolOutput(testName, extension, tool);

					matcher.match(gtElements, toElements);

					for (IMeasure measure : pMeasures) {
						measure.measure(gtElements, toElements);
					}

					output.save(gtElements, testName, testFile, tool.getToolName(), tool.getResultsPath());
				}
			}

			long endTime = System.nanoTime();
			double elapsedTime = ((double) endTime - startTime) / 1000000000;
			System.out.println("TestCase[" + currentTestCase + "/" + totalTestCases + "] " + testFile + " processed in "
					+ elapsedTime + " seconds");

		}

		long totalEnd = System.nanoTime();
		double totalElapsed = ((double) totalEnd - totalStart) / 1000000000;
		System.out.println("Evaluation done in " + totalElapsed + " seconds");
	}

	private String[] getNames() {
		File f = new File(documentPath);
		String[] names = f.list(testFilter);
		return names;
	}
	
	private FilenameFilter testFilter = new FilenameFilter() {
		public boolean accept (File dir, String name) {
			if (name.compareTo(".DS_Store")==0) {
				return false;
			}
			return true;
		}
	};
}
