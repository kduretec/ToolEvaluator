package org.benchmarkdp.toolevaluator.tool.parser;

import java.util.List;
import java.util.Map;

import org.benchmarkdp.toolevaluator.elements.Text;

public abstract class AbstractParser implements IParser{

	protected String removeAllFormating(String input) {

		String output = input.replaceAll("\\s+", " ").trim();
		output = output.replaceAll("(\\r|\\n|\\r\\n|\\t)+", " ");
		output = output.replaceAll("\\s+", " ").trim();

		return output;

	}
	
	public List<Text> parseToTextElements(String text, String format) {
		throw new UnsupportedOperationException();
	}
	
	public void parse(String text, String format) {
		throw new UnsupportedOperationException();
	}

	public List<String> getLines() {
		throw new UnsupportedOperationException();
	}
	
	public Map<String, Integer> getWordHistogram() {
		throw new UnsupportedOperationException();
	}
	
	public String[] getAllWords() {
		throw new UnsupportedOperationException();
	}
}
