package client.commands;

import java.io.Console;

import network.utility.SHA1;

public class RegistrationCommand implements Command {
	private String authKey;
	
	@Override
	public Object execute(Object arg) {
		Console cnsl = System.console();
        String str = cnsl.readLine("Enter username: "); 
        char[] ch = cnsl.readPassword("Enter password: ");
		authKey = SHA1.getSHA1String(String.format("%s:%s", str, ch));
        return authKey;
	}

	public String getAuthKey(){
		return authKey;
	}

	@Override
	public boolean isHasArgs() {
		return false;
	}
    
}
