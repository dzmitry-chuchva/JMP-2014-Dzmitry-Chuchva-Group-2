package com.epam.cdp.java.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.epam.cdp.java.multithreading.output.BufferedFileOutput;
import com.epam.cdp.java.multithreading.output.IOutput;
import com.epam.cdp.java.multithreading.util.ThreadUtil;

public class Main {

	public static void main(String[] args) {

		Long startTime = System.currentTimeMillis();

		try {
			// options format check
			if (args.length > 0 && !args[0].substring(0, 1).matches("-")) {
				throw new IllegalArgumentException();
			}

			Scanner sc = new Scanner(System.in);

			List<Integer> modes = new ArrayList<Integer>();
			modes.add(1);
			modes.add(2);

			Integer choosenMode = 0;

			System.out.println("Choose options execution mode: ");
			System.out.println("Parallel mode		: 1");
			System.out.println("Not parallel mode	: 2");

			while (sc.hasNext()) {
				try {
					choosenMode = Integer.parseInt(sc.next());
					if (!modes.contains(choosenMode)) {
						System.out.println("Avalible execution modes are 1 and 2.");
					} else {
						break;
					}
				} catch (NumberFormatException e) {
					System.out.println("Value should be a number.");
				}
			}

			System.out.println("You are chosen" + (choosenMode == 1 ? " parallel mode." : " not parallel mode."));

			ThreadUtil util = new ThreadUtil();
			List<Thread> threadList = util.initThreadList(args);

			switch (choosenMode) {
			// run in parallel mode
			case 1:
				for (Thread thread : threadList) {
					thread.start();
				}
				for (Thread thread : threadList) {
					thread.join();
				}
				break;
			// run in not parallel mode
			case 2:
				for (Thread thread : threadList) {
					thread.start();
					thread.join();
				}
				break;
			default:
				break;
			}

		} catch (IllegalArgumentException e1) {
			System.out.println("Command line option should follows format: -optionname [<argument1> <argument2> ... <argumentN>]");
		} catch (InterruptedException e2) {
			System.out.println("Option execution is interrupted.");
			e2.printStackTrace();
		}

		ThreadUtil.calcExecTime(Thread.currentThread().getName(), startTime);
		ThreadUtil.printStatistics();

		IOutput out = BufferedFileOutput.getInstance();
		out.flush();

		System.out.println("App is finished.");
	}
}
