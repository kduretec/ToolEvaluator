package org.benchmarkdp.toolevaluator.output;

import java.io.File;
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
import org.benchmarkdp.toolevaluator.tool.ITool;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLOutputWriter implements IOutputWriter{

	public void save(DocumentElements values, String testName, String testFile, String toolName, String toolResPath) {
		
		//String pathRes = tool.getResultsPath();
		String pathRes = toolResPath;
		
		createResDir(pathRes); 

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("evaluationResults");
			doc.appendChild(rootElement);
			
			Element t = doc.createElement("tool");
			t.appendChild(doc.createTextNode(toolName));
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
	
	
	private void createResDir(String path) {
		if (path.endsWith("/")) {
			path = path.substring(0, path.length()-1);
		}
		File f = new File(path);
		if (!f.exists()) {
			path = path.substring(0, path.lastIndexOf("/"));
			createResDir(path);
			f.mkdir();
		}
	}

}
