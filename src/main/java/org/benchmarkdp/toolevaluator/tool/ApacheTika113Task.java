package org.benchmarkdp.toolevaluator.tool;

import benchmarkdp.datagenerator.properties.ExperimentProperties;
import benchmarkdp.datagenerator.testcase.TestCase;

public class ApacheTika113Task extends AbstractApacheTikaTask {

	public ApacheTika113Task(ExperimentProperties ep, TestCase tc) {
		super(ep, tc);
		toolName = "ApacheTika_1_13";
		toolNameNice = "Apache Tika v1.13";
		tool = "/home/duretec/Programs/tika-app-1.13.jar";
	}

}
