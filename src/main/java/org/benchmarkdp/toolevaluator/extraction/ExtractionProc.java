package org.benchmarkdp.toolevaluator.extraction;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.benchmarkdp.toolevaluator.tool.IToolTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractionProc implements Runnable{

	private static Logger log = LoggerFactory.getLogger(ExtractionProc.class);
	
	String command = null; 
	String outputFile = null; 
	IToolTask toolTask = null;
	String [] commands = null; 
	public ExtractionProc(String com, String oF, IToolTask tt) {
		command = com; 
		outputFile = oF;
		toolTask = tt;
	}
	public ExtractionProc(String[] com, String oF, IToolTask tt) {
		commands = com; 
		outputFile = oF;
		toolTask = tt;
	}
	
	public void run() {
		
		//log.info("Executing: " + command);
		try {
			Process p; 
			if (command != null) {
				p = Runtime.getRuntime().exec(command);
			} else {
				p = Runtime.getRuntime().exec(commands);
			}
			p.waitFor();
			//InputStream is = p.getInputStream();
			//InputStream is = p.getErrorStream();
			//log.info(IOUtils.toString(is,"UTF-8"));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File f = new File(outputFile);
		if (f.exists()) {
			toolTask.setSuccessful(true);
			//log.info("File is there");
		} else {
			toolTask.setSuccessful(false);
			//log.info("File is not there");
		}
	}

}
