package org.benchmarkdp.toolevaluator.tool.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GenericParserTest {

	@Test
	public void Test1() {
		IParser gen = new GenericParser();

		String testText = "This is     a  \t huge text \n Another line       \n Third line";
		gen.parse(testText, "txt");
		assertTrue(gen.getLines().size() == 3);
		Integer num = gen.getWordHistogram().get("line");
		assertTrue(num.intValue() == 2);
		Integer[] wordPos = gen.getWordsPositions();
		assertTrue(wordPos[2].intValue() == 0);
		assertTrue(wordPos[5].intValue() == 1);
		assertTrue(wordPos[8].intValue() == 2);

		String[] words = gen.getAllWords();
		assertTrue(words.length == 9);
		assertTrue(words[0].compareTo("This") == 0);
		assertTrue(words[2].compareTo("a") == 0);
		assertTrue(words[5].compareTo("Another") == 0);
	}

}
