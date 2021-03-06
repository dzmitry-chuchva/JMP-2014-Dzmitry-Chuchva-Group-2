package com.epam.cdp.java.multithreading.option.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.epam.cdp.java.multithreading.option.IOption;
import com.epam.cdp.java.multithreading.output.IOutput;
import com.epam.cdp.java.multithreading.util.ThreadUtil;

public class SendMailOption implements IOption {

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public void run(Object[] args, IOutput output) throws IllegalArgumentException {
		Long startTime = System.currentTimeMillis();

		if (args.length == 0) {
			System.out.println("You should provide at least one email.");
			throw new IllegalArgumentException();
		} else {
			String messPostfix = args.length == 1 ? " email is sent." : " emails are sent.";
			StringBuilder confirmStr = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				if (!validateEmail(args[i].toString())) {
					System.out.println("You should provide email in correct format like 'email@example.com'.");
					throw new IllegalArgumentException();
				}
				confirmStr.append(args[i]);
				if (i != args.length - 1) {
					confirmStr.append(", ");
				} else {
					confirmStr.append(messPostfix);
				}
			}
			output.output(confirmStr.toString());
		}

		ThreadUtil.calcExecTime(Thread.currentThread().getName(), startTime);
	}

	private boolean validateEmail(String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

}