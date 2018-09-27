package org.benchmarkdp.toolevaluator;

import java.util.ArrayList;
import java.util.List;

import org.benchmarkdp.toolevaluator.tool.AbiWordTask;
import org.benchmarkdp.toolevaluator.tool.ApacheTika113Task;
import org.benchmarkdp.toolevaluator.tool.ApacheTika119Task;
import org.benchmarkdp.toolevaluator.tool.ApacheTika11Task;
import org.benchmarkdp.toolevaluator.tool.ApacheTika12Task;
import org.benchmarkdp.toolevaluator.tool.DocToTextTask;
import org.benchmarkdp.toolevaluator.tool.IToolTask;
import org.benchmarkdp.toolevaluator.tool.PdfactTask;
import org.benchmarkdp.toolevaluator.tool.XpdfTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import benchmarkdp.datagenerator.properties.ExperimentProperties;
import benchmarkdp.datagenerator.testcase.TestCase;
import benchmarkdp.datagenerator.testcase.TestCaseContainer;

public class ToolTaskLoader {

	private static Logger log = LoggerFactory.getLogger(ToolTaskLoader.class);
	
	public ToolTaskLoader() {

	}

	public List<IToolTask> getTasks(ExperimentProperties ep, TestCaseContainer tCC, boolean pdfOnly) {
		List<IToolTask> tasks = new ArrayList<IToolTask>();
		for (TestCase tc : tCC.getTestCases()) {
			tasks.addAll(addTaskForTestCase(ep, tc, pdfOnly));
		}
		return tasks;
	}

	private List<IToolTask> addTaskForTestCase(ExperimentProperties ep, TestCase tc, boolean pdfOnly) {
		List<IToolTask> tasks = addTaskAllFormat(ep, tc);
		if (pdfOnly) {
			//log.info("Adding tools which can only extract pdf");
			tasks.addAll(addTaskPdfOnly(ep, tc));
		}
		return tasks;
	}

	private List<IToolTask> addTaskPdfOnly(ExperimentProperties ep, TestCase tc) {
		List<IToolTask> tasks = new ArrayList<IToolTask>();
		tasks.add(new XpdfTask(ep, tc));
		tasks.add(new PdfactTask(ep, tc));
		return tasks;
	}

	private List<IToolTask> addTaskAllFormat(ExperimentProperties ep, TestCase tc) {
		List<IToolTask> tasks = new ArrayList<IToolTask>();
		tasks.add(new ApacheTika11Task(ep, tc));
		tasks.add(new ApacheTika12Task(ep, tc));
		tasks.add(new ApacheTika113Task(ep, tc));
		tasks.add(new ApacheTika119Task(ep, tc));
		tasks.add(new DocToTextTask(ep, tc));
		tasks.add(new AbiWordTask(ep, tc));
		return tasks;
	}
}
