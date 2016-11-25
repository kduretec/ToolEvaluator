package org.benchmarkdp.toolevaluator.utils;

import static org.junit.Assert.*;

import org.junit.Test;


public class WordErrorRateTest {

	@Test
	public void Test1() {
		String s1 = "This is a nice string";
		String s2 = "This a nice string";
		WordErrorRate wer = new WordErrorRate(s1, s2);
		wer.evaluate();
		int op = wer.getTotalOp();
		assertTrue(op==1);
	}
}
