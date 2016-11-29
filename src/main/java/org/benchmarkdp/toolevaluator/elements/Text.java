package org.benchmarkdp.toolevaluator.elements;

/**
 * Text content divided on several elements 
 * @author kresimir
 *
 */
public class Text {

	private String text; 
	
	private int nWords;
	
	private String source;
	
	private String ID; 
	
	public String getText() {
		return text;
	}
	
	public void setText(String t) {
		text = t;
	}
	
	public void setID(String id) {
		ID = id;
	}
	
	public String getID() {
		return ID;
	}
	
}
