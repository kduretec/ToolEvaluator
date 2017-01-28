package org.benchmarkdp.toolevaluator.matcher;

import static org.junit.Assert.*;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.GroundTruthElement;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.elements.Text;
import org.junit.Test;

public class TextMatcherTest {

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

		DocumentElements tol = new DocumentElements();
		String[] allText = { "this", "is", "the", "first", "paragraph", "And", "now", "comes", "the", "second",
				"paragraph", "hyperlink", "in", "the", "text" };
		tol.setAllText(allText);
		Integer[] wordPositions = {1,1,1,1,1,1,1,1,1,1,1,2,2,3,3};
		tol.setWordPositions(wordPositions);
		
		IMatcher m = new TextMatcher();
		m.match(gt, tol);
		
		assertTrue(egt1.isMatched());
		assertTrue(egt1.getStartLinePosition()==1);
		assertTrue(egt2.isMatched());
		assertTrue(egt2.getStartLinePosition()==1);
		assertTrue(egt2.getEndLinePosition()==3);
		assertTrue(egt2.getStartWordPosition()==5);
		assertTrue(egt2.getEndWordPosition()==14);
	}
}
