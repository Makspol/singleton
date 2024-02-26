package com.polishchuk.singleton;

public class ThreadSafeSingleton2 {

	private static ThreadSafeSingleton2 INSTANCE;

	private ThreadSafeSingleton2() {

	}

	public static ThreadSafeSingleton2 getInstanceUsingDoubleLocking() {

		if (INSTANCE == null) {
			synchronized (ThreadSafeSingleton2.class) {

				if (INSTANCE == null) {
					INSTANCE = new ThreadSafeSingleton2();
				}
			}
		}

		return INSTANCE;
	}
}
