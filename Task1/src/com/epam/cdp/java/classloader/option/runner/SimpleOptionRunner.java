package com.epam.cdp.java.classloader.option.runner;

import com.epam.cdp.java.classloader.task1.IOption;

public class SimpleOptionRunner {
	private static final SimpleOptionRunner INSTANCE = new SimpleOptionRunner();

	public static SimpleOptionRunner getInstance() {
		return INSTANCE;
	}

	private SimpleOptionRunner() {
	}

	public void runOption(IOption option, Object args[]) {
		option.run(args);
	}
}
