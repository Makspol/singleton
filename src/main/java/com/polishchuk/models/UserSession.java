package com.polishchuk.models;

import com.polishchuk.enums.ConversationState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
// Цей клас UserSession представляє сесію користувача, в якій зберігається інформація,
// необхідна для управління взаємодією користувача з системою.

// Анотація @NoArgsConstructor генерує конструктор без аргументів.
@NoArgsConstructor
// Анотація @AllArgsConstructor генерує конструктор з усіма аргументами.
@AllArgsConstructor
// Анотація @Builder використовується для генерації шаблону Builder для класу.
@Builder
// Анотація @Data генерує методи getter, setter, equals, hashCode та toString.
@Data
public class UserSession {

	private int id; // Унікальний ідентифікатор сесії користувача.
	private Long chatId; // Ідентифікатор чату з користувачем, який використовується для взаємодії з Telegram API.
}