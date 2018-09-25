package org.benchmarkdp.toolevaluator.tool;

import benchmarkdp.datagenerator.properties.ExperimentProperties;
import benchmarkdp.datagenerator.testcase.TestCase;

public class ApacheTika11Task extends AbstractApacheTikaTask {

	public ApacheTika11Task(ExperimentProperties ep, TestCase tc) {
		super(ep, tc);
		toolName = "ApacheTika_1_1";
		toolNameNice = "Apache Tika v1.1";
		tool = "/home/duretec/Programs/tika-app-1.1.jar";
	}

}
