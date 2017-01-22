package org.benchmarkdp.toolevaluator;

import java.io.File;
import java.util.List;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.measure.IMeasure;
import org.benchmarkdp.toolevaluator.output.IOutputWriter;
import org.benchmarkdp.toolevaluator.output.XMLOutputWriter;
import org.benchmarkdp.toolevaluator.tool.ITool;

public class Evaluator {

	private String documentPath;

	private ITool groundTruthTool;

	private List<ITool> tools;

	private List<IMeasure> pMeasures;

	private Loader loader;

	private Matcher matcher;

	private IOutputWriter output;

	public Evaluator() {
		loader = new Loader();
		matcher = new Matcher();
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

		for (String testFile : testNames) {
			System.out.println("Processing file " + testFile);
			String testName = testFile.substring(0, testFile.lastIndexOf("."));

			for (ITool tool : tools) {
				DocumentElements gtElements = loader.getGroundTruth(testName, groundTruthTool);
				DocumentElements toElements = loader.getToolOutput(testName, tool);

				matcher.match(gtElements, toElements);

				for (IMeasure measure : pMeasures) {
					measure.measure(gtElements, toElements);
				}

				output.save(gtElements, testName, testFile, tool);

			}
		}
	}

	private String[] getNames() {
		File f = new File(documentPath);
		String[] names = f.list();
		return names;
	}
}
