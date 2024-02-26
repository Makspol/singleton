package com.polishchuk.singleton;

public class Singleton {

	// Екземпляр класу створюэться під час завантаження класу
	private static final Singleton INSTANCE = new Singleton();

	// Приватний конструктор, щоб ніхто інший не створив об'єкт цього класу
	private Singleton() {

	}

	public static Singleton getInstance() {
		return INSTANCE;
	}
}
