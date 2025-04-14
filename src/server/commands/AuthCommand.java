package server.commands;

import server.managers.ServerCommandManager;
import server.psql.auth.Auth;

public class AuthCommand implements Command {
	@Override
	public Object execute(Object arg, String authKey) {
		String validkeyMess = "Авторизация успешна";
		Auth auth = ServerCommandManager.getAuthInstance();
		if (auth.keyIsExists(authKey)){
			return validkeyMess;
		} else {
			if (auth.insertKey(authKey))
				return validkeyMess;
		}
		return "Ошибка авторизации";
		
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
