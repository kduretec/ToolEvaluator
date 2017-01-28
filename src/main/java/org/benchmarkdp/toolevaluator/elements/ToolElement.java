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
		textEl = new Text();
	}

	public ToolElement(Text te) {
		textEl = te;
	}

	public Text getTextElement() {
		return textEl;
	}

	public void clearMatch() {
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

	public void setMatchWordPosition(int start, int end) {
		throw new UnsupportedOperationException();
	}

	public int getStartWordPosition() {
		throw new UnsupportedOperationException();
	}

	public int getEndWordPosition() {
		throw new UnsupportedOperationException();
	}

	public void setMatchLinePosition(int start, int end) {
		throw new UnsupportedOperationException();
	}

	public int getStartLinePosition() {
		throw new UnsupportedOperationException();
	}

	public int getEndLinePosition() {
		throw new UnsupportedOperationException();
	}

}
