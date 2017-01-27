package org.benchmarkdp.toolevaluator.elements;

public interface IElement {

	public Text getTextElement();
	
	public void clearMeasures(); 
	
	public MeasureValue getMeasureElement();
	
	public void setMatch(IElement m); 
	
	public IElement getMatch();
	
	public boolean isMatched();
	
	public void setMatchWordPosition(int start, int end);
	
	public int getStartWordPosition();
	
	public int getEndWordPosition();
	
	public void setMatchLinePosition(int start, int end);
	
	public int getStartLinePosition();
	
	public int getEndLinePosition();
	
}
