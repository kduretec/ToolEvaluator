package org.benchmarkdp.toolevaluator.measure;

import java.util.List;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.utils.WordErrorRateLinear;

public class TextIntegrityMeasure implements IMeasure {

	public void measure(DocumentElements gt, DocumentElements tool) {

		int numPreserved = 0;
		int totalIntegrity = 0;
		for (int i = 0; i < gt.getNumElements(); i++) {
			IElement gtEl = gt.getElement(i);

			if (gtEl.isMatched()) {

				int gtNumLines = gtEl.getTextElement().getLines().size();
				if (gtNumLines > 0) {
					totalIntegrity++;
					int startLine = gtEl.getStartLinePosition();
					int endLine = gtEl.getEndLinePosition();
					int numLines = endLine - startLine + 1;

					if (gtNumLines == numLines) {
						List<String> toolLines = tool.getLines();
						List<String> gtLines = gtEl.getTextElement().getLines();
						int lPos = startLine;
						boolean intPreserved = true;
						for (String gtLine : gtLines) {
							String tLine = toolLines.get(lPos);
							WordErrorRateLinear err = new WordErrorRateLinear(gtLine, tLine);
							err.evaluateWER();
							int c = err.getCorrect();
							int n = err.getNumberOfWords();
							double perc = (double) c / n;
							//System.out.println("line-gt: " + gtLine + " line-tool: " + tLine);
							if (perc < 0.9) {
								intPreserved = false;
								break;
							}
							lPos++;
						}
						gtEl.getMeasureElement().addMeasureValue("layoutPreserved", new Boolean(intPreserved));
						numPreserved += intPreserved ? 1 : 0;
					} else {
						gtEl.getMeasureElement().addMeasureValue("layoutPreserved", new Boolean(false));
					}
				}
			}
		}

		double perc = (double) numPreserved / totalIntegrity;
		gt.addMeasure("percLayoutPreserved", new Double(perc));

	}

}
