package com.polishchuk.handlers;

import com.polishchuk.enums.Command;
import com.polishchuk.models.UserRequest;

// Клас StartHandler розширює UserRequestHandler для обробки команди /start.
public class StartHandler extends UserRequestHandler {

	// Перевіряє, чи є вхідний запит командою /start.
	@Override
	public boolean isApplicable(UserRequest userRequest) {
		return isCommand(userRequest.getUpdate(), Command.start);
	}

	// Обробляє команду /start, відсилаючи відповідне повідомлення користувачу.
	@Override
	public void handle(UserRequest userRequest) {
		String mess = "Singleton pattern";

		telegramService.SendMessage(userRequest.getChatId(), mess);
	}
}

