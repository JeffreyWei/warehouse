package com.wei.warehouse.lang;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 用于加载当前环境之外的资源
 * Created by wei on 16/8/17.
 */
public class ChangeClassLoader {
	public static void main(String[] args) {
		showCurrentClassLoader();

//		method1();
//		method2();
		method3();


		showCurrentClassLoader();


	}

	public static void addURL(URL url) throws Exception {
		URLClassLoader classLoader
				= (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class clazz = URLClassLoader.class;

		// Use reflection
		Method method = clazz.getDeclaredMethod("addURL", new Class[]{URL.class});
		method.setAccessible(true);
		method.invoke(classLoader, new Object[]{url});
	}

	private static void method3() {
		try {
			addURL(new File("/Users/wei/work/code/java/warehouse/lang/src/other_cnfig").toURL());
		} catch (Exception e) {
			e.printStackTrace();
		}
// This should work now!
		Thread.currentThread().getContextClassLoader().getResourceAsStream("context.xml");
		URL resource = Thread.currentThread().getContextClassLoader().getResource("ex_app.xml");
		System.out.println(resource);
	}

	private static void method2() {

		ClassLoader currentThreadClassLoader = Thread.currentThread().getContextClassLoader();

// Add the conf dir to the classpath
// Chain the current thread classloader
		URLClassLoader urlClassLoader
				= null;
		try {
			urlClassLoader = new URLClassLoader(new URL[]{new File("/Users/wei/work/code/java/warehouse/lang/src/other_cnfig").toURL()},
					currentThreadClassLoader);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

// Replace the thread classloader - assumes
// you have permissions to do so
		Thread.currentThread().setContextClassLoader(urlClassLoader);
		URL resource = urlClassLoader.getResource("ex_app.xml");
		System.out.println(resource);

	}

	private static void method1() {
		URL[] url = new URL[0];
		try {
			url = new URL[]{new URL("file:///Users/wei/work/code/java/warehouse/lang/src/other_cnfig/")};
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		URLClassLoader loader = new URLClassLoader(url);
		URL resource = loader.getResource("ex_app.xml");
		System.out.println(resource);
	}

	private static void showCurrentClassLoader() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource("app.xml");
		System.out.println(url.toString());
	}

}
