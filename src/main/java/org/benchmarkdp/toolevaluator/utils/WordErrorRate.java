package org.benchmarkdp.toolevaluator.utils;

/**
 * Word Error Rate of two string objects This object return the WER value but
 * also additional values such as number of substitutions, deletions and
 * insertions.
 * 
 * @author kresimir
 *
 */
public class WordErrorRate {

	private String reference;

	private String newString;

	private int deletion;

	private int substitution;

	private int insertion;

	private int correct;
	
	private int numberOfWords;

	private int totalOp;

	private class MatEl {
		public int totalOp;

		public int deletion;

		public int substitution;

		public int insertion;

		public int correct;

		public MatEl(int tO, int del, int sub, int ins, int cor) {
			totalOp = tO;
			deletion = del;
			substitution = sub;
			insertion = ins;
			correct = cor;
		}

		public MatEl(MatEl me) {
			totalOp = me.totalOp;
			deletion = me.deletion;
			substitution = me.substitution;
			insertion = me.insertion;
			correct = me.correct;
		}
	}

	private MatEl[][] mat;

	public WordErrorRate(String ref, String n) {
		reference = ref;
		newString = n;
	}

	public void evaluate() {
		String[] rWords = reference.split(" ");
		String[] nWords = newString.split(" ");
		int m = rWords.length + 1;
		int n = nWords.length + 1;
		mat = new MatEl[m][n];
		for (int i = 0; i < m; i++) {
			mat[i][0] = new MatEl(i, i, 0, 0, 0);
		}

		for (int j = 0; j < n; j++) {
			mat[0][j] = new MatEl(j, 0, 0, j, 0);
		}

		for (int j = 1; j < n; j++) {
			for (int i = 1; i < m; i++) {
				int subCost = 0;
				if (rWords[i - 1].compareTo(nWords[j - 1]) != 0) {
					subCost = 1;
				}
				if (mat[i - 1][j].totalOp + 1 < mat[i][j - 1].totalOp + 1) {
					if (mat[i - 1][j].totalOp + 1 < mat[i - 1][j - 1].totalOp + subCost) {
						mat[i][j] = new MatEl(mat[i - 1][j]);
						mat[i][j].totalOp += 1;
						mat[i][j].deletion += 1;
					} else {
						mat[i][j] = new MatEl(mat[i - 1][j - 1]);
						mat[i][j].totalOp += subCost;
						mat[i][j].substitution += subCost > 0 ? 1 : 0;
						mat[i][j].correct += subCost > 0 ? 0 : 1;
					}
				} else {
					if (mat[i][j - 1].totalOp + 1 < mat[i - 1][j - 1].totalOp + subCost) {
						mat[i][j] = new MatEl(mat[i][j-1]);
						mat[i][j].totalOp += 1;
						mat[i][j].insertion += 1;
					} else {
						mat[i][j] = new MatEl(mat[i - 1][j - 1]);
						mat[i][j].totalOp += subCost;
						mat[i][j].substitution += subCost > 0 ? 1 : 0;
						mat[i][j].correct += subCost > 0 ? 0 : 1;
					}
				}
			}
		}

		totalOp = mat[m - 1][n - 1].totalOp;
		deletion = mat[m - 1][n - 1].deletion;
		substitution = mat[m - 1][n - 1].substitution;
		insertion = mat[m - 1][n - 1].insertion;
		correct = mat[m - 1][n - 1].correct;
		numberOfWords = rWords.length;
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
}
