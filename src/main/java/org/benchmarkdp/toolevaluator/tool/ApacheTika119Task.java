package org.benchmarkdp.toolevaluator.tool;

import benchmarkdp.datagenerator.properties.ExperimentProperties;
import benchmarkdp.datagenerator.testcase.TestCase;

public class ApacheTika119Task extends AbstractApacheTikaTask {

	public ApacheTika119Task(ExperimentProperties ep, TestCase tc) {
		super(ep, tc);
		toolName = "ApacheTika_1_19";
		toolNameNice = "Apache Tika v1.19";
		tool = "/home/duretec/Programs/tika-app-1.19.jar";
	}

}
