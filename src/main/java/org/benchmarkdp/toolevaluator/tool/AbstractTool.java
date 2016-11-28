package org.benchmarkdp.toolevaluator.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public abstract class AbstractTool implements ITool{

	protected String readFileToString(File f) {
		StringBuilder sb = new StringBuilder();
		try {
			Reader r = new InputStreamReader(new FileInputStream(f), "UTF-8");
			int c = 0;
			while ((c = r.read()) != -1) {
				sb.append((char) c);
			}
			r.close();
		} catch (IOException e) {
			return null;
		}
		String result = sb.toString();
		return result;
	}
}
