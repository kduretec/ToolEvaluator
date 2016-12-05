package org.benchmarkdp.toolevaluator.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.tool.ITool;

public class TextOutputWriter implements IOutputWriter{

	public void save(DocumentElements values, String testName, String testFile, ITool tool) {
		String pathRes = tool.getResultsPath();
		File f = new File(pathRes);
		if (!f.exists()) {
			f.mkdir();
		}
		f = new File(pathRes + "/" + testName + "-res.txt");
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

		f = new File(pathRes + "/" + testName + "-resAll.txt");
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
