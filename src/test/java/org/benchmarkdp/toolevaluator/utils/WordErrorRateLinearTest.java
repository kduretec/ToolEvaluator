package org.benchmarkdp.toolevaluator.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WordErrorRateLinearTest {

	private void checkStrings(String s1, String s2, int o, int c, int s, int i, int d) {
		WordErrorRateLinear wer = new WordErrorRateLinear(s1, s2);
		wer.evaluateWER();
		int op = wer.getTotalOp();
		int correct = wer.getCorrect();
		int substitution = wer.getSubstitution();
		int insertion = wer.getInsertion();
		int deletion = wer.getDeletion();

		// System.out.println("operation=" + op + " correct=" + correct + "
		// substitution=" + substitution + " insertion="
		// + insertion + " deletion=" + deletion);

		assertTrue(op == o);
		assertTrue(correct == c);
		assertTrue(substitution == s);
		assertTrue(insertion == i);
		assertTrue(deletion == d);
	}

	@Test
	public void Test1() {
		String s1 = "This is a nice string";
		String s2 = "This a nice string";
		checkStrings(s1, s2, 1, 4, 0, 0, 1);
	}
	
	@Test
	public void Test2() {
		String s1 = "This is a nice string";
		String s2 = "This a nice string indeed";
		checkStrings(s1, s2, 2, 4, 0, 1, 1);
	}

	@Test
	public void Test3() {
		String s1 = "P1 P2 T1 P3";
		String s2 = "P1 P2 P3 T1";
		checkStrings(s1, s2, 2, 2, 2, 0, 0);
	}
	
	
}
