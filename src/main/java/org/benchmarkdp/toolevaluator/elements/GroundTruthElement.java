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
	
	private int startWord = -1;
	
	private int endWord = -1;
	
	private int startLine = -1;
	
	private int endLine = -1;
	
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
		return (match != null) || (startWord > -1 && endWord > -1);
	}

	public void setMatchWordPosition(int start, int end) {
		startWord = start;
		endWord = end;
	}

	public int getStartWordPosition() {
		return startWord;
	}

	public int getEndWordPosition() {
		return endWord;
	}

	public void setMatchLinePosition(int start, int end) {
		startLine = start;
		endLine = end;
	}

	public int getStartLinePosition() {
		return startLine;
	}

	public int getEndLinePosition() {
		return endLine;
	}

	
}
