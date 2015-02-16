package com.epam.cdp.java.classloader.option.factory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ResourceBundle;

import com.epam.cdp.java.classloader.task1.IOption;

public class FactoryProducer {
	private static final FactoryProducer INSTANCE = new FactoryProducer();
	private final OptionFactory fileConfigFactory = new FileConfigOptionFactory();

	public static FactoryProducer getInstance() {
		return INSTANCE;
	}

	private FactoryProducer() {
	}

	public OptionFactory getFactory(String factoryName) {
		switch (factoryName) {
		case "FILE_CONFIG":
			return fileConfigFactory;
		default:
			return null;
		}
	}

	private class FileConfigOptionFactory implements OptionFactory {
		private static final String LIB_PATH = "..\\lib\\options.jar";
		private static final String CONFIG_PATH = "resources.config";

		@Override
		public IOption buildOption(String optionName) {
			IOption option = null;
			try {
				File file = new File(LIB_PATH);
				URL url = file.toURI().toURL();
				URL[] urls = { url };

				URLClassLoader loader = new URLClassLoader(urls);
				ResourceBundle resourceBundle = ResourceBundle.getBundle(CONFIG_PATH);
				Class<?> cls = loader.loadClass(resourceBundle.getString(optionName));

				option = (IOption) cls.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return option;
		}
	}
}
