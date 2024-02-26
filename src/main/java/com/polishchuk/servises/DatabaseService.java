package com.polishchuk.servises;

import com.polishchuk.enums.ConversationState;
import com.polishchuk.models.UserSession;
import com.polishchuk.models.entities.User;

import java.io.Serial;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

// Сервіс для взаємодії з даними користувачів.

// Цей клас імітує взаємодію з базою даних за допомогою колекції в пам'яті.
// Він використовує шаблон Singleton для забезпечення того,
// що існує лише один екземпляр DatabaseService в додатку.
// Метод findUserByChatId шукає користувача в колекції; якщо користувач не знайдений,
// він автоматично створює нового користувача та сесію для нього.
public class DatabaseService implements Serializable {

	// Статична змінна для реалізації шаблону Singleton.
	private static final DatabaseService INSTANCE = new DatabaseService();
	// Список для імітації зберігання даних користувачів.
	private final ArrayList<User> users;

	// Приватний конструктор для запобігання створенню екземплярів класу ззовні.
	private DatabaseService() {
		users = new ArrayList<>();
	}

	// Метод для отримання екземпляра сервісу (Singleton).
	public static DatabaseService getInstance() {
		return INSTANCE;
	}

	@Serial
	protected Object readResolve() {
		return getInstance();
	}

	// Метод для пошуку сесії користувача за його chatId.
	public UserSession findUserByChatId(Long chatId) {
		User user = null;

		// Перебір існуючих користувачів для пошуку збігу за chatId.
		for (User item: users) {

			if (item.getChatId().equals(chatId)) {
				user = item;
				break;
			}
		}

		// Якщо користувач не знайдений, створюється новий і додається до списку.
		if (user == null) {
			user = User.builder()
					.id(users.size()) // Встановлення ID на основі розміру списку для імітації автоінкременту.
					.chatId(chatId)
					.username("username" + users.size()) // Генерація імені користувача.
					.build();

			users.add(user);
		}

		// Створення і повернення нової сесії користувача.
		return UserSession.builder()
				.id(user.getId())
				.chatId(chatId)
				.build();
	}
}
