package org.benchmarkdp.toolevaluator;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.utils.WordErrorRate;

public class Matcher {

	public void match(DocumentElements groundTruth, DocumentElements toolOutput) {

		for (int i = 0; i < groundTruth.getNumElements(); i++) {
			IElement gE = groundTruth.getElement(i);

			IElement bestMatch = null;
			int bestCorr = 0;
			for (int j = 0; j < toolOutput.getNumElements(); j++) {
				IElement tE = toolOutput.getElement(j);
				if (!tE.isMatched()) {
					WordErrorRate wer = new WordErrorRate(gE.getTextElement().getText(), tE.getTextElement().getText());
					wer.evaluate();
					int correct = wer.getCorrect();
					int num = wer.getNumberOfWords();
					double perc = (double) correct / num;
					if (perc > 0.9) {
						bestCorr = correct;
						bestMatch = tE;
						break;
					}
					if (correct > bestCorr) {
						bestCorr = correct;
						bestMatch = tE;
					}
				}
			}

			if (bestMatch != null) {
				gE.setMatch(bestMatch);
				bestMatch.setMatch(gE);

			}

		}
	}
}
