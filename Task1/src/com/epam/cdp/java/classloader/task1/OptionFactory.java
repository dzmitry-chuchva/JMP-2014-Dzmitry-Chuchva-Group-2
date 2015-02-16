package com.epam.cdp.java.classloader.task1;

import com.epam.cdp.java.classloader.task1.impl.SayHelloOption;
import com.epam.cdp.java.classloader.task1.impl.SendMailOption;
import com.epam.cdp.java.classloader.task1.impl.SumOption;

public class OptionFactory {
	public static IOption buildOption(OptionType type) {

		IOption option = null;
		switch (type) {
		case SAY_HEELO:
			option = new SayHelloOption();
			break;
		case SEND_MAIL:
			option = new SendMailOption();
			break;
		case SUM:
			option = new SumOption();
			break;
		default:
			break;
		}

		return option;
	}
}
