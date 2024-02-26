package com.polishchuk.singleton;

public class ThreadSafeSingleton {

	private static ThreadSafeSingleton INSTANCE;

	private ThreadSafeSingleton() {

	}

	public static synchronized ThreadSafeSingleton getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new ThreadSafeSingleton();
		}

		return INSTANCE;
	}
}
