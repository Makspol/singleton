package com.polishchuk.handlers;

import com.polishchuk.enums.Command;
import com.polishchuk.models.UserRequest;

import java.util.Arrays;
import java.util.Random;

// Цей клас CmdWithParamHandler є конкретним обробником, який наслідується від
// абстрактного класу UserRequestHandler. Він призначений для обробки команд із параметрами,
// зокрема, для генерації випадкового числа в заданому діапазоні.
public class CmdWithParamHandler extends UserRequestHandler {

	private static final Command[] commands = new Command[] {Command.generate};
	private static final Random random = new Random();

	@Override
	public boolean isApplicable(UserRequest userRequest) {
		return isCommand(userRequest.getUpdate().getMessage().getText().split(" ")[0], commands);
	}

	@Override
	public void handle(UserRequest userRequest) {

		String[] params = userRequest.getUpdate().getMessage().getText().split(" ");

		Command command = Command.valueOf(params[0].substring(1));

		params = Arrays.stream(params).skip(1).toArray(String[]::new);

		switch (command) {

			case generate -> generate(userRequest, params);
		}
	}

	private void generate(UserRequest userRequest, String[] params) {
		int min = 1, max = 100, res = 0;
		int i = 0;

		for (String param : params) {
			try {
				switch (i) {
					case 0: min = Integer.parseInt(param);
					case 1: max = Integer.parseInt(param);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			i++;
		}
		res = random.nextInt(min, max + 1);

		StringBuilder sb = new StringBuilder()
				.append("*Random Number Generator*\n")
				.append("_Min: ").append(min).append("_\n")
				.append("_Max: ").append(max).append("_\n")
				.append("Result: ").append(res);

		telegramService.SendMessage(userRequest.getChatId(), sb.toString());
	}
}
