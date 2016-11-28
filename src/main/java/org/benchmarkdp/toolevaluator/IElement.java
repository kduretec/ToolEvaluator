package org.benchmarkdp.toolevaluator;

public interface IElement {

	public Text getTextElement();
	
	public MeasureValue getMeasureElement();
	
	public void setMatch(IElement m); 
	
	public IElement getMatch();
	
	public boolean isMatched();
	
}
