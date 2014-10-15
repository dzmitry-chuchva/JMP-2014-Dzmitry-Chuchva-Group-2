package com.epam.cdp.java.classloader.task1.impl;

import com.epam.cdp.java.classloader.task1.IOption;

public class SumOption implements IOption {

	@Override
	public void run(Object[] args) throws IllegalArgumentException {

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
			System.out.println(result);
		} catch (NumberFormatException e) {
			System.out.println("You should provide only number values for sum.");
			throw new IllegalArgumentException(e);
		}
	}
}
