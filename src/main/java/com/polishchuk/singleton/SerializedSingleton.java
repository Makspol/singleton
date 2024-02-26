package com.polishchuk.singleton;

import java.io.Serializable;

public class SerializedSingleton implements Serializable {

	private static final long serialVersionUID = -7604766932017737115L;

	private SerializedSingleton(){

	}

	private static class SingletonHelper {
		private static final SerializedSingleton INSTANCE = new SerializedSingleton();
	}

	public static SerializedSingleton getInstance() {
		return SingletonHelper.INSTANCE;
	}
}
