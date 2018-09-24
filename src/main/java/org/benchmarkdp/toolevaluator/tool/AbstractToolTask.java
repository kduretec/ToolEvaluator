package org.benchmarkdp.toolevaluator.tool;

import org.benchmarkdp.toolevaluator.EvaluationProc;
import org.benchmarkdp.toolevaluator.extraction.ExtractionProc;

import benchmarkdp.datagenerator.properties.ExperimentProperties;
import benchmarkdp.datagenerator.testcase.TestCase;

public class AbstractToolTask implements IToolTask{

	protected boolean successful = false;
	protected ExperimentProperties ep; 
	protected TestCase tc; 
	
	public AbstractToolTask(ExperimentProperties ep, TestCase tc) {
		super();
		this.ep = ep;
		this.tc = tc;
	}

	public ExtractionProc getExtractionProc() {
		// TODO Auto-generated method stub
		return null;
	}

	public EvaluationProc getEvaluationProc() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean s) {
		successful = s;
	}
	
	

}
