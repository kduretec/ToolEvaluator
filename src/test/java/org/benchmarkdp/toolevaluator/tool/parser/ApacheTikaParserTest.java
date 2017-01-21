package org.benchmarkdp.toolevaluator.tool.parser;

import static org.junit.Assert.*;

import java.util.List;

import org.benchmarkdp.toolevaluator.elements.Text;
import org.junit.Test;

public class ApacheTikaParserTest {

	@Test
	public void Test1() {
		IParser tika = new ApacheTikaParser();

		String testText = "This is     a  \t huge text \n Another line       \n Third line";
		List<Text> elements = tika.parse(testText, "xml");
		assertTrue(elements.size() == 3);
	}

	@Test
	public void Test2() {
		IParser tika = new ApacheTikaParser();

		String testText = "This is     a  \t huge text \n Another line       \n Third line\n\n";
		List<Text> elements = tika.parse(testText, "xml");
		assertTrue(elements.size() == 3);
		Text elT = elements.get(0);
		assertTrue(elT.getText().compareTo("This is a huge text") == 0);
		assertTrue(elT.getText().compareTo("This is a huge text ") != 0);
		elT = elements.get(2);
		assertTrue(elT.getText().compareTo("Third line") == 0);
	}
}
