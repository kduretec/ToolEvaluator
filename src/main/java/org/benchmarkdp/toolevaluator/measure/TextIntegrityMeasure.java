package org.benchmarkdp.toolevaluator.measure;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;

public class TextIntegrityMeasure implements IMeasure {

	public void measure(DocumentElements gt, DocumentElements tool) {

		int numPreserved = 0;
		int totalIntegrity = 0;
		for (int i = 0; i < gt.getNumElements(); i++) {
			IElement gtEl = gt.getElement(i);

			if (gtEl.isMatched()) {
				totalIntegrity++;
				int gtNumLines = gtEl.getTextElement().getLines().size();
				if (gtNumLines > 0) {
					int startLine = gtEl.getStartLinePosition();
					int endLine = gtEl.getEndLinePosition();
					int numLines = endLine - startLine + 1;

					if (gtNumLines == numLines) {
						gtEl.getMeasureElement().addMeasureValue("INTEGRITYPRESERVED", new Boolean(true));
						numPreserved++;
					} else {
						gtEl.getMeasureElement().addMeasureValue("INTEGRITYPRESERVED", new Boolean(false));
					}
				}
			}
		}
		
		double perc = (double) numPreserved / totalIntegrity;
		gt.addMeasure("percIntegrityPreserved", new Double(perc));

	}

}
