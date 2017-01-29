package org.benchmarkdp.toolevaluator.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DocumentElements {

	private String source;

	private String[] allText = null;

	private Integer[] wordPositions = null;

	private List<String> lines = null;

	private Map<String, Integer> wordHistogram;

	private List<IElement> elements = null;

	private MeasureValue documentMeasures;

	public DocumentElements() {
		elements = new ArrayList<IElement>();
		documentMeasures = new MeasureValue();
	}

	public void setSource(String s) {
		source = s;
	}

	public String getSource() {
		return source;
	}

	public void addElement(IElement e) {
		elements.add(e);
	}

	public void addElement(List<IElement> l) {
		elements.addAll(l);
	}

	public int getNumElements() {
		return elements.size();
	}

	public IElement getElement(int i) {
		return elements.get(i);
	}

	public void addMeasure(String name, Object value) {
		documentMeasures.addMeasureValue(name, value);
	}

	public void addMeasure(Map<String, Object> measures) {
		documentMeasures.addMeasureValue(measures);
	}

	public MeasureValue getMeasureValue() {
		return documentMeasures;
	}

	public String[] getAllText() {
		return allText;
	}

	public void setAllText(String[] allText) {
		this.allText = allText;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public Map<String, Integer> getWordHistogram() {
		return wordHistogram;
	}

	public void setWordHistogram(Map<String, Integer> wordHistogram) {
		this.wordHistogram = wordHistogram;
	}

	public Integer[] getWordPositions() {
		return wordPositions;
	}

	public void setWordPositions(Integer[] wordPositions) {
		this.wordPositions = wordPositions;
	}

	public IElement mergeElements(int startPos, int endPos) {
		if (endPos >= elements.size()) {
			endPos = elements.size() - 1;
		}
		IElement newEl = new ToolElement();
		StringBuilder newText = new StringBuilder();
		List<String> newLines = new ArrayList<String>();
		for (int i = startPos; i <= endPos; i++) {
			IElement tmp = elements.get(i);
			String txtTmp = tmp.getTextElement().getText();
			newText.append(" ");
			newText.append(txtTmp);
			newLines.add(txtTmp);
		}
		newEl.getTextElement().setText(newText.toString().trim());
		newEl.getTextElement().setLines(newLines);

		elements.set(startPos, newEl);
		int removInd = startPos + 1;
		for (int i = removInd; i <= endPos; i++) {
			elements.remove(removInd);

		}

		return newEl;
	}

	public String[] getWordsFromTo(int start, int end) {

		String[] tmp = new String[end - start + 1];
		for (int i = start, j = 0; i <= end; i++, j++) {
			tmp[j] = allText[i];
		}

		return tmp;
	}

}
