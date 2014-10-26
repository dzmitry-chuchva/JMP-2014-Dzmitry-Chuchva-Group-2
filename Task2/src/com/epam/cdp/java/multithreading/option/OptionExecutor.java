package com.epam.cdp.java.multithreading.option;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Properties;

public class OptionExecutor {

	private static final String LIB_PATH = "lib\\options.jar";
	private static final String CONFIG_PATH = "resources\\config";

	public void execute(String optionName, List<String> paramsList) {
		try {
			String className = getOptionClassName(optionName);
			String[] paramsArr = new String[paramsList.size()];
			paramsArr = paramsList.toArray(paramsArr);
			loadAndRunOption(className, paramsArr);
		} catch (IOException e1) {
			System.out.println("Configuration error. Option '" + optionName + "' is not executed.");
			e1.printStackTrace();
		} catch (Exception e2) {
			System.out.println("Application error. Option '" + optionName + "' is not executed.");
			e2.printStackTrace();
		}
	}

	private void loadAndRunOption(String className, Object[] params) throws Exception {
		File file = new File(LIB_PATH);
		URL url = file.toURI().toURL();
		URL[] urls = { url };

		URLClassLoader loader = new URLClassLoader(urls);
		Class<?> cls = loader.loadClass(className);

		Method method = cls.getDeclaredMethod("run", new Class[] { Object[].class });
		Object obj = cls.newInstance();
		method.invoke(obj, new Object[] { params });

		loader.close();
	}

	private String getOptionClassName(String optionName) throws IOException {
		InputStream is = new FileInputStream(CONFIG_PATH);
		Properties prop = new Properties();
		prop.load(is);
		String className = prop.get(optionName).toString();
		return className;
	}
}
