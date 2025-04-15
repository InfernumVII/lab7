package server.commands;

import server.managers.ServerCommandManager;
import server.psql.auth.Auth;

public class AuthCommand implements Command {
	@Override
	public Object execute(Object argument, String authKey) {
		String username = (String) argument;
		Auth auth = ServerCommandManager.getAuthInstance();
		if (auth.userIsExists(username)) {
			if (auth.passwordCheck(username, authKey)) {
				return "Авторизация успешна";
			} else {
				return "Неправильный логин или пароль";
			}
		} else {
			if (auth.insertUser(username, authKey)) {
				return "Пользователь зарегистрирован";
			} else {
				return "Ошибка авторизации";
			}
		}
		
	}

	@Override
	public String getDescription() {
		return "Авторизация пользователя";
	}

	@Override
	public boolean isHasArgs() {
		return true;
	}
    
}
