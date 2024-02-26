package com.polishchuk.singleton;

public class EagerInitializedSingleton {

	// Екземпляр класу створюэться під час завантаження класу
	private static final EagerInitializedSingleton INSTANCE = new EagerInitializedSingleton();

	// Приватний конструктор, щоб ніхто інший не створив об'єкт цього класу
	private EagerInitializedSingleton() {

	}

	public static EagerInitializedSingleton getInstance() {
		return INSTANCE;
	}
}
