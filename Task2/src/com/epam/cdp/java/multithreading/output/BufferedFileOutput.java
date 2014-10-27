package com.epam.cdp.java.multithreading.output;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferedFileOutput implements IOutput {

	private static final String FILE_PATH = "execution_results";
	private static final BufferedFileOutput INSTANCE = new BufferedFileOutput(3);
	private final Lock lock = new ReentrantLock();

	private String[] buffer;
	private int capacity;
	private int length;

	public static BufferedFileOutput getInstance() {
		return INSTANCE;
	}

	private BufferedFileOutput(int capacity) {
		this.capacity = capacity;
		this.buffer = new String[capacity];
		this.length = 0;
	}

	@Override
	public void output(String str) {
		try {
			lock.lock();
			if (length == capacity) {
				flush();
			}
			buffer[length] = str;
			length++;
		} finally {
			lock.unlock();
		}

	}

	@Override
	public void flush() {
		try {
			lock.lock();
			Writer fw = new FileWriter(FILE_PATH, true);
			for (int i = 0; i < buffer.length; i++) {
				if (buffer[i] != null) {
					fw.write(buffer[i] + '\n');
				}
			}
			fw.close();
			length = 0;
			buffer = new String[capacity];
		} catch (IOException e) {
			System.out.println("Buffer error. File for output is not avalible.");
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

}
