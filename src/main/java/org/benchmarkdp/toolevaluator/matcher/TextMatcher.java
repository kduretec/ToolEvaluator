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
			//System.out.println("Matching " + gE.getTextElement().getID());
			//System.out.println(" perc = " + perc + " start=" + wer.getStartPos());
			//we match those which have more than 50% correct words
			if (perc > 0.5) {
				int startW = wer.getStartPos();
				int endW = wer.getEndPos();
				int startL = toolOutput.getWordPositions()[startW].intValue();
				int endL = toolOutput.getWordPositions()[endW].intValue();
				gE.setMatchWordPosition(startW, endW);
				gE.setMatchLinePosition(startL, endL);		
//				gE.getMeasureElement().addMeasureValue("percCorr", new Double(perc));
//				gE.getMeasureElement().addMeasureValue("N", new Integer(n));
//				gE.getMeasureElement().addMeasureValue("C", new Integer(c));
//				gE.getMeasureElement().addMeasureValue("D", new Integer(wer.getDeletion()));
//				gE.getMeasureElement().addMeasureValue("I", new Integer(wer.getInsertion()));
				gE.getMeasureElement().addMeasureValue("startWord", new Integer(wer.getStartPos()));
				gE.getMeasureElement().addMeasureValue("endWord", new Integer(wer.getEndPos()));
				
				// TODO add some measures to the element
			}
		}
		

	}

}
