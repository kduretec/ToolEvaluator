package org.benchmarkdp.toolevaluator.measure;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.utils.WordErrorRateLinear;

public class OrderMeasure implements IMeasure {

	public void measure(DocumentElements gt, DocumentElements tool) {
		String gS = getGroundTruthIDs(gt);
		String tS = getToolIDs(tool);
		WordErrorRateLinear wer = new WordErrorRateLinear(gS, tS);
		wer.evaluateWER();
		int c = wer.getCorrect();
		int n = wer.getNumberOfWords();
		double res = (double)c/(double)n;
		gt.addMeasure("percOfOrder", new Double(res));
	}

	private String getGroundTruthIDs(DocumentElements d) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < d.getNumElements(); i++) {
			IElement el = d.getElement(i);
			sb.append(el.getTextElement().getID() + " ");
		}
		return sb.toString().trim();
	}
	
	private String getToolIDs(DocumentElements t) {
		StringBuilder sb = new StringBuilder();

		int cNt = 0;
		for (int i = 0; i < t.getNumElements(); i++) {
			IElement el = t.getElement(i);
			if (el.isMatched()) {
				sb.append(el.getMatch().getTextElement().getID() + " ");				
			} else {
				cNt++;
				sb.append("New"+cNt+" ");
			}
		}
		return sb.toString().trim();
	}

}
