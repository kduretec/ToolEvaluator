package org.benchmarkdp.toolevaluator;

import java.util.ArrayList;
import java.util.List;

public class DocumentElements {

	private String source;

	private List<IElement> elements;

	public DocumentElements() {
		elements = new ArrayList<IElement>();
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
}
