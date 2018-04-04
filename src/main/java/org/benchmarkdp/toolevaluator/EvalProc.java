package org.benchmarkdp.toolevaluator;

import java.util.ArrayList;
import java.util.List;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;
import org.benchmarkdp.toolevaluator.loader.GenericLoader;
import org.benchmarkdp.toolevaluator.loader.ILoader;
import org.benchmarkdp.toolevaluator.matcher.IMatcher;
import org.benchmarkdp.toolevaluator.matcher.TextMatcherLinear;
import org.benchmarkdp.toolevaluator.measure.IMeasure;
import org.benchmarkdp.toolevaluator.measure.PercCorMeasure;
import org.benchmarkdp.toolevaluator.measure.TextIntegrityMeasure;
import org.benchmarkdp.toolevaluator.measure.TextOrderMeasure;
import org.benchmarkdp.toolevaluator.measure.TextWERLinearMeasure;
import org.benchmarkdp.toolevaluator.output.IOutputWriter;
import org.benchmarkdp.toolevaluator.output.XMLOutputWriter;
import org.benchmarkdp.toolevaluator.tool.ITool;

public class EvalProc implements Runnable {

	private List<IMeasure> pMeasures;

	private IMatcher matcher;

	private ILoader loader;
	private IOutputWriter output;

	String toolName;
	String toolResPath;
	String testFile;
	DocumentElements gtElements;
	DocumentElements toElements;

	ITool groundTruthTool;
	ITool tool;
	public EvalProc(String tF, String tN, String trp, ITool gt, ITool tt) {
		// pMeasures = m;
		testFile = tF;
		toolName = tN;
		toolResPath = trp;
		loader = new GenericLoader();
		matcher = new TextMatcherLinear();
		output = new XMLOutputWriter();
		//gtElements = gtE;
		//toElements = toE;
		groundTruthTool = gt;
		tool = tt;
		pMeasures = new ArrayList<IMeasure>();
		// measures.add(new WERMeasure());
		// measures.add(new PercCorMeasure());
		// measures.add(new OrderMeasure());
		// measures.add(new IntegrityMeasure());
		// measures.add(new TextHistogramDiffMeasure());
		pMeasures.add(new TextWERLinearMeasure());
		pMeasures.add(new PercCorMeasure());
		pMeasures.add(new TextOrderMeasure());
		pMeasures.add(new TextIntegrityMeasure());

	}

	public void run() {

		long startTime = System.nanoTime();
		String testName = testFile.substring(0, testFile.lastIndexOf("."));
		String extension = testFile.substring(testFile.lastIndexOf(".") + 1, testFile.length());

		gtElements = loader.getGroundTruth(testName, extension, groundTruthTool);
		toElements = loader.getToolOutput(testName, extension, tool);
		
		//System.out.println("Processing testName=" + testName + " tool=" + toolName);
		matcher.match(gtElements, toElements);

		for (IMeasure measure : pMeasures) {
			measure.measure(gtElements, toElements);
		}

		output.save(gtElements, testName, testFile, toolName, toolResPath);

		long endTime = System.nanoTime();
		double elapsedTime = ((double) endTime - startTime) / 1000000000;
		System.out.println(
				"TestCase=" + testName + " toolName=" + toolName + " processed in " + elapsedTime + " seconds");
	}
}
