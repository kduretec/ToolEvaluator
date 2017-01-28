package org.benchmarkdp.toolevaluator.matcher;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.utils.WordErrorRate;

public class TextMatcher implements IMatcher {

	public void match(DocumentElements groundTruth, DocumentElements toolOutput) {

		String[] allText = toolOutput.getAllText();
		WordErrorRate wer = new WordErrorRate();
		wer.setNewWords(allText);
		for (int i = 0; i < groundTruth.getNumElements(); i++) {
			IElement gE = groundTruth.getElement(i);
			wer.setRefWords(gE.getTextElement().getText());
			wer.evaluate();
			int c = wer.getCorrect();
			int n = wer.getNumberOfWords();
			double perc = (double) c / n;
			
			//we match those which have more than 90% correct words
			if (perc > 0.9) {
				int startW = wer.getStartPos();
				int endW = wer.getEndPos();
				int startL = toolOutput.getWordPositions()[startW].intValue();
				int endL = toolOutput.getWordPositions()[endW].intValue();
				gE.setMatchWordPosition(startW, endW);
				gE.setMatchLinePosition(startL, endL);		
				
				// TODO add some measures to the element
			}
		}
		

	}

}
