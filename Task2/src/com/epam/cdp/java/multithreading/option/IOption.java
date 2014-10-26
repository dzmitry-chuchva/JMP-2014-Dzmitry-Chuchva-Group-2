package com.epam.cdp.java.multithreading.option;

import com.epam.cdp.java.multithreading.output.IOutput;

public interface IOption {
	void run(Object[] args, IOutput output) throws IllegalArgumentException;
}
