package com.epam.cdp.java.classloader.task1;

public class OptionRunner {
	private static OptionRunner INSTANCE = new OptionRunner();

	public static OptionRunner getInstance() {
		return INSTANCE;
	}

	private OptionRunner() {
	}

	public void runOption(IOption option, Object args[]) {
		option.run(args);
	}
}
