package org.benchmarkdp.toolevaluator.tool;

import benchmarkdp.datagenerator.properties.ExperimentProperties;
import benchmarkdp.datagenerator.testcase.TestCase;

public class ApacheTika12Task extends AbstractApacheTikaTask{

	public ApacheTika12Task(ExperimentProperties ep, TestCase tc) {
		super(ep, tc);
		toolName = "ApacheTika_1_2";
		toolNameNice = "Apache Tika v1.2";
		tool = "/home/duretec/Programs/tika-app-1.2.jar";
	}

}
