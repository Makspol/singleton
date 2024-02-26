package com.polishchuk.servises;

import com.polishchuk.senders.BotSender;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

// Клас TelegramService використовується для взаємодії з Telegram API через бібліотеку telegrambots.
// Він надає методи для відправлення повідомлень, зображень, стікерів та анімацій,
// а також для завантаження файлів від користувачів.
public class TelegramService {

	// Єдиний екземпляр класу TelegramService для реалізації шаблону Singleton.
	private static final TelegramService INSTANCE = new TelegramService();
	// Посилання на екземпляр BotSender для відправлення повідомлень через Telegram API.
	private final BotSender botSender;
	// Шлях до ресурсів додатку, отриманий з конфігураційного файлу.
	private final String RESOURCES_PATH = PropertiesReader.getInstance().getProperty("app.resources");

	// Приватний конструктор, що ініціалізує botSender.
	private TelegramService() {
		this.botSender = BotSender.getInstance();
	}

	// Статичний метод для отримання екземпляра TelegramService.
	public static TelegramService getInstance() {
		return INSTANCE;
	}

	// Метод для отримання chatId з оновлення (update) Telegram.
	public Long getChatId(Update update) {
		if (update.hasMessage())
			return update.getMessage().getFrom().getId();

		if (update.hasCallbackQuery())
			return update.getCallbackQuery().getFrom().getId();

		return null;
	}

	// Методи для відправлення текстових повідомлень з опціональними кнопками.

	// Відправлення текстового повідомлення без кнопок.
	public boolean SendMessage(Long chatId, String text) {
		return SendMessage(createMessage(chatId, text));
	}

	// Відправлення текстового повідомлення з кнопками.
	public boolean SendMessage(Long chatId, String text, Map<String, String> buttons) {
		SendMessage message = createMessage(chatId, text);
		attachButtons(message, buttons);

		return SendMessage(message);
	}

	// Спроба відправити повідомлення асинхронно і обробка виключень.
	private boolean SendMessage(SendMessage message) {
		try {
			botSender.executeAsync(message);
			return true;
		} catch (TelegramApiException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Створення об'єкта повідомлення з текстом і налаштуваннями.
	private SendMessage createMessage(Long chatId, String text) {
		SendMessage message = new SendMessage();
		message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
		message.setParseMode(ParseMode.MARKDOWN);
		message.setChatId(chatId);

		return message;
	}

	// Додавання кнопок до повідомлення.
	private void attachButtons(SendMessage message, Map<String, String> buttons) {
		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

		List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

		for (String buttonName: buttons.keySet()) {
			String buttonValue = buttons.get(buttonName);

			InlineKeyboardButton button = new InlineKeyboardButton();
			button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
			button.setCallbackData(buttonValue);

			keyboard.add(Arrays.asList(button));
		}

		markup.setKeyboard(keyboard);
		message.setReplyMarkup(markup);
	}

	// Методи для відправлення анімацій та стікерів.

	// Відправлення зображення (GIF) за шляхом до файлу.
	public void sendImage(Long chatId, String name) {
		SendAnimation animation = new SendAnimation();
		animation.setAnimation(new InputFile().setMedia(new File(
				RESOURCES_PATH + "gifs/" + name + ".gif"
		)));

		animation.setChatId(chatId);

		botSender.executeAsync(animation);
	}

	// Відправлення стікера за ідентифікатором файлу.
	public void sendSticker(Long chatId, String sticker_file_id) {
		SendAnimation animation = new SendAnimation();
		animation.setAnimation(new InputFile().setMedia(sticker_file_id));

		animation.setChatId(chatId);

		botSender.executeAsync(animation);
	}

	// Методи для завантаження файлів від користувачів.

	// Завантаження файлу як об'єкта File.
	public File downloadFile(@NonNull String fileId, @NonNull String fileOutput) throws TelegramApiException {

		GetFile getFile = new GetFile(fileId);
		org.telegram.telegrambots.meta.api.objects.File file = botSender.execute(getFile);

		return botSender.downloadFile(file, new File(fileOutput));
	}

	// Завантаження файлу як потоку даних (InputStream).
	public InputStream downloadFile(@NonNull String fileId) throws TelegramApiException {

		GetFile getFile = new GetFile(fileId);
		org.telegram.telegrambots.meta.api.objects.File file = botSender.execute(getFile);

		return botSender.downloadFileAsStream(file);
	}
}