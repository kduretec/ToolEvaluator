package org.benchmarkdp.toolevaluator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.elements.MeasureValue;
import org.benchmarkdp.toolevaluator.measure.IMeasure;
import org.benchmarkdp.toolevaluator.tool.ITool;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

				saveMeasuresXML(gtElements, testName, testFile, tool);

			}
		}
	}

	private String[] getNames() {
		File f = new File(documentPath);
		String[] names = f.list();
		return names;
	}

	private void saveMeasuresXML(DocumentElements values, String testName, String testFile, ITool tool) {
		String pathRes = tool.getResultsPath();
		File f = new File(pathRes);
		if (!f.exists()) {
			f.mkdir();
		}

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("evaluationResults");
			doc.appendChild(rootElement);
			
			Element t = doc.createElement("tool");
			t.appendChild(doc.createTextNode(tool.getToolName()));
			Element inpt = doc.createElement("input");
			inpt.appendChild(doc.createTextNode(testName));
			rootElement.appendChild(t);
			rootElement.appendChild(inpt);
			Element elResult = doc.createElement("elementResults");
			for (int i=0; i<values.getNumElements(); i++) {
				Element el = doc.createElement("element");
				IElement dEl = values.getElement(i);
				Element id = doc.createElement("ID");
				id.appendChild(doc.createTextNode(dEl.getTextElement().getID()));
				el.appendChild(id);
				Element m = getMeasuresAsElement(doc, dEl.getMeasureElement());
				el.appendChild(m);
				elResult.appendChild(el);
			}
			rootElement.appendChild(elResult);
			
			Element docResults = doc.createElement("documentResults");
			Element m = getMeasuresAsElement(doc, values.getMeasureValue());
			docResults.appendChild(m);
			rootElement.appendChild(docResults);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(pathRes + "/" + testName + ".xml"));
			
			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Element getMeasuresAsElement(Document doc, MeasureValue v)  {
		Element measures = doc.createElement("measures");
		
		Map<String, Object> mValues = v.getAllMeasures();
		for (Map.Entry<String, Object> entr : mValues.entrySet()) {
			Element tmp = doc.createElement("measure");
			tmp.setAttribute("name", entr.getKey());
			tmp.appendChild(doc.createTextNode(entr.getValue().toString()));
			measures.appendChild(tmp);
		}
		return measures;
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
