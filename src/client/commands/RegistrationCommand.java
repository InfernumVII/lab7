package client.commands;

import java.io.Console;
import java.math.BigInteger;

import collection.User;
import network.utility.SHA1;

public class RegistrationCommand implements Command {
	private User user;
	
	@Override
	public Object execute(Object arg) {
		//TOOD авторизация/регистрация
		Console console = System.console();
		String login;
		do {
			login = console.readLine("Введите логин: "); 
		} while (!validateLogin(login));
		char[] passwordChars;
		do {
			passwordChars = console.readPassword("Введите пароль: ");
		} while (!validatePassword(passwordChars));
		//String username = SHA1.getSHA1String(new String(login)); 
		//authKey = SHA1.getSHA1String(String.format("%s:%s", login, new String(passwordChars))); //TODO хешировать на сервере
		//TODO посолить поперчить
		user = new User(login, new String(passwordChars));
        return user;
		//Запретить второй auth ()
		
	}

	private boolean validateLogin(String username){
		
		if (username.length() < 4){
			System.err.println("Минимальная длина логина 4 символа");
			return false;
		} else {
			if (username.length() > 20){
				System.err.println("Максимальная длина логина 20 символов");
				return false;
			}
		}
		return true;
	}

	private boolean validatePassword(char[] passwordChars){
		String password = new String(passwordChars);
		if (password.length() < 4){
			System.err.println("Минимальная длина пароля 4 символа");
			return false;
		} else {
			if (password.length() > 100){
				System.err.println("Максимальная длина пароля 100 символов");
				return false;
			}
		}
		return true;
	}

	public User getUser(){
		return user;
	}

	@Override
	public boolean isHasArgs() {
		return false;
	}
    
}
