package org.benchmarkdp.toolevaluator.measure;

import java.util.HashMap;
import java.util.Map;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.elements.Text;
import org.benchmarkdp.toolevaluator.utils.WordErrorRate;

public class TextWERMeasure implements IMeasure {

	public void measure(DocumentElements gt, DocumentElements tool) {
		
		for (int i = 0; i < gt.getNumElements(); i++) {
			IElement gtEl = gt.getElement(i);
			
			String gtWords = gtEl.getTextElement().getText();
			String[] toolWords = {};
			
			if (gtEl.isMatched()) {
				toolWords = tool.getWordsFromTo(gtEl.getStartWordPosition(), gtEl.getEndWordPosition());
			}
			
			WordErrorRate wer = new WordErrorRate();
			wer.setRefWords(gtWords);
			wer.setNewWords(toolWords);
			wer.evaluate();

			Map<String, Object> tmpMeasures = new HashMap<String, Object>();
			tmpMeasures.put("C", new Integer(wer.getCorrect()));
			tmpMeasures.put("D", new Integer(wer.getDeletion()));
			tmpMeasures.put("I", new Integer(wer.getInsertion()));
			tmpMeasures.put("S", new Integer(wer.getSubstitution()));
			tmpMeasures.put("N", new Integer(wer.getNumberOfWords()));
			gtEl.getMeasureElement().addMeasureValue(tmpMeasures);

		}
		
	}

}
