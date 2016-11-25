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
	
	private int totalOp;

	private int[][] mat;

	public WordErrorRate(String ref, String n) {
		reference = ref;
		newString = n;
	}

	public void evaluate() {
		String[] rWords = reference.split(" ");
		String[] nWords = newString.split(" ");
		int m = rWords.length + 1;
		int n = nWords.length + 1;
		mat = new int[m][n];

		for (int i = 0; i < m; i++) {
			mat[i][0] = i;
		}

		for (int j = 0; j < n; j++) {
			mat[0][j] = j;
		}

		for (int j = 1; j < n; j++) {
			for (int i = 1; i < m; i++) {
				int subCost = 0;
				if (rWords[i - 1].compareTo(nWords[j - 1]) != 0) {
					subCost = 1;
				}
				int value = 0;
				if (mat[i - 1][j] + 1 < mat[i][j - 1] + 1) {
					if (mat[i - 1][j] + 1 < mat[i - 1][j - 1] + subCost) {
						value = mat[i - 1][j] + 1;
					} else {
						value = mat[i - 1][j - 1] + subCost;
					}
				} else {
					if (mat[i][j - 1] + 1 < mat[i - 1][j - 1] + subCost) {
						value = mat[i][j - 1] + 1;
					} else {
						value = mat[i - 1][j - 1] + subCost;
					}
				}
				mat[i][j] = value;
			}
		}
	
		totalOp = mat[m-1][n-1];
		
	}
	
	public int getTotalOp() {
		return totalOp;
	}
}
