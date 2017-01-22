package org.benchmarkdp.toolevaluator;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.elements.GroundTruthElement;
import org.benchmarkdp.toolevaluator.elements.IElement;
import org.benchmarkdp.toolevaluator.elements.Text;
import org.benchmarkdp.toolevaluator.elements.ToolElement;
import org.junit.Test;

public class MatcherTest {

	
	
	@Test
	public void Test1() {
	
		DocumentElements gt = new DocumentElements();
		Text t = new Text();
		t.setText("this is one string element in the extracted text");
		IElement egt1 = new  GroundTruthElement(t);
		gt.addElement(egt1);
		
		t = new Text();
		t.setText("this is the second paragraph in the text");
		IElement egt2 = new GroundTruthElement(t);
		gt.addElement(egt2);
		
		DocumentElements tol = new DocumentElements();
		t = new Text();
		t.setText("this is one string element in the extracted text");
		IElement etol1 = new ToolElement(t);
		tol.addElement(etol1);
		
		Matcher m = new Matcher();
		m.match(gt, tol);
		
		assertTrue(egt1.getMatch() == etol1);
		assertTrue(etol1.getMatch() == egt1);
		assertTrue(egt2.getMatch() == null);
		
		t = new Text();
		t.setText("hello world");
		IElement etol2 = new ToolElement(t);
		tol.addElement(etol2);
		
		assertTrue(egt2.getMatch() == null);
		assertTrue(etol2.getMatch() == null);
	}
	
	@Test
	public void Test2() {
		DocumentElements gt = new DocumentElements();
		Text t = new Text();
		t.setText("this is one string element in the extracted text");
		t.setLines(Arrays.asList("this is one", "string element in the extracted text"));
		IElement egt1 = new  GroundTruthElement(t);
		gt.addElement(egt1);
		
		t = new Text();
		t.setText("this is the second paragraph in the text");
		t.setLines(Arrays.asList("this is the second paragraph", "in the text"));
		IElement egt2 = new GroundTruthElement(t);
		gt.addElement(egt2);
		
		DocumentElements tol = new DocumentElements();
		t = new Text();
		t.setText("this is one string element in the extracted text");
		t.setLines(Arrays.asList("this is one", "string element in the extracted text"));
		IElement etol1 = new ToolElement(t);
		tol.addElement(etol1);
		
		t = new Text();
		t.setText("this is the second paragraph");
		IElement etol2 = new ToolElement(t);
		tol.addElement(etol2);
		
		t = new Text();
		t.setText("in the text");
		IElement etol3 = new ToolElement(t);
		tol.addElement(etol3);
		
		Matcher m = new Matcher();
		m.match(gt, tol);
		
		assertTrue(egt1.getMatch() == etol1);
		assertTrue(etol1.getMatch() == egt1);
		assertTrue(tol.getNumElements()==2);
		assertTrue(tol.getElement(1).getTextElement().getText().compareTo("this is the second paragraph in the text")==0);
		assertTrue(egt2.getMatch()==tol.getElement(1));
		
		
	}
}
