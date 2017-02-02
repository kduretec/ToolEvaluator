package org.benchmarkdp.toolevaluator.measure;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;

public class PercCorMeasure implements IMeasure {

	public void measure(DocumentElements gt, DocumentElements tool) {

		int correct = 0;
		int total = 0;
		int totalMatched = 0;
		for (int i = 0; i < gt.getNumElements(); i++) {
			IElement el = gt.getElement(i);
			if (el.isMatched()) {
				totalMatched++;
			}
			Integer c = (Integer) el.getMeasureElement().getMeasureValue("C");
			Integer n = (Integer) el.getMeasureElement().getMeasureValue("N");
			correct += c.intValue();
			total += n.intValue();
		}

		double perc = (double) correct / total;
		double percMatched = (double) totalMatched / gt.getNumElements();
		gt.addMeasure("percCorrect", new Double(perc));
		gt.addMeasure("percMatched", new Double(percMatched));

	}

}
