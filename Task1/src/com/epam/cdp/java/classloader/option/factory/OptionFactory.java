package com.epam.cdp.java.classloader.option.factory;

import com.epam.cdp.java.classloader.task1.IOption;

public interface OptionFactory {
	public IOption buildOption(String optionName);
}
