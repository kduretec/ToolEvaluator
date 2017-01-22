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

	
	
//	@Test
//	public void Test1() {
//	
//		DocumentElements gt = new DocumentElements();
//		Text t = new Text();
//		t.setText("this is one string element in the extracted text");
//		IElement egt1 = new  GroundTruthElement(t);
//		gt.addElement(egt1);
//		
//		t = new Text();
//		t.setText("this is the second paragraph in the text");
//		IElement egt2 = new GroundTruthElement(t);
//		gt.addElement(egt2);
//		
//		DocumentElements tol = new DocumentElements();
//		t = new Text();
//		t.setText("this is one string element in the extracted text");
//		IElement etol1 = new ToolElement(t);
//		tol.addElement(etol1);
//		
//		Matcher m = new Matcher();
//		m.match(gt, tol);
//		
//		assertTrue(egt1.getMatch() == etol1);
//		assertTrue(etol1.getMatch() == egt1);
//		assertTrue(egt2.getMatch() == null);
//		
//		t = new Text();
//		t.setText("hello world");
//		IElement etol2 = new ToolElement(t);
//		tol.addElement(etol2);
//		
//		assertTrue(egt2.getMatch() == null);
//		assertTrue(etol2.getMatch() == null);
//	}
	
	@Test
	public void Test2() {
		DocumentElements gt = new DocumentElements();
		Text t = new Text();
		t.setText("First is one string element in the extracted text");
		t.setLines(Arrays.asList("First is one", "string element in the extracted text"));
		IElement egt1 = new  GroundTruthElement(t);
		gt.addElement(egt1);
		
		t = new Text();
		t.setText("Second is the second paragraph in the text");
		t.setLines(Arrays.asList("Second is the second paragraph", "in the text"));
		IElement egt2 = new GroundTruthElement(t);
		gt.addElement(egt2);
		
		t = new Text();
		t.setText("Third paragraph should match according to words");
		t.setLines(Arrays.asList("Third paragraph should match according to words"));
		IElement egt3 = new GroundTruthElement(t);
		gt.addElement(egt3);
		
		DocumentElements tol = new DocumentElements();
		t = new Text();
		t.setText("First is one string element in the extracted text");
		t.setLines(Arrays.asList("First is one", "string element in the extracted text"));
		IElement etol1 = new ToolElement(t);
		tol.addElement(etol1);
		
		t = new Text();
		t.setText("Second is the second paragraph");
		IElement etol2 = new ToolElement(t);
		tol.addElement(etol2);
		
		t = new Text();
		t.setText("in the text");
		IElement etol3 = new ToolElement(t);
		tol.addElement(etol3);
		
		t = new Text();
		t.setText("Third");
		IElement etol4 = new ToolElement(t);
		tol.addElement(etol4);
		
		t = new Text();
		t.setText("paragraph");
		IElement etol5 = new ToolElement(t);
		tol.addElement(etol5);
		
		t = new Text();
		t.setText("should");
		IElement etol6 = new ToolElement(t);
		tol.addElement(etol6);
		
		t = new Text();
		t.setText("match");
		IElement etol7 = new ToolElement(t);
		tol.addElement(etol7);
		
		t = new Text();
		t.setText("according");
		IElement etol8 = new ToolElement(t);
		tol.addElement(etol8);
		
		t = new Text();
		t.setText("to");
		IElement etol9 = new ToolElement(t);
		tol.addElement(etol9);
		
		t = new Text();
		t.setText("words");
		IElement etol10 = new ToolElement(t);
		tol.addElement(etol10);
		
		assertTrue(tol.getNumElements()==10);
		assertTrue(tol.getElement(4).getTextElement().getText().compareTo("paragraph")==0);
		Matcher m = new Matcher();
		m.match(gt, tol);
		
		assertTrue(egt1.getMatch() == etol1);
		assertTrue(etol1.getMatch() == egt1);
		assertTrue(tol.getNumElements()==3);
		assertTrue(tol.getElement(1).getTextElement().getText().compareTo("Second is the second paragraph in the text")==0);
		assertTrue(egt2.getMatch()==tol.getElement(1));
		assertTrue(egt3.getMatch()==tol.getElement(2));
		
		
	}
}
