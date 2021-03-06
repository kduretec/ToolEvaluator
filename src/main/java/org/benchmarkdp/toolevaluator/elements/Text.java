package org.benchmarkdp.toolevaluator.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Text content divided on several elements 
 * @author kresimir
 *
 */
public class Text {

	private String text; 
	
	private List<String> lines; 
	
	private int nWords;
	
	private String source;
	
	private String ID; 
	
	private String elementType;
	
	public Text() {
		lines = new ArrayList<String>(); 
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String t) {
		text = t;
		nWords =  text.split(" ").length;
	}
	
	public void setID(String id) {
		ID = id;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getElementType() {
		return elementType;
	}
	
	public void setElementType(String et) {
		elementType = et;
	}
	
	public List<String> getLines() {
		return lines;
	}
	
	public void setLines(List<String> l) {
		lines = l;
	}
	
	public int getNumberWords() {
		return nWords;
	}
	
}
