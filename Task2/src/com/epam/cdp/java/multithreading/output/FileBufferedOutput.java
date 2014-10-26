package com.epam.cdp.java.multithreading.output;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileBufferedOutput implements IOutput {

	private static String FILE_PATH = "execution_results";

	private char[] buffer;
	private int capacity;
	private int length;

	public FileBufferedOutput(int capacity) {
		this.capacity = capacity;
		this.buffer = new char[capacity];
		this.length = 0;
	}

	public FileBufferedOutput() {
		this(16);
	}

	@Override
	public void output(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (length == capacity) {
				flush();
			}
			buffer[length] = new Character(str.charAt(i));
			length ++;
		}
	}

	@Override
	public void flush() {
		try {
			Writer fw = new FileWriter(FILE_PATH);
			fw.write(buffer);
			fw.close();
			length = 0;
			buffer = new char[capacity];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return new String(buffer, 0, length);
	}

}
