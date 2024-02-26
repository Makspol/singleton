package com.polishchuk.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

// Цей клас UserRequest представляє модель запиту користувача в контексті Telegram бота.
// Він включає інформацію про оновлення (Update), отримане від Telegram,
// а також про сесію користувача (UserSession), що дозволяє асоціювати запит з конкретним користувачем.

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRequest {

	private Update update; // Об'єкт оновлення від Telegram API.
	private UserSession userSession; // Дані сесії користувача.

	// Метод для отримання ідентифікатора чату з сесії користувача.
	public Long getChatId() {
		return userSession.getChatId();
	}
}
