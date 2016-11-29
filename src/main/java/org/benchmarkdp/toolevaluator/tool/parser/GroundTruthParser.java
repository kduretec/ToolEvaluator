package org.benchmarkdp.toolevaluator.tool.parser;

import java.util.ArrayList;
import java.util.List;

import org.benchmarkdp.toolevaluator.elements.Text;

public class GroundTruthParser extends AbstractParser {

	public List<Text> parse(String text) {

		List<Text> elements = new ArrayList<Text>();
		StringBuilder sb = new StringBuilder();
		boolean isEl = true;
		Text txtEl = new Text();
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) != '\n') {
				sb.append(text.charAt(i));
			} else {
				if (sb.length() > 0) {
					String s = sb.toString();
					if (isEl) {
						String[] parts = s.split(":");
						txtEl.setID(parts[1]);
						isEl = false;
						sb = new StringBuilder();
					} else {
						String[] parts = s.split(":");
						s = removeAllFormating(parts[1]);
						txtEl.setText(s);
						elements.add(txtEl);
						txtEl = new Text();
						sb = new StringBuilder();
						isEl = true;
					}
				}
			}
		}
		if (sb.length() > 0) {
			String s = sb.toString();
			String[] parts = s.split(":");
			s = removeAllFormating(parts[1]);
			txtEl.setText(s);
			elements.add(txtEl);
			txtEl = new Text();
			sb = new StringBuilder();
			isEl = true;
		}

		return elements;
	}

}
