package org.benchmarkdp.toolevaluator.tool.parser;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.benchmarkdp.toolevaluator.elements.Text;
import org.junit.Test;

public class GroundTruthParserTest {

	@Test
	public void Test1() {
		IParser gt = new GroundTruthParser();

		String testText = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> "
				+ "<textValues>"
				+ "	<document>Test1</document>"
				+ "	<textElements>"
				+ "		<element>"
				+ "			<ID>1</ID>"
				+ "			<text>Hello world</text>"
				+ "		</element>"
				+ "		<element>"
				+ "			<ID>2</ID>"
				+ "			<text>Hello world again</text>"
				+ "		</element>"
				+ "	</textElements>"
				+ "</textValues>";
		List<Text> elements = gt.parse(testText);
		assertTrue(elements.size() == 2);
	}

	@Test
	public void Test2() {
		IParser gt = new GroundTruthParser();

		String testText = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> "
				+ "<textValues>"
				+ "	<document>Test2</document>"
				+ "	<textElements>"
				+ "		<element>"
				+ "			<ID>234567</ID>"
				+ "			<text>This is a huge text \t      Another line Third line \n\n   </text>"
				+ "		</element>"
				+ "	</textElements>"
				+ "</textValues>";
		List<Text> elements = gt.parse(testText);
		assertTrue(elements.size() == 1);
		Text elT = elements.get(0);
		String ID = elT.getID();
		assertTrue(ID.compareTo("234567")==0);
		assertTrue(elT.getText().compareTo("This is a huge text Another line Third line") == 0);
	}
}
