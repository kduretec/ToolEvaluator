package org.benchmarkdp.toolevaluator.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class WordErrorRateTest {

	private void checkStrings(String s1, String s2, int o, int c, int s, int i, int d) {
		WordErrorRate wer = new WordErrorRate(s1, s2);
		wer.evaluate();
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

	@Test
	public void Test4() {
		String s1 = "P1 P2 T1 P3";
		String s2 = "P1 P2 P3 T1";
		WordErrorRate wer = new WordErrorRate(s1, s2);
		wer.evaluate();
		int startPos = wer.getStartPos();
		int endPos = wer.getEndPos();
		//System.out.println("startPos=" + startPos + " endPos=" + endPos);
		assertTrue(startPos == 0 && endPos == 2);
	}
	
	@Test
	public void Test5() {
		String s1 = "This is a string that should be found";
		String s2 = "This is a This is a string that should be found found should be found should be found should be found";
		WordErrorRate wer = new WordErrorRate(s1, s2);
		wer.evaluate();
		int startPos = wer.getStartPos();
		int endPos = wer.getEndPos();
		System.out.println("startPos=" + startPos + " endPos=" + endPos);
		assertTrue(startPos == 3 && endPos == 10);
	}
	
	@Test
	public void Test6() {
		String s1 = "This is a string that should be found";
		String s2 = "Thiss is a This is a string that Another string goes from here";
		WordErrorRate wer = new WordErrorRate(s1, s2);
		wer.evaluate();
		int startPos = wer.getStartPos();
		int endPos = wer.getEndPos();
		System.out.println("startPos=" + startPos + " endPos=" + endPos);
		assertTrue(startPos == 3 && endPos == 7);
	}
	
	@Test
	public void Test7() {
		String s1 = "This is a string that should be found";
		String s2 = "This is a This is a string that might should be found Another string goes from here";
		WordErrorRate wer = new WordErrorRate(s1, s2);
		wer.evaluate();
		int startPos = wer.getStartPos();
		int endPos = wer.getEndPos();
		//System.out.println("startPos=" + startPos + " endPos=" + endPos);
		assertTrue(startPos == 3 && endPos == 11);
	}

}
