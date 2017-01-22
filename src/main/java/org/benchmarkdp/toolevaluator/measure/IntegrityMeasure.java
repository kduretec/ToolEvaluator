package org.benchmarkdp.toolevaluator.measure;

import java.util.HashMap;
import java.util.List;
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
						List<String> linesGT = gtText.getLines();
						List<String> linesTool = mText.getLines();
						int numCorrect = 0;
						for (int j = 0; j < linesGT.size(); j++) {
							WordErrorRate err = new WordErrorRate(linesGT.get(j), linesTool.get(j));
							int d = err.getDeletion();
							int ins = err.getInsertion();
							int sub = err.getSubstitution();
							if ((d + ins + sub) == 0) {
								numCorrect++;
							}
						}
						tmpMeasures.put("INTEGRITYPRESERVED", new Double((double)numCorrect/gtText.getLines().size()));
					} else {
						tmpMeasures.put("INTEGRITYPRESERVED", new Double(0.0));
					}
				}
			}

			gtEl.getMeasureElement().addMeasureValue(tmpMeasures);

		}

	}

}
