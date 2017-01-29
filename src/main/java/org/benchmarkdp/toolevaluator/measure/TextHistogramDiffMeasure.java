package org.benchmarkdp.toolevaluator.measure;

import java.util.Map;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;

public class TextHistogramDiffMeasure implements IMeasure {

	public void measure(DocumentElements gt, DocumentElements tool) {

		Map<String, Integer> histGT = gt.getWordHistogram();
		Map<String, Integer> histTool = tool.getWordHistogram();

		int gtDiffWords = histGT.size();
		int toolDiffWords = histTool.size();

		int diff = gtDiffWords - toolDiffWords;

		gt.addMeasure("HIST_DIFF", new Integer(diff));

	}

}
