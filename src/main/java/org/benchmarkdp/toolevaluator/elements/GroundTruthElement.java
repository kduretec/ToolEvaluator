package org.benchmarkdp.toolevaluator.elements;

/**
 * Element to hold ground truth data
 * 
 * @author kresimir
 *
 */
public class GroundTruthElement implements IElement {

	private Text textEl;

	private IElement match = null;

	private MeasureValue mEl;

	public GroundTruthElement() {
		mEl = new MeasureValue();
	}

	public GroundTruthElement(Text te) {
		this();
		textEl = te;
	}

	public Text getTextElement() {
		return textEl;
	}

	public void clearMeasures() {
		mEl = new MeasureValue();
		
	}
	
	public MeasureValue getMeasureElement() {
		return mEl;
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
