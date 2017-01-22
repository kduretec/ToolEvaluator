package org.benchmarkdp.toolevaluator;

import java.util.List;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.utils.WordErrorRate;

public class Matcher {

	public void match(DocumentElements groundTruth, DocumentElements toolOutput) {

		// first stage of matching
		// try to match elements to elements
		for (int i = 0; i < groundTruth.getNumElements(); i++) {
			IElement gE = groundTruth.getElement(i);

			IElement bestMatch = null;
			for (int j = 0; j < toolOutput.getNumElements(); j++) {
				IElement tE = toolOutput.getElement(j);
				if (!tE.isMatched()) {
					int gnw = gE.getTextElement().getNumberWords();
					int tnw = tE.getTextElement().getNumberWords();
					if (Math.abs((double) gnw - tnw) / gnw <= 0.1) {
						if (isMatch(gE.getTextElement().getText(), tE.getTextElement().getText())) {
							bestMatch = tE;
							break;
						}
					}
				}
			}

			if (bestMatch != null) {
				gE.setMatch(bestMatch);
				bestMatch.setMatch(gE);
			}

		}

		// second stage of matching for those unmatched elements
		// trying to match lines to elements  
		for (int i = 0; i < groundTruth.getNumElements(); i++) {
			IElement gE = groundTruth.getElement(i);
			if (!gE.isMatched()) {
				List<String> lines = gE.getTextElement().getLines();

				for (int j = 0; j < toolOutput.getNumElements(); j++) {
					if (!toolOutput.getElement(j).isMatched()) {
						IElement matched = matchLinesToElements(lines, toolOutput, j);
						if (matched != null) {
							gE.setMatch(matched);
							matched.setMatch(gE);
							break;
						}
					}
				}
			}
		}

		// third stage of matching 
		// trying to match words to elements
		for (int i = 0; i < groundTruth.getNumElements(); i++) {
			IElement gE = groundTruth.getElement(i);
			if (!gE.isMatched()) {
				String[] words = gE.getTextElement().getText().split(" ");

				for (int j = 0; j < toolOutput.getNumElements(); j++) {
					if (!toolOutput.getElement(j).isMatched()) {
						IElement matched = matchWordsToElements(words, toolOutput, j);
						if (matched != null) {
							gE.setMatch(matched);
							matched.setMatch(gE);
							break;
						}
					}
				}
			}
		}
		
	}

	private boolean isMatch(String s1, String s2) {
		WordErrorRate wer = new WordErrorRate(s1, s2);
		wer.evaluate();
		int correct = wer.getCorrect();
		int num = wer.getNumberOfWords();
		double perc = (double) correct / num;
		if (perc >= 0.9) {
			return true;
		}
		return false;
	}

	private IElement matchLinesToElements(List<String> lines, DocumentElements elements, int startPos) {

		int endPos = startPos;
		for (int i = 0; i < lines.size(); i++) {
			if (endPos >= elements.getNumElements()) {
				return null;
			}
			IElement tmp = elements.getElement(endPos);
			if (isMatch(lines.get(i), tmp.getTextElement().getText())) {
				endPos = endPos + 1;
			} else {
				return null;
			}
		}
		
		IElement merged = elements.mergeElements(startPos, endPos);
		return merged;
	}
	
	private IElement matchWordsToElements(String[] words , DocumentElements elements, int startPos) {
		
		int endPos = startPos;
		for (int i = 0; i < words.length; i++) {
			if (endPos >= elements.getNumElements()) {
				return null;
			}
			IElement tmp = elements.getElement(endPos);
			if (isMatch(words[i], tmp.getTextElement().getText())) {
				endPos = endPos + 1;
			} else {
				return null;
			}
		}
		
		IElement merged = elements.mergeElements(startPos, endPos);
		return merged;
	}

}
