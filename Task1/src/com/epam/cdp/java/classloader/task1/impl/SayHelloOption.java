package com.epam.cdp.java.classloader.task1.impl;

import com.epam.cdp.java.classloader.task1.IOption;

public class SayHelloOption implements IOption {

	@Override
	public void run(Object[] args) throws IllegalArgumentException {
		if (args.length == 0) {
			System.out.println("Hello, user.");
		} else {
			StringBuilder helloStr = new StringBuilder("Hello");
			for (int i = 0; i < args.length; i++) {
				helloStr.append(", " + args[i]);
				if (i == args.length - 1) {
					helloStr.append(".");
				}
			}
			System.out.println(helloStr);
		}
	}

}
