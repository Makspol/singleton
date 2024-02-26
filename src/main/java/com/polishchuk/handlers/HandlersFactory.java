package com.polishchuk.handlers;

import java.util.HashSet;
import java.util.Set;

// Клас фабрика для створення та управління обробниками запитів користувачів.
public class HandlersFactory {

	// Метод для отримання набору обробників.
	public static Set<UserRequestHandler> getHandlers() {
		// Використання HashSet для зберігання обробників запитів.
		Set<UserRequestHandler> handlers = new HashSet<>();

		// Додавання конкретних обробників до набору.
		handlers.add(new StartHandler()); // Обробник для команди /start.
		handlers.add(new CmdWithParamHandler()); // Припустимо, це обробник для команд з параметрами.

		// Повернення набору обробників.
		return handlers;
	}
}
