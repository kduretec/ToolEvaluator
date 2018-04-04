package org.benchmarkdp.toolevaluator.matcher;

import java.util.Arrays;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.utils.WordErrorRateLinear;

public class TextMatcherLinear implements IMatcher {

	public void match(DocumentElements groundTruth, DocumentElements toolOutput) {

		String[] allText = toolOutput.getAllText();
		int[] endWord = new int[allText.length];
		Arrays.fill(endWord, allText.length - 1);
		WordErrorRateLinear wer = new WordErrorRateLinear();
		for (int i = 0; i < groundTruth.getNumElements(); i++) {
			IElement gE = groundTruth.getElement(i);
			wer.setXWords(gE.getTextElement().getText());
			//if (!gE.getTextElement().getText().startsWith("pearlfish")) continue;
			//System.out.println(" Matching text : " + gE.getTextElement().getText());
			int pos = 0;
			int bestC = -1;
			int bestD = -1;
			int bestI = -1;
			int bestS = -1;		
			int nW = -1;
			int bestStart = -1;
			int bestEnd = -1;
			int bestPos = -1;
			while (pos < allText.length) {
				if (endWord[pos] != -1) {
					String[] currentText = Arrays.copyOfRange(allText, pos, endWord[pos] + 1);
					wer.setYWords(currentText);
					wer.evaluatePosition();
					int start = wer.getStartPos();
					int end = wer.getEndPos();
					if (start > -1 && end > -1) {
						String[] matchedText = Arrays.copyOfRange(currentText, start, end + 1);
						wer.setYWords(matchedText);
						wer.evaluateWER();
						int c = wer.getCorrect();
						if (c > bestC) {
							bestC = c;
							bestI = wer.getInsertion();
							bestD = wer.getDeletion();
							bestS = wer.getSubstitution();
							nW = wer.getNumberOfWords();
							bestStart = start + pos;
							bestEnd = end + pos;
							bestPos = pos;
							if (c == nW)
								break;
						}
					}
					pos = endWord[pos] + 1;
				} else {
					pos++;
				}
			}

			if (bestC > -1) {
				double perc = (double) bestC / nW;
				if (perc > 0.5) {
					//System.out.println("Match found start=" + bestStart + " end=" + bestEnd + " bestPos=" + bestPos);
					int startL = toolOutput.getWordPositions()[bestStart].intValue();
					int endL = toolOutput.getWordPositions()[bestEnd].intValue();
					gE.setMatchWordPosition(bestStart, bestEnd);
					gE.setMatchLinePosition(startL, endL);

//					gE.getMeasureElement().addMeasureValue("percCorr", new Double(perc));
//					gE.getMeasureElement().addMeasureValue("N", new Integer(nW));
//					gE.getMeasureElement().addMeasureValue("C", new Integer(bestC));
//					gE.getMeasureElement().addMeasureValue("D", new Integer(bestD));
//					gE.getMeasureElement().addMeasureValue("I", new Integer(bestI));
//					gE.getMeasureElement().addMeasureValue("S", new Integer(bestS));
					gE.getMeasureElement().addMeasureValue("startWord", new Integer(bestStart));
					gE.getMeasureElement().addMeasureValue("endWord", new Integer(bestEnd));
					
					Arrays.fill(endWord, bestStart, bestEnd +1, -1);
					if (bestPos < bestStart) {
						Arrays.fill(endWord, bestPos, bestStart, bestStart - 1);
					}
				} 
			} 
		}

	}

}
