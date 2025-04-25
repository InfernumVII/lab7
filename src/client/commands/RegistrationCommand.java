package client.commands;

import java.io.Console;
import java.math.BigInteger;

import network.utility.SHA1;

public class RegistrationCommand implements Command {
	private String authKey;
	
	@Override
	public Object execute(Object arg) {
		//TOOD авторизация/регистрация
		Console cnsl = System.console();
        String str = cnsl.readLine("Enter username: "); 
        char[] ch = cnsl.readPassword("Enter password: ");
		String username = SHA1.getSHA1String(new String(str)); //TODO не хешировать
		authKey = SHA1.getSHA1String(String.format("%s:%s", str, new String(ch))); //TODO хешировать на сервере
		//TODO посолить поперчить
        return username;
		//Запретить второй auth ()
		
	}

	public String getAuthKey(){
		return authKey;
	}

	@Override
	public boolean isHasArgs() {
		return false;
	}
    
}
