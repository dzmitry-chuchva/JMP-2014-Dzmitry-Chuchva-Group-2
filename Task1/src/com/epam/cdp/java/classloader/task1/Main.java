package com.epam.cdp.java.classloader.task1;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Main {
	
	private static final String libPath = "..\\lib\\options.jar";
	
	public static void main(String[] args) {

		try {

			if (args.length > 0 && !args[0].substring(0, 1).matches("-")) {
				throw new IllegalArgumentException();
			}

			ResourceBundle rb = ResourceBundle.getBundle("resources.config");
			StringBuilder curOption = new StringBuilder();
			List<String> curOptionParams = new ArrayList<String>();

			for (int i = 0; i < args.length; i++) {
				String arg = args[i];
				// if starts with "-" it is option otherwise it is option
				// parameter
				if (arg.substring(0, 1).matches("-")) {
					// execute option only if it is not first elem
					if (curOption.length() != 0) {
						executeOption(curOption, curOptionParams, rb);
						curOption.setLength(0);
						curOptionParams.clear();
					}
					curOption.append(arg.substring(1, arg.length()));
				} else {
					curOptionParams.add(arg);
				}

				// if last iteration
				if (i == args.length - 1) {
					executeOption(curOption, curOptionParams, rb);
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Command line option should follows format: -optionname [<argument1> <argument2> ... <argumentN>]");
		}

	}

	private static void executeOption(StringBuilder curOption, List<String> curOptionParams, ResourceBundle rb) {
		String className = rb.getString(curOption.toString());
		String[] paramsArr = new String[curOptionParams.size()];
		paramsArr = curOptionParams.toArray(paramsArr);
		try {
			loadAndRunClass(className, paramsArr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadAndRunClass(String className, Object[] params) throws Exception {
		File file = new File(libPath);
		URL url = file.toURI().toURL();
		URL[] urls = { url };

		URLClassLoader loader = new URLClassLoader(urls);
		Class<?> cls = loader.loadClass(className);

		Method method = cls.getDeclaredMethod("run", new Class[] { Object[].class });
		Object obj = cls.newInstance();
		try {
			method.invoke(obj, new Object[] { params });
		} catch (InvocationTargetException e) {
			System.out.println("Please, provide correct values according to the message.");
		}

		loader.close();
	}

}
