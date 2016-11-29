package org.benchmarkdp.toolevaluator.elements;

/**
 * Element to hold tool output data
 * 
 * @author kresimir
 *
 */
public class ToolElement implements IElement {

	private Text textEl;

	private IElement match = null;

	public ToolElement() {

	}

	public ToolElement(Text te) {
		textEl = te;
	}

	public Text getTextElement() {
		return textEl;
	}

	public void clearMeasures() {
		throw new UnsupportedOperationException();
	}
	
	public MeasureValue getMeasureElement() {
		throw new UnsupportedOperationException();
	}

	public void setMatch(IElement m) {
		match = m;
	}

	public IElement getMatch() {
		return match;
	}

	public boolean isMatched() {
		return match != null;
	}

}
