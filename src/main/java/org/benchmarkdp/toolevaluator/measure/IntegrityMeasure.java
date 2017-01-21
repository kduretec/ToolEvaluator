package org.benchmarkdp.toolevaluator.measure;

import java.util.HashMap;
import java.util.Map;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.elements.Text;
import org.benchmarkdp.toolevaluator.utils.WordErrorRate;

public class IntegrityMeasure implements IMeasure {

	public void measure(DocumentElements gt, DocumentElements tool) {
		
		for (int i = 0; i < gt.getNumElements(); i++) {
			IElement gtEl = gt.getElement(i);
			IElement matched = gtEl.getMatch();
			Map<String, Object> tmpMeasures = new HashMap<String, Object>();
			
			if (matched != null) { 
				Text gtText = gtEl.getTextElement();
				Text mText = matched.getTextElement();
				
				if (gtText.getLines().size() > 0) {
					if (gtText.getLines().size() == mText.getLines().size()) {
						tmpMeasures.put("INTEGRITYPRESERVED", new Boolean(true));
					} else {
						tmpMeasures.put("INTEGRITYPRESERVED", new Boolean(false));
					}
				}
			}
			
			gtEl.getMeasureElement().addMeasureValue(tmpMeasures);

		}
		
	}

}
