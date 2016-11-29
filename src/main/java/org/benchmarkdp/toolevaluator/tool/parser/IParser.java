package org.benchmarkdp.toolevaluator.tool.parser;

import java.util.List;

import org.benchmarkdp.toolevaluator.elements.Text;

/**
 * The interface for tool output parsers 
 * @author kresimir
 *
 */
public interface IParser {

	public List<Text> parse(String text);
	
}
