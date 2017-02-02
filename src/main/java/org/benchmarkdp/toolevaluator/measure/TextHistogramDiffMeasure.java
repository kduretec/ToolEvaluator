package org.benchmarkdp.toolevaluator.measure;

import java.util.Map;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;

public class TextHistogramDiffMeasure implements IMeasure {

	public void measure(DocumentElements gt, DocumentElements tool) {

		Map<String, Integer> histGT = gt.getWordHistogram();
		Map<String, Integer> histTool = tool.getWordHistogram();

		int gtDiffWords = histGT.size();
		int toolDiffWords = histTool.size();

		int wordDiff = 0;

		for (Map.Entry<String, Integer> ent : histGT.entrySet()) {
			String word = ent.getKey();
			int gtValue = ent.getValue().intValue();

			if (histTool.containsKey(word)) {
				int toolValue = histTool.get(word).intValue();
				wordDiff += toolValue < gtValue ? gtValue - toolValue : 0;
			} else {
				wordDiff += gtValue;
			}
		}
		gt.addMeasure("wordDiff", new Integer(wordDiff));

	}

}
