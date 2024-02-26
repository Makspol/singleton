package com.polishchuk.singleton;

import java.io.Serial;
import java.io.Serializable;

public class SerializedSingleton2 implements Serializable {

	private static final long serialVersionUID = -7604766932017737115L;

	private SerializedSingleton2(){

	}

	private static class SingletonHelper {
		private static final SerializedSingleton2 INSTANCE = new SerializedSingleton2();
	}

	public static SerializedSingleton2 getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Serial
	protected Object readResolve() {
		return getInstance();
	}
}
