package com.epam.cdp.java.multithreading.option.impl;

import com.epam.cdp.java.multithreading.option.IOption;
import com.epam.cdp.java.multithreading.output.IOutput;
import com.epam.cdp.java.multithreading.util.ThreadUtil;

public class SumOption implements IOption {

	@Override
	public void run(Object[] args, IOutput output) throws IllegalArgumentException {
		Long startTime = System.currentTimeMillis();

		if (args.length < 2) {
			System.out.println("You should provide two or more arguments for sum.");
			throw new IllegalArgumentException();
		}

		try {
			Integer result = 0;
			for (Object arg : args) {
				if (arg instanceof String) {
					result += new Integer((String) arg);
				} else {
					result += (Integer) arg;
				}
			}
			output.output("Sum is " + result.toString());
		} catch (NumberFormatException e) {
			System.out.println("You should provide only number values for sum.");
			throw new IllegalArgumentException(e);
		}

		ThreadUtil.calcExecTime(Thread.currentThread().getName(), startTime);
	}
}
