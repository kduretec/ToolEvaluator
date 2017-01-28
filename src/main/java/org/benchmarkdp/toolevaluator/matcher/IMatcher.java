package org.benchmarkdp.toolevaluator.matcher;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;

public interface IMatcher {

	public void match(DocumentElements groundTruth, DocumentElements toolOutput);
	
}
