package com.epam.cdp.java.multithreading.option.impl;

import com.epam.cdp.java.multithreading.option.IOption;
import com.epam.cdp.java.multithreading.output.IOutput;
import com.epam.cdp.java.multithreading.util.ThreadUtil;

public class SayHelloOption implements IOption {

	@Override
	public void run(Object[] args, IOutput output) throws IllegalArgumentException {
		Long startTime = System.currentTimeMillis();

		if (args.length == 0) {
			output.output("Hello, user.");
		} else {
			StringBuilder helloStr = new StringBuilder("Hello");
			for (int i = 0; i < args.length; i++) {
				helloStr.append(", " + args[i]);
				if (i == args.length - 1) {
					helloStr.append(".");
				}
			}
			output.output(helloStr.toString());
		}

		ThreadUtil.calcExecTime(Thread.currentThread().getName(), startTime);
	}

}
