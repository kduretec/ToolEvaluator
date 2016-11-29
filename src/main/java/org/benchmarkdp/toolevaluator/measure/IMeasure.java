package org.benchmarkdp.toolevaluator.measure;

import org.benchmarkdp.toolevaluator.elements.DocumentElements;

/**
 * Interface for the performance measures
 * 
 * @author kresimir
 *
 */
public interface IMeasure {

	public void measure(DocumentElements gt, DocumentElements tool);

}
