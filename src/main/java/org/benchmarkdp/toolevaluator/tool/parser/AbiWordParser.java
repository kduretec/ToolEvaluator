package org.benchmarkdp.toolevaluator.tool.parser;

import java.util.ArrayList;
import java.util.List;

import org.benchmarkdp.toolevaluator.elements.Text;

public class AbiWordParser extends AbstractParser {

	public List<Text> parseToTextElements(String text, String format) {
		List<Text> elements = new ArrayList<Text>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) != '\n') {
				sb.append(text.charAt(i));
			} else {
				if (sb.length() > 0) {
					String s = sb.toString();
					s = removeAllFormating(s);
					Text txtEl = new Text();
					txtEl.setText(s);
					elements.add(txtEl);
					sb = new StringBuilder();
				}
			}
		}
		if (sb.length() > 0) {
			String s = sb.toString();
			s = removeAllFormating(s);
			Text txtEl = new Text();
			txtEl.setText(s);
			elements.add(txtEl);
		}

		return elements;
	}

}
