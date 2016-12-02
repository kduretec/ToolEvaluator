package org.benchmarkdp.toolevaluator.measure;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;

public class PercCorMeasure implements IMeasure {

	public void measure(DocumentElements gt, DocumentElements tool) {

		int correct = 0;
		int total = 0;
		for (int i = 0; i < gt.getNumElements(); i++) {
			IElement el = gt.getElement(i);
			Integer c = (Integer) el.getMeasureElement().getMeasureValue("WER_C");
			Integer n = (Integer) el.getMeasureElement().getMeasureValue("WER_N");
			correct += c.intValue();
			total += n.intValue();
		}

		double perc = (double) correct / total;
		gt.getMeasureValue().addMeasureValue("percCorrect", new Double(perc));

	}

}
