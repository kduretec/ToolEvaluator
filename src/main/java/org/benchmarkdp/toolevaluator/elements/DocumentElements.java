package org.benchmarkdp.toolevaluator.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DocumentElements {

	private String source;

	private List<IElement> elements;

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
}
