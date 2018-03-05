package org.benchmarkdp.toolevaluator.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Word Error Rate of two string objects. This object return the WER value but
 * also additional values such as number of substitutions, deletions and
 * insertions.
 * 
 * @author kresimir
 *
 */
public class WordErrorRate {

	private int deletion;

	private int substitution;

	private int insertion;

	private int correct;

	private int numberOfWords;

	private int totalOp;

	private int startPos;

	private int endPos;

	private String[] rWords;
	private String[] nWords;

	private int[] matchedWords;

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
	}

	private MatEl[][] mat;

	public WordErrorRate() {

	}

	public WordErrorRate(String ref, String n) {
		rWords = removeAllFormating(ref).split(" ");
		matchedWords = new int[rWords.length];
		setMatchedWords(0, matchedWords.length - 1, false);
		nWords = removeAllFormating(n).split(" ");
	}

	public WordErrorRate(String[] rW, String[] nW) {
		rWords = rW;
		matchedWords = new int[rWords.length];
		setMatchedWords(0, matchedWords.length - 1, false);
		nWords = nW;
	}

	public void setRefWords(String refWords) {
		rWords = removeAllFormating(refWords).split(" ");
		matchedWords = new int[rWords.length];
		setMatchedWords(0, matchedWords.length - 1, false);
	}

	public void setRefWords(String[] refWords) {
		rWords = refWords;
		matchedWords = new int[rWords.length];
		setMatchedWords(0, matchedWords.length - 1, false);
	}

	public void setNewWords(String newWords) {
		nWords = removeAllFormating(newWords).split(" ");
	}

	public void setNewWords(String[] newWords) {
		nWords = newWords;
	}

	public void evaluate() {
		int m = rWords.length + 1;
		int n = nWords.length + 1;
		mat = new MatEl[m][n];
		int totalI = 0;
		for (int i = 0; i < m; i++) {
			totalI = totalI + i;
			if (i > 0) {
				if (matchedWords[i - 1] == i - 1) {
					mat[i][0] = new MatEl(i, i, 0, 0, 0, i);
				} else {
					mat[i][0] = new MatEl(10000000, i, 0, 0, 0, 10000000);
				}
			} else {
				mat[i][0] = new MatEl(i, i, 0, 0, 0, i);
			}
		}

		for (int j = 0; j < n; j++) {
			mat[0][j] = new MatEl(j, 0, 0, j, 0, 0);
		}

		int minBestSubs = Integer.MAX_VALUE;
		int minBestSubsPos = -1;
		for (int j = 1; j < n; j++) {
			for (int i = 1; i < m; i++) {
				int subCost = 0;

				if (matchedWords[i - 1] == i - 1) {

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
							mat[i][j].substitution += subCost;
							mat[i][j].correct += subCost == 0 ? 1 : 0;
						}
					} else {
						if (mat[i][j - 1].totalOp + 1 < mat[i - 1][j - 1].totalOp + subCost) {
							mat[i][j] = new MatEl(mat[i][j - 1]);
							mat[i][j].totalOp += 1;
							mat[i][j].insertion += 1;
						} else {
							mat[i][j] = new MatEl(mat[i - 1][j - 1]);
							mat[i][j].totalOp += subCost;
							mat[i][j].substitution += subCost;
							mat[i][j].correct += subCost == 0 ? 1 : 0;
						}
					}

					mat[i][j].bestSubs = getMin(mat[i - 1][j].bestSubs + 1, mat[i - 1][j - 1].bestSubs + subCost,
							mat[i][j - 1].bestSubs + 1);

					if (i == m - 1) {
						if (mat[i][j].bestSubs < minBestSubs) {
							minBestSubs = mat[i][j].bestSubs;
							minBestSubsPos = j;
						}
					}
				} else {
					i = matchedWords[i - 1] + 1;
				}
			}
		}

		print(mat, m, n, rWords, nWords);
		
		endPos = minBestSubsPos;
		startPos = retrieveStartPos(m - 1, endPos);

		totalOp = mat[m - 1][n - 1].totalOp;
		deletion = mat[m - 1][n - 1].deletion;
		substitution = mat[m - 1][n - 1].substitution;
		insertion = mat[m - 1][n - 1].insertion;
		correct = mat[m - 1][n - 1].correct;
		numberOfWords = rWords.length;

		// updating to match exact string positions as additional
		// row and column are added
		startPos = startPos - 1;
		endPos = endPos - 1;
		setMatchedWords(startPos, endPos, true);

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

	private int retrieveStartPos(int i, int j) {
		// System.out.println("i=" +i + " j="+j); 
		if (i == 0) { return j + 1; }
		System.out.println("corr=" + mat[i][j].correct + " del=" + mat[i][j].deletion + " insert=" + mat[i][j].insertion
				+ " sub=" + mat[i][j].substitution);
		if (j > 0) {

			int subCost = 0;
			if (rWords[i - 1].compareTo(nWords[j - 1]) != 0) {
				subCost = 1;
			}

			if (mat[i][j].bestSubs == mat[i - 1][j - 1].bestSubs + subCost) {
				return retrieveStartPos(i - 1, j - 1);
			} else if (mat[i][j].bestSubs == mat[i - 1][j].bestSubs + 1) {
				return retrieveStartPos(i - 1, j);
			} else {
				return retrieveStartPos(i, j - 1);
			}

		} else {
			return retrieveStartPos(i - 1, j);
		}

	}

	/*
	 * private int retrieveStartPos(int i, int j) { System.out.println("corr=" +
	 * mat[i][j].correct + " del=" + mat[i][j].deletion + " insert=" +
	 * mat[i][j].insertion + " sub=" + mat[i][j].substitution); int crt =
	 * mat[i][j].correct; int inst = mat[i][j].deletion;
	 * 
	 * return j - crt - inst + 1; }
	 */
	private void setMatchedWords(int start, int end, boolean value) {
		for (int i = start; i <= end && i < matchedWords.length; i++) {
			if (!value) {
				matchedWords[i] = i;
			} else {
				matchedWords[i] = end + 1;
			}
		}
	}
	
	private void print(MatEl[][] matrix, int m, int n, String[] rW, String[] nW) { 
		File f = new File("/Users/kresimir/Desktop/matrix.tsv");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for (int i = -2; i<m; i++) {
				for (int j = -2; j<n; j++) {
					if (j==-2) {
						if (i>=0) {
							bw.write(i + "\t");
						}
					} else if (j==-1) {
						if (i>=1) {
							bw.write(rW[i-1] + "\t");
						}
					}
					if (i == -2) {
						if (j==0) {
							bw.write("\t\t0\t");
						} else if (j>0) {
							bw.write(j + "\t");
						}
					} else if ( i== -1) {
						if (j<1) {
							bw.write("\t");
						}else {
							bw.write(nW[j-1] + "\t");
						}
					}
					if (i>=0 && j>=0) {
						if (i==0 && j==0) bw.write("\t");
						bw.write("tOp="+matrix[i][j].totalOp + ",sB="+matrix[i][j].bestSubs + 
								",c="+matrix[i][j].correct + ",i="+matrix[i][j].insertion + 
								",d="+matrix[i][j].deletion + ",s="+matrix[i][j].substitution + 
								 "\t");
					}
				}
				bw.write("\n");
			}
			bw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
	}
}
