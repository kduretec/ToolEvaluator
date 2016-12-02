package org.benchmarkdp.toolevaluator.measure;

import java.util.HashMap;
import java.util.Map;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.elements.Text;
import org.benchmarkdp.toolevaluator.utils.WordErrorRate;

public class WERMeasure implements IMeasure {

	public void measure(DocumentElements gt, DocumentElements tool) {

		for (int i = 0; i < gt.getNumElements(); i++) {
			IElement gtEl = gt.getElement(i);
			IElement matched = gtEl.getMatch();
			Text gtText = gtEl.getTextElement();
			String reference = gtText.getText();
			String matchedText = "";
			if (matched != null) {
				matchedText = matched.getTextElement().getText();
			}
			WordErrorRate wer = new WordErrorRate(reference, matchedText);
			wer.evaluate();

			Map<String, Object> tmpMeasures = new HashMap<String, Object>();
			tmpMeasures.put("WER_C", new Integer(wer.getCorrect()));
			tmpMeasures.put("WER_D", new Integer(wer.getDeletion()));
			tmpMeasures.put("WER_I", new Integer(wer.getInsertion()));
			tmpMeasures.put("WER_S", new Integer(wer.getSubstitution()));
			tmpMeasures.put("WER_N", new Integer(wer.getNumberOfWords()));
			gtEl.getMeasureElement().addMeasureValue(tmpMeasures);

		}

	}

}
