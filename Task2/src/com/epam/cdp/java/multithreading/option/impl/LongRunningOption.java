package com.epam.cdp.java.multithreading.option.impl;

import com.epam.cdp.java.multithreading.option.IOption;
import com.epam.cdp.java.multithreading.output.IOutput;
import com.epam.cdp.java.multithreading.util.ThreadUtil;

public class LongRunningOption implements IOption {

	@Override
	public void run(Object[] args, IOutput output) throws IllegalArgumentException {
		Long startTime = System.currentTimeMillis();

		output.output("Start executing of Long Running Option.");
		try {
			for (int i = 0; i < 5; i++) {
				Thread.sleep(500);
				output.output("Long Running Option is still executing.");
			}
		} catch (InterruptedException e) {
			System.out.println("Option execution is interrupted.");
			e.printStackTrace();
		}
		output.output("End executing of Long Running Option.");

		ThreadUtil.calcExecTime(Thread.currentThread().getName(), startTime);
	}

}
