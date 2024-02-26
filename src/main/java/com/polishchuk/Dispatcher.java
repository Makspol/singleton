package com.polishchuk;

import com.polishchuk.handlers.HandlersFactory;
import com.polishchuk.handlers.UserRequestHandler;
import com.polishchuk.models.UserRequest;

import java.util.Set;

// Клас для маршрутизації запитів користувачів.
public class Dispatcher {

	// Єдиний екземпляр класу Dispatcher.
	private static Dispatcher INSTANCE;
	// Множина обробників запитів.
	private final Set<UserRequestHandler> handlers;

	// Приватний конструктор для ініціалізації обробників.
	private Dispatcher() {
		this.handlers = HandlersFactory.getHandlers();
	}

	// Перший варіант отримання екземпляра класу з повною синхронізацією.
	// Ключове слово synchronized у Java використовується для забезпечення того,
	// що лише один потік одночасно може виконувати певний блок коду.
	public static synchronized Dispatcher getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new Dispatcher();
		}

		return INSTANCE;
	}

	// Другий варіант отримання екземпляра класу з подвійною перевіркою (Double-Checked Locking).
	public static Dispatcher getInstance2() {

		// Перевірка чи існує екземпляр
		if (INSTANCE == null) {

			// Якщо екземпляр не існує, виконання доходить до синхронізованого блоку.

			// Синхронізація на рівні класу гарантує, що в будь-який момент часу тільки один потік
			// може виконувати код всередині цього блоку. Це запобігає створенню багатьох екземплярів
			// INSTANCE у багатопоточному середовищі.
			synchronized (Dispatcher.class) {

				// Після входу в синхронізований блок метод знову перевіряє,
				// чи не був екземпляр INSTANCE створений іншим потоком,
				// поки поточний потік чекав на вхід до синхронізованого блоку.
				// Якщо екземпляр все ще не існує, метод створює його.
				if (INSTANCE == null) {
					INSTANCE = new Dispatcher();
				}
			}
		}

		return INSTANCE;
	}

	// Метод для маршрутизації запитів до відповідних обробників.
	public boolean dispatch(UserRequest userRequest) {

		for (UserRequestHandler handler : handlers) {

			if (handler.isApplicable(userRequest)) {
				handler.handle(userRequest);
				return true;
			}
		}

		return false;
	}
}
