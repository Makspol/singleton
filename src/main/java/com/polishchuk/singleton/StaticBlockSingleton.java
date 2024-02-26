package com.polishchuk.singleton;

public class StaticBlockSingleton {

	private static StaticBlockSingleton INSTANCE;

	private StaticBlockSingleton() {

	}

	static {
		try {
			INSTANCE = new StaticBlockSingleton();
		} catch (Exception e) {
			throw new RuntimeException("Exception when creating a single instance");
		}
	}

	public static StaticBlockSingleton getInstance() {
		return INSTANCE;
	}
}
