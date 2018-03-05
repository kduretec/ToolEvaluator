package org.benchmarkdp.toolevaluator.utils;

/**
 * Word Error Rate of two string objects. This object return the WER value but
 * also additional values such as number of substitutions, deletions and
 * insertions.
 * 
 * This implementation brings several improvements over the one in WordErrorRate
 * class. The space requirement is linear instead of quadratic and also the
 * class differentiates between finding the start and end position and
 * calculating WER values.
 * 
 * @author kresimir
 *
 */
public class WordErrorRateLinear {

	private int deletion;

	private int substitution;

	private int insertion;

	private int correct;

	private int numberOfWords;

	private int totalOp;

	private int startPos;

	private int endPos;

	private String[] xWords;
	private String[] yWords;

	private class MatEl {
		public int totalOp;

		public int deletion;

		public int substitution;

		public int insertion;

		public int correct;

		public int bestSubs;

		public MatEl(int tO, int del, int sub, int ins, int cor, int bS) {
			totalOp = tO;
			deletion = del;
			substitution = sub;
			insertion = ins;
			correct = cor;
			bestSubs = bS;
		}

		public MatEl(MatEl me) {
			totalOp = me.totalOp;
			deletion = me.deletion;
			substitution = me.substitution;
			insertion = me.insertion;
			correct = me.correct;
			bestSubs = me.bestSubs;
		}

	};

	public WordErrorRateLinear() {

	}

	public WordErrorRateLinear(String x, String y) {
		xWords = removeAllFormating(x).split(" ");
		yWords = removeAllFormating(y).split(" ");
	}

	public WordErrorRateLinear(String[] x, String[] y) {
		xWords = x;
		yWords = y;
	}

	public void setXWords(String x) {
		xWords = removeAllFormating(x).split(" ");
	}

	public void setXWords(String[] x) {
		xWords = x;
	}

	public void setYWords(String y) {
		yWords = removeAllFormating(y).split(" ");
	}

	public void setNewWords(String[] y) {
		yWords = y;
	}

	public int getTotalOp() {
		return totalOp;
	}

	public int getInsertion() {
		return insertion;
	}

	public int getSubstitution() {
		return substitution;
	}

	public int getDeletion() {
		return deletion;
	}

	public int getCorrect() {
		return correct;
	}

	public int getNumberOfWords() {
		return numberOfWords;
	}

	public int getStartPos() {
		return startPos;
	}

	public int getEndPos() {
		return endPos;
	}

	public void evaluateWER() {

		MatEl[] results = calculateWER(xWords, yWords);
		MatEl el = results[results.length-1];
		totalOp = el.totalOp;
		deletion = el.deletion;
		substitution = el.substitution;
		insertion = el.insertion;
		correct = el.correct;
	}

	public void evaluatePosition() {

	}

	private MatEl[] calculateWER(String[] x, String[] y) {
		MatEl[] firstRow = null;
		MatEl[] secondRow = null;

		int m = x.length + 1;
		int n = y.length + 1;

		for (int i = 0; i < m; i++) {
			secondRow = new MatEl[n];
			for (int j = 0; j < n; j++) {
				if (i == 0) {
					//firstRow = new MatEl[n];
					secondRow[j] = new MatEl(j, 0, 0, j, 0, 0);
				} else {
					//firstRow = secondRow;
					//secondRow = new MatEl[n];
					if (j == 0) {
						secondRow[j] = new MatEl(i, i, 0, 0, 0, i);
					} else {
						int subCost = 0;
						if (x[i - 1].compareTo(y[j - 1]) != 0) {
							subCost = 1;
						}

						if (firstRow[j].totalOp + 1 < secondRow[j - 1].totalOp + 1) {
							if (firstRow[j].totalOp + 1 < firstRow[j - 1].totalOp + subCost) {
								secondRow[j] = new MatEl(firstRow[j]);
								secondRow[j].totalOp += 1;
								secondRow[j].deletion += 1;
							} else {
								secondRow[j] = new MatEl(firstRow[j - 1]);
								secondRow[j].totalOp += subCost;
								secondRow[j].substitution += subCost;
								secondRow[j].correct += subCost == 0 ? 1 : 0;
							}
						} else {
							if (secondRow[j - 1].totalOp + 1 < firstRow[j - 1].totalOp + subCost) {
								secondRow[j] = new MatEl(secondRow[j - 1]);
								secondRow[j].totalOp += 1;
								secondRow[j].insertion += 1;
							} else {
								secondRow[j] = new MatEl(firstRow[j - 1]);
								secondRow[j].totalOp += subCost;
								secondRow[j].substitution += subCost;
								secondRow[j].correct += subCost == 0 ? 1 : 0;
							}
						}

						secondRow[j].bestSubs = getMin(firstRow[j].bestSubs + 1, firstRow[j - 1].bestSubs + subCost,
								secondRow[j - 1].bestSubs + 1);
					}
				}
			}
			firstRow = secondRow;
		}

		return secondRow;
	}

	private String removeAllFormating(String input) {

		String output = input.replaceAll("\\s+", " ").trim();
		output = output.replaceAll("(\\r|\\n|\\r\\n|\\t)+", " ");
		output = output.replaceAll("\\s+", " ").trim();

		return output;

	}

	private int getMin(int a, int b, int c) {
		if (a < b) {
			if (a < c) {
				return a;
			} else {
				return c;
			}
		} else {
			if (b < c) {
				return b;
			} else {
				return c;
			}
		}
	}

}
