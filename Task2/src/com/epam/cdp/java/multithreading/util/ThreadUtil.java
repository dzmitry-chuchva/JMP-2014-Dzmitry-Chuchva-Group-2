package com.epam.cdp.java.multithreading.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.epam.cdp.java.multithreading.option.OptionRunner;
import com.epam.cdp.java.multithreading.output.BufferedFileOutput;
import com.epam.cdp.java.multithreading.output.IOutput;

public class ThreadUtil {

	private static Map<String, Double> threadStatistics = new ConcurrentHashMap<String, Double>();

	public static void calcExecTime(String threadName, Long startTime) {
		Long endTime = System.currentTimeMillis();
		Double execTimeInSeconds = (endTime - startTime) / 1000.;
		threadStatistics.put(threadName, execTimeInSeconds);
	}

	public static void printStatistics() {
		IOutput out = BufferedFileOutput.getInstance();
		out.output("");
		out.output("Threads execution statistics:");
		for (Entry<String, Double> entry : threadStatistics.entrySet()) {
			String thrName = entry.getKey();
			Double execTime = entry.getValue();
			out.output("Thread " + thrName + " executions time is " + execTime + " sec.");
		}
	}

	public List<Thread> initThreadList(String[] cmdArgs) {
		List<Thread> threadList = new ArrayList<Thread>();

		StringBuilder curOption = new StringBuilder();
		List<String> curOptionParams = new ArrayList<String>();

		ThreadGroup group = new ThreadGroup("Options");
		Thread thread;

		for (int i = 0; i < cmdArgs.length; i++) {
			String arg = cmdArgs[i];
			// if starts with "-" it is option otherwise it is option
			// parameter
			if (arg.substring(0, 1).matches("-")) {
				// execute option only if it is not first elem
				if (curOption.length() != 0) {
					thread = new Thread(group, new OptionRunner(curOption.toString(), new ArrayList<String>(curOptionParams)));
					threadList.add(thread);
					curOption.setLength(0);
					curOptionParams.clear();
				}
				curOption.append(arg.substring(1, arg.length()));
			} else {
				curOptionParams.add(arg);
			}
			// if last iteration
			if (i == cmdArgs.length - 1) {
				thread = new Thread(group, new OptionRunner(curOption.toString(), new ArrayList<String>(curOptionParams)));
				threadList.add(thread);
			}
		}
		return threadList;
	}

}
