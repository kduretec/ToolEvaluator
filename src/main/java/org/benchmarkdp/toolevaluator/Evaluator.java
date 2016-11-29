package org.benchmarkdp.toolevaluator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.measure.IMeasure;
import org.benchmarkdp.toolevaluator.tool.ITool;

public class Evaluator {

	private String documentPath;

	private ITool groundTruthTool;

	private List<ITool> tools;

	private List<IMeasure> pMeasures;

	private Loader loader;

	private Matcher matcher;

	public Evaluator() {
		loader = new Loader();
		matcher = new Matcher();
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

		for (String test : testNames) {
			test = test.substring(0, test.lastIndexOf("."));

			for (ITool tool : tools) {
				DocumentElements gtElements = loader.getGroundTruth(test, groundTruthTool);
				DocumentElements toElements = loader.getToolOutput(test, tool);

				matcher.match(gtElements, toElements);

				for (IMeasure measure : pMeasures) {
					measure.measure(gtElements, toElements);
				}
				
				saveMeasures(gtElements, test, tool);

			}
		}
	}

	private String[] getNames() {
		File f = new File(documentPath);
		String[] names = f.list();
		return names;
	}

	private void saveMeasures(DocumentElements values, String testCase, ITool tool) {
		String pathRes = tool.getResultsPath();
		File f = new File(pathRes);
		if (!f.exists()) {
			f.mkdir();
		}
		f = new File(pathRes + "/" + testCase + "-res.txt");
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f));
			bw.write("ID\tMeasure\tValue\n");
			for (int i = 0; i < values.getNumElements(); i++) {
				IElement el = values.getElement(i);
				Map<String, Object> v = el.getMeasureElement().getAllMeasures();
				String elementID = el.getTextElement().getID();
				writeToBufferedWritter(bw, elementID, v);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		f = new File(pathRes + "/" + testCase + "-resAll.txt");
		try {
			bw = new BufferedWriter(new FileWriter(f));
			Map<String, Object> v = values.getMeasureValue().getAllMeasures();
			writeToBufferedWritter(bw, null, v);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void writeToBufferedWritter(BufferedWriter bw, String elementID, Map<String, Object> values)
			throws IOException {

		for (Map.Entry<String, Object> entry : values.entrySet()) {
			if (elementID != null) {
				bw.write(elementID + "\t");
			}
			bw.write(entry.getKey() + "\t" + entry.getValue().toString() + "\n");
		}
	}
}
