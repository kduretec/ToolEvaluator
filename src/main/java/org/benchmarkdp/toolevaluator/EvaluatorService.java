package org.benchmarkdp.toolevaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluatorService {

	private static Logger log = LoggerFactory.getLogger(EvaluatorService.class);

	private int numProc = 4;

	private List<Runnable> runnables;

	public EvaluatorService() {
		runnables = new ArrayList<Runnable>();
	}

	public void setNumProc(int numProc) {
		this.numProc = numProc;
	}

	public void addRunnable(Runnable r) {
		runnables.add(r);
	}

	public void execute() {

		ExecutorService exec = Executors.newFixedThreadPool(numProc);
		log.info("Evaluator has " + runnables.size() + " tasks");
		for (Runnable r : runnables) {
			if (r != null) {
				exec.execute(r);
			}
		}

		exec.shutdown();
		try {
			exec.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Execution done");
	}

}
