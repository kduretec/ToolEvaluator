package org.benchmarkdp.toolevaluator.tool.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericParser extends AbstractParser implements IParser {

	private List<String> lines;

	private List<String> allWords;

	private List<Integer> lineInfo;

	@Override
	public void parse(String text, String format) {

		lines = new ArrayList<String>();
		wordHistogram = new HashMap<String, Integer>();
		allWords = new ArrayList<String>();
		lineInfo = new ArrayList<Integer>();

		StringBuilder sbAll = new StringBuilder();
		StringBuilder word = new StringBuilder();
		StringBuilder line = new StringBuilder();

		int lineNumber = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			sbAll.append(c);
			if (Character.isAlphabetic(c)) {
				word.append(c);
				line.append(c);
			} else if (Character.isWhitespace(c)) {
				String w = word.toString();
				if (w.length() > 0) {
					allWords.add(w);
					addWordToHistogram(w);
					lineInfo.add(new Integer(lineNumber));
				}
				word = new StringBuilder();
				if (c == '\n') {
					String l = line.toString();
					if (l.length() > 0) {
						lines.add(l);
						line = new StringBuilder();
						lineNumber++;						
					}
				}
			}
		}
		// remaining stuff
		String w = word.toString();
		if (w.length() > 0) {
			allWords.add(w);
			addWordToHistogram(w);
			lineInfo.add(new Integer(lineNumber));
		}
		String l = line.toString();
		if (l.length() > 0) {
			lines.add(l);
			line = new StringBuilder();
			lineNumber++;						
		}

	}

	public List<String> getLines() {
		return lines;
	}

	public Map<String, Integer> getWordHistogram() {
		return wordHistogram;
	}

	public String[] getAllWords() {
		String tmp[] = new String[allWords.size()];
		return allWords.toArray(tmp);
	}

	public Integer[] getWordsPositions() {
		Integer tmp[] = new Integer[lineInfo.size()]; 
		return lineInfo.toArray(tmp);
	}

}
