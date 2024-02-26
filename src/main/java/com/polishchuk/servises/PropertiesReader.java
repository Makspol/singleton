package com.polishchuk.servises;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// Клас для читання властивостей з файлу конфігурації.

// Використовується шаблон Singleton для гарантії того, що клас матиме лише один екземпляр в додатку,
// що дозволяє централізовано управляти доступом до конфігураційних налаштувань.
public class PropertiesReader extends Properties {

	// Статична змінна для зберігання єдиного екземпляра класу.
	private static PropertiesReader INSTANCE;

	// Приватний конструктор запобігає створенню екземплярів класу ззовні.
	private PropertiesReader() {
		super();
	}

	// Статичний блок ініціалізації для створення екземпляра і завантаження властивостей.
	static {
		try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
			INSTANCE = new PropertiesReader();
			INSTANCE.load(fis); // Завантаження властивостей з файлу.

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Публічний статичний метод для отримання екземпляра класу.
	public static PropertiesReader getInstance() {
		return INSTANCE;
	}
}
