package com.polishchuk;

import com.polishchuk.models.UserRequest;
import com.polishchuk.models.UserSession;
import com.polishchuk.servises.EnumTelegramService;
import com.polishchuk.servises.PropertiesReader;
import com.polishchuk.servises.UserSessionService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

// Головний клас бота, який наслідує TelegramLongPollingBot для обробки оновлень через довге опитування.
public class KMAssistantBot extends TelegramLongPollingBot {

	// Сервіс для управління сесіями користувачів, синглтон.
	private final UserSessionService userSessionService = UserSessionService.getInstance();

	// Диспетчер для маршрутизації запитів користувачів до відповідних обробників, синглтон.
	private final Dispatcher dispatcher = Dispatcher.getInstance();

	// Головний метод для запуску бота.
	public static void main(String[] args) throws TelegramApiException {
		// Створення і реєстрація бота в API Telegram.
		TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
		api.registerBot(new KMAssistantBot());
	}

	// Метод для обробки отриманих повідомлень/команд від користувачів.
	@Override
	public void onUpdateReceived(Update update) {
		// Отримання ID чату для ідентифікації сесії користувача.
		Long chatId = EnumTelegramService.INSTANCE.getChatId(update);
		// Отримання або створення сесії для користувача.
		UserSession userSession = userSessionService.getSession(chatId);
		// Створення запиту користувача з оновленням та сесією.
		UserRequest userRequest = UserRequest.builder()
				.update(update)
				.userSession(userSession)
				.build();

		// Делегування запиту користувача диспетчеру для обробки.
		boolean dispatch = dispatcher.dispatch(userRequest);
	}

	// Метод для отримання імені бота, яке використовується в Telegram.
	@Override
	public String getBotUsername() {
		// Читання імені бота з конфігураційних налаштувань.
		return PropertiesReader.getInstance().getProperty("telegram.bot_username");
	}

	// Метод для отримання токену бота, необхідного для авторизації в API Telegram.
	@Override
	public String getBotToken() {
		// Читання токену бота з конфігураційних налаштувань.
		return PropertiesReader.getInstance().getProperty("telegram.bot_token");
	}
}
