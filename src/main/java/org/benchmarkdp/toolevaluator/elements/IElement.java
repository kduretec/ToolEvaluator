package org.benchmarkdp.toolevaluator.elements;

public interface IElement {

	public Text getTextElement();
	
	public void clearMeasures(); 
	
	public MeasureValue getMeasureElement();
	
	public void setMatch(IElement m); 
	
	public IElement getMatch();
	
	public boolean isMatched();
	
}
