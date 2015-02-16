package com.epam.cdp.java.classloader.task1;

public enum OptionType {
	SAY_HEELO, SEND_MAIL, SUM;

	public static OptionType getTypeFromString(String optionName) {
		OptionType type = null;

		switch (optionName) {
		case "sum":
			type = SUM;
			break;
		case "sendmail":
			type = SEND_MAIL;
			break;
		case "sayhello":
			type = SAY_HEELO;
			break;
		default:
			throw new IllegalArgumentException();
		}

		return type;
	}
}
