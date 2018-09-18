package org.benchmarkdp.toolevaluator.tool.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.benchmarkdp.toolevaluator.elements.Text;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GroundTruthParser extends AbstractParser {

	private List<String> allWords;
		
	public List<Text> parseToTextElements(String text, String format) {

		text = text.trim().replaceFirst("^([\\W]+)<","<"); 
		allWords = new ArrayList<String>();
		wordHistogram = new HashMap<String, Integer>();
		
		List<Text> elements = new ArrayList<Text>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(text)));

			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.evaluate("//element", document, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				if (nodes.item(i) instanceof Element) {
					Element tmp = (Element) nodes.item(i);
					Node id = tmp.getElementsByTagName("ID").item(0);
					Node eType = tmp.getElementsByTagName("ElementType").item(0);
					Node txt = tmp.getElementsByTagName("text").item(0);
					Node lNode = tmp.getElementsByTagName("lines").item(0);
					List<String> lines = new ArrayList<String>();
					if (lNode != null) {
						NodeList lN = lNode.getChildNodes();
						for (int j = 0; j < lN.getLength(); j++) {
							Node n = lN.item(j);
							if (n.getNodeType() == Node.ELEMENT_NODE) {
								lines.add(n.getTextContent());
							}
						}
					}
					Text txtEl = new Text();
					txtEl.setID(id.getTextContent());
					txtEl.setElementType(eType.getTextContent());
					String maintext = removeAllFormating(txt.getTextContent());
					String[] wordsEl = maintext.split(" ");
					addTextToHist(wordsEl);
					allWords.addAll(Arrays.asList(wordsEl));
					txtEl.setText(maintext);
					txtEl.setLines(lines);
					elements.add(txtEl);
				}
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return elements;
	}
	
	public Map<String, Integer> getWordHistogram() {
		return wordHistogram;
	}

	public String[] getAllWords() {
		String tmp[] = new String[allWords.size()];
		return allWords.toArray(tmp);
	}
	
	private void addTextToHist(String[] words) {
		for (String w : words) {
			addWordToHistogram(w);
		}
	}

}
