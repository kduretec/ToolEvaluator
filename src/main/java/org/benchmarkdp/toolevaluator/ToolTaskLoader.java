package org.benchmarkdp.toolevaluator;

import java.util.ArrayList;
import java.util.List;

import org.benchmarkdp.toolevaluator.tool.ApacheTika113Task;
import org.benchmarkdp.toolevaluator.tool.ApacheTika11Task;
import org.benchmarkdp.toolevaluator.tool.ApacheTika12Task;
import org.benchmarkdp.toolevaluator.tool.IToolTask;

import benchmarkdp.datagenerator.properties.ExperimentProperties;
import benchmarkdp.datagenerator.testcase.TestCase;
import benchmarkdp.datagenerator.testcase.TestCaseContainer;

public class ToolTaskLoader {

	public ToolTaskLoader() {

	}

	public List<IToolTask> getTasks(ExperimentProperties ep, TestCaseContainer tCC) {
		List<IToolTask> tasks = new ArrayList<IToolTask>();
		for (TestCase tc : tCC.getTestCases()) {
			tasks.addAll(addTaskForTestCase(ep, tc));
		}
		return tasks;
	}
	
	private List<IToolTask> addTaskForTestCase(ExperimentProperties ep, TestCase tc) {
		List<IToolTask> tasks = new ArrayList<IToolTask>();
		tasks.add(new ApacheTika11Task(ep, tc));
		tasks.add(new ApacheTika12Task(ep, tc));
		tasks.add(new ApacheTika113Task(ep, tc));
		return tasks;
	}
}
