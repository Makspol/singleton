package com.polishchuk.handlers;

import com.polishchuk.enums.Command;
import com.polishchuk.models.UserRequest;
import com.polishchuk.servises.EnumTelegramService;
import org.telegram.telegrambots.meta.api.objects.Update;

// Цей абстрактний клас UserRequestHandler служить базою для створення конкретних
// обробників запитів користувачів у Telegram боті. Він визначає спільну логіку та утилітні методи,
// які можуть використовуватися різними обробниками для ідентифікації типів повідомлень або команд,
// отриманих від користувачів.

// Абстрактний клас, який слугує шаблоном для обробників запитів користувачів.
public abstract class UserRequestHandler {

	// Посилання на сервіс для взаємодії з Telegram API.
	protected final EnumTelegramService telegramService = EnumTelegramService.INSTANCE;

	// Абстрактний метод, який перевіряє, чи підходить обробник для даного запиту користувача.
	public abstract boolean isApplicable(UserRequest userRequest);
	// Абстрактний метод для обробки запиту користувача.
	public abstract void handle(UserRequest userRequest);

	// Метод для перевірки, чи є оновлення командою.
	public boolean isCommand(Update update, Command command) {
		return update.hasMessage() && isCommand(update.getMessage().getText(), command);
	}

	// Метод для перевірки, чи є оновлення однією з набору команд.
	public boolean isCommand(Update update, Command[] commands) {
		return update.hasMessage() && isCommand(update.getMessage().getText(), commands);
	}

	// Перевантажений метод для перевірки, чи рядок є конкретною командою.
	public boolean isCommand(String str, Command command) {
		return isCommand(str) && str.equals('/' + command.toString());
	}

	// Метод для перевірки, чи рядок є однією з набору команд.
	public boolean isCommand(String str, Command[] commands) {

		if (!isCommand(str))
			return false;

		for (Command command : commands)
			if (isCommand(str, command))
				return true;

		return false;
	}

	// Метод для перевірки, чи рядок є командою (починається з '/' і не містить пробілів).
	public boolean isCommand(String str) {
		return str != null && !str.isEmpty() && str.charAt(0) == '/' && !str.contains(" ");
	}

	// Методи для перевірки типів оновлень: текстові повідомлення, контакти, запити зворотнього виклику, документи.

	public boolean isTextMessage(Update update) {
		return update.hasMessage() && update.getMessage().hasText();
	}

	public boolean isTextMessage(Update update, Command command) {
		return isTextMessage(update) &&
				update.getMessage().getText().equals(command.toString());
	}

	public boolean isContact(Update update) {
		return update.hasMessage() && update.getMessage().hasContact();
	}

	public boolean isCallbackQuery(Update update) {
		return update.hasCallbackQuery();
	}

	public boolean isCallbackQuery(Update update, Command command) {
		return isCallbackQuery(update) &&
				update.getCallbackQuery().getData().equals(command.toString());
	}

	public boolean isDocument(Update update) {
		return update.hasMessage() && update.getMessage().hasDocument();
	}
}