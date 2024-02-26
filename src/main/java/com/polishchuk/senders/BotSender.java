package com.polishchuk.senders;

import com.polishchuk.KMAssistantBot;
import com.polishchuk.servises.PropertiesReader;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

// Клас BotSender, який розширює DefaultAbsSender для відправлення повідомлень через Telegram API.
public class BotSender extends DefaultAbsSender {

	// Статична змінна для реалізації шаблону Singleton.
	private static BotSender INSTANCE;

	// Приватний конструктор, який приймає налаштування бота та токен.
	private BotSender(DefaultBotOptions options, String botToken) {
		super(options, botToken);
	}

	// Публічний статичний метод для отримання екземпляра BotSender.
	public static BotSender getInstance() {

		// Ініціалізація INSTANCE, якщо вона ще не була ініціалізована.
		if (INSTANCE == null) {
			INSTANCE = new BotSender(new DefaultBotOptions(), PropertiesReader.getInstance().getProperty("telegram.bot_token"));
		}

		return INSTANCE;
	}
}
