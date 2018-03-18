package org.benchmarkdp.toolevaluator.matcher;

import static org.junit.Assert.assertTrue;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.GroundTruthElement;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.elements.Text;
import org.junit.Test;

public class TextMatcherLinearTest {

	@Test
	public void Test1() {
		DocumentElements gt = new DocumentElements();
		Text t = new Text();
		t.setText("this is the first paragraph");
		IElement egt1 = new GroundTruthElement(t);
		gt.addElement(egt1);

		t = new Text();
		t.setText("And now comes the second paragraph in the text");
		IElement egt2 = new GroundTruthElement(t);
		gt.addElement(egt2);

		t = new Text();
		t.setText("And now comes the second paragraph in the text");
		IElement egt3 = new GroundTruthElement(t);
		gt.addElement(egt3);

		DocumentElements tol = new DocumentElements();
		String[] allText = { "this", "is", "the", "first", "paragraph", "And", "now", "comes", "the", "second",
				"paragraph", "hyperlink", "in", "the", "text" };
		tol.setAllText(allText);
		Integer[] wordPositions = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 3, 3 };
		tol.setWordPositions(wordPositions);

		IMatcher m = new TextMatcherLinear();
		m.match(gt, tol);

		assertTrue(egt1.isMatched());
		assertTrue(egt1.getStartLinePosition() == 1);
		assertTrue(egt2.isMatched());
		assertTrue(egt2.getStartLinePosition() == 1);
		assertTrue(egt2.getEndLinePosition() == 3);
		assertTrue(egt2.getStartWordPosition() == 5);
		assertTrue(egt2.getEndWordPosition() == 14);
		assertTrue(!egt3.isMatched());
	}

	@Test
	public void Test2() {
		DocumentElements gt = new DocumentElements();
		Text t = new Text();
		t.setText("this is the first paragraph");
		IElement egt1 = new GroundTruthElement(t);
		gt.addElement(egt1);

		t = new Text();
		t.setText("And now comes the second paragraph in the text");
		IElement egt2 = new GroundTruthElement(t);
		gt.addElement(egt2);

		t = new Text();
		t.setText("And now comes the second paragraph in the text");
		IElement egt3 = new GroundTruthElement(t);
		gt.addElement(egt3);

		DocumentElements tol = new DocumentElements();
		String[] allText = { "And", "now", "comes", "the", "second", "paragraph", "in", "the", "text", "test", "test",
				"this", "is", "the", "first", "paragraph", "And", "now", "comes", "the", "second", "paragraph",
				"hyperlink", "in", "the", "text" };
		tol.setAllText(allText);
		Integer[] wordPositions = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4 };
		tol.setWordPositions(wordPositions);

		IMatcher m = new TextMatcherLinear();
		m.match(gt, tol);

		assertTrue(egt1.isMatched());
		assertTrue(egt1.getStartLinePosition() == 2);
		assertTrue(egt2.isMatched());
		assertTrue(egt2.getStartLinePosition() == 1);
		assertTrue(egt2.getEndLinePosition() == 1);
		assertTrue(egt2.getStartWordPosition() == 0);
		assertTrue(egt2.getEndWordPosition() == 8);
		assertTrue(egt3.isMatched());
	}
}
