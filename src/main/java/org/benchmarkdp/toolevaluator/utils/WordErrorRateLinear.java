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

	private class Pos {
		public int start;
		public int end;

		public Pos() {
			start = -1;
			end = -1;
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
		MatEl el = results[results.length - 1];
		totalOp = el.totalOp;
		deletion = el.deletion;
		substitution = el.substitution;
		insertion = el.insertion;
		correct = el.correct;
	}

	public void evaluatePosition() {
		Pos pos = calculateHirschberg(xWords, yWords, 0, false);
		startPos = pos.start;
		endPos = pos.end;
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
					secondRow[j] = new MatEl(j, 0, 0, j, 0, 0);
				} else {
					if (j == 0) {
						 secondRow[j] = new MatEl(i, i, 0, 0, 0, i);
						//secondRow[j] = new MatEl(i, i, 0, 0, 0, 0);
					} else {
						int subCost = 0;
						if (x[i - 1].compareTo(y[j - 1]) != 0) {
							subCost = 1;
						}

						if (firstRow[j].totalOp + 1 < secondRow[j - 1].totalOp + 1) {
							if (firstRow[j].totalOp + 0 < firstRow[j - 1].totalOp + subCost) {
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

	private Pos calculateHirschberg(String[] x, String[] y, int level, boolean right) {

		System.out.println("LEVEL: " + level + " X=" + connectStrings(x) + " Y=" + connectStrings(y));
		Pos tmpPos = new Pos();

		if (x.length == 0) {
			return tmpPos;
		} else if (y.length == 0) {
			return tmpPos;
		} else if (x.length == 1) {
			if (right) {
				for (int j = y.length -1; j >= 0; j--) {
					if (x[0].compareTo(y[j]) == 0) {
						tmpPos.start = j;
						tmpPos.end = j;
						return tmpPos;
					}
				}
			} else {
				for (int j = 0; j != y.length; j++) {
					if (x[0].compareTo(y[j]) == 0) {
						tmpPos.start = j;
						tmpPos.end = j;
						return tmpPos;
					}
				}
			}
		} else if (y.length == 1) {
			for (int i = 0; i < x.length; i++) {
				if (x[i].compareTo(y[0]) == 0) {
					tmpPos.start = 0;
					tmpPos.end = 0;
					return tmpPos;
				}
			}
		} else {
			int xlen = x.length - 1;
			int xmid = x.length / 2 - 1;
			int ylen = y.length - 1;
			String[] sx1 = subArray(x, 0, xmid);
			MatEl[] scoreL = calculateWER(sx1, y);
			String[] sx2rev = subArray(x, xlen, xmid + 1);
			String[] syrev = subArray(y, ylen, 0);
			MatEl[] scoreR = calculateWER(sx2rev, syrev);
			int ymid = findArgMax(scoreL, scoreR, right);
			System.out.println(ymid);
			String[] sx2 = subArray(x, xmid + 1, xlen);
			String[] sy1 = subArray(y, 0, ymid);
			String[] sy2 = subArray(y, ymid + 1, ylen);
			Pos pos1 = calculateHirschberg(sx1, sy1, level + 1, true);
			Pos pos2 = calculateHirschberg(sx2, sy2, level + 1, false);
			pos2.start = (pos2.start > -1) ? pos2.start + ymid + 1 : -1;
			pos2.end = (pos2.end > -1) ? pos2.end + ymid + 1 : -1;
			if (pos1.start > -1) {
				tmpPos.start = pos1.start;
			} else {
				tmpPos.start = pos2.start;
			}
			if (pos2.end > -1) {
				tmpPos.end = pos2.end;
			} else {
				tmpPos.end = pos1.end;
			}
			System.out.println("LEVEL: " + level + " Current positions " + tmpPos.start + " " + tmpPos.end);
		}
		return tmpPos;
	}

	private String[] subArray(String[] st, int i, int j) {
		if (j == -1 || i == -1)
			return new String[0];

		String[] tmp = null;
		int inc = i > j ? -1 : 1;
		int len = Math.abs(j - i) + 1;
		tmp = new String[len];
		int cnt = 0;
		if (len > 0) {
			while (true) {
				tmp[cnt] = st[i];
				if (i == j)
					break;
				i = i + inc;
				cnt++;
			}
		}

		return tmp;
	}

	private int findArgMax(MatEl[] scx, MatEl[] scy, boolean right) {
		int j = scy.length - 1;
		int min = Integer.MAX_VALUE;
		int pos = -1;
		// int max = -1;
		for (int i = 0; i < scx.length; i++) {
			// int totalOp = scx[i].totalOp + scy[j].totalOp;
			int totalOp = scx[i].bestSubs + scy[j].bestSubs;
			if (right) {
				if (totalOp <= min) {
					min = totalOp;
					pos = i;
				}
			} else {
				if (totalOp < min) {
					min = totalOp;
					pos = i;
				}
			}
			/*
			 * if (totalOp > max) { max = totalOp; System.out.println("max="
			 * +max); pos = i; }
			 */
			j--;
		}
		return pos - 1;
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

	private String connectStrings(String[] s) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		// System.out.print("[");
		for (int i = 0; i < s.length; i++) {
			// System.out.print(s[i] + " ");
			sb.append(s[i] + " ");
		}
		sb.append("]");
		return sb.toString();
	}
}
