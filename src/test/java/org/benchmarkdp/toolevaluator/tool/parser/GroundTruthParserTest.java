package org.benchmarkdp.toolevaluator.tool.parser;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.benchmarkdp.toolevaluator.elements.Text;
import org.junit.Test;

public class GroundTruthParserTest {

	@Test
	public void Test1() {
		IParser gt = new GroundTruthParser();

		String testText = "ELEMENT:234567\nText:This is     a  \t huge text Another line  Third line";
		List<Text> elements = gt.parse(testText);
		assertTrue(elements.size() == 1);
	}

	@Test
	public void Test2() {
		IParser gt = new GroundTruthParser();

		String testText = "ELEMENT:234567\nText:This is     a  \t huge text Another line  Third line\n\n";
		List<Text> elements = gt.parse(testText);
		assertTrue(elements.size() == 1);
		Text elT = elements.get(0);
		String ID = elT.getID();
		assertTrue(ID.compareTo("234567")==0);
		assertTrue(elT.getText().compareTo("This is a huge text Another line Third line") == 0);
	}
}
