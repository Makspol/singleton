package com.polishchuk.servises;

import com.polishchuk.models.UserSession;

import java.util.HashMap;
import java.util.Map;

// Клас для управління сесіями користувачів.
public class UserSessionService {

	// Посилання на єдиний екземпляр DatabaseService.
	private final DatabaseService databaseService = DatabaseService.getInstance();
	// Map для зберігання сесій користувачів, ключем є chatId.
	private Map<Long, UserSession> userSessionMap;

	// Приватний конструктор для ініціалізації Map сесій.
	private UserSessionService() {
		userSessionMap = new HashMap<>();
	}

	// Внутрішній клас, який містить єдиний екземпляр UserSessionService.
	private static class SingletonHelper {
		private static final UserSessionService INSTANCE = new UserSessionService();
	}

	// Публічний статичний метод для отримання єдиного екземпляра UserSessionService.
	public static UserSessionService getInstance() {
		return SingletonHelper.INSTANCE;
	}

	// Метод для отримання сесії користувача за chatId.
	public UserSession getSession(Long chatId) {
		UserSession userSession = userSessionMap.get(chatId);

		// Якщо сесія не знайдена, спроба завантажити дані сесії.
		if (userSession == null)
			userSession = loadSessionData(chatId);

		return userSession;
	}

	// Приватний метод для завантаження даних сесії користувача.
	private UserSession loadSessionData(Long chatId) {
		// Звернення до DatabaseService для пошуку користувача за chatId.
		return databaseService.findUserByChatId(chatId);
	}
}
