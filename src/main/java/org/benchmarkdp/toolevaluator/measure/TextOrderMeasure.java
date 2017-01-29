package org.benchmarkdp.toolevaluator.measure;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;

public class TextOrderMeasure implements IMeasure {

	public void measure(DocumentElements gt, DocumentElements tool) {
		int missing = 0;
		boolean orderPreserved = true;
		int currentStart = 0;
		for (int i = 0; i < gt.getNumElements(); i++) {
			IElement gtEl = gt.getElement(i);
			
			if (gtEl.isMatched()) {
				int start = gtEl.getStartWordPosition();
				if (start < currentStart) {
					orderPreserved = false;
				} 
				currentStart = start;
			} else {
				missing++;
			}
		}
		double missingPerc = (double)missing/gt.getNumElements();
		
		gt.addMeasure("missingPerc", new Double(missingPerc));
		gt.addMeasure("orderPreserved", new Boolean(orderPreserved));

	}

}
