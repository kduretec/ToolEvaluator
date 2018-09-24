package org.benchmarkdp.toolevaluator.tool;

import org.benchmarkdp.toolevaluator.EvaluationProc;
import org.benchmarkdp.toolevaluator.extraction.ExtractionProc;

public interface IToolTask {

	public ExtractionProc getExtractionProc(); 
	
	public EvaluationProc getEvaluationProc();
	
	public boolean isSuccessful();
	
	public void setSuccessful(boolean s);
}
