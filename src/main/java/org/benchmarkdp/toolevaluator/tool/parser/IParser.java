package org.benchmarkdp.toolevaluator.tool.parser;

import java.util.List;
import java.util.Map;

import org.benchmarkdp.toolevaluator.elements.Text;

/**
 * The interface for tool output parsers 
 * @author kresimir
 *
 */
public interface IParser {

	public List<Text> parseToTextElements(String text, String format);
	
	public void parse(String text, String format);
	
	public List<String> getLines();
	
	public Map<String, Integer> getWordHistogram(); 
	
	public String[] getAllWords(); 
	
	public Integer[] getWordsPositions();
	
}
