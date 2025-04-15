package client.commands;

import java.io.Console;
import java.math.BigInteger;

import network.utility.SHA1;

public class RegistrationCommand implements Command {
	private String authKey;
	
	@Override
	public Object execute(Object arg) {
		Console cnsl = System.console();
        String str = cnsl.readLine("Enter username: "); 
        char[] ch = cnsl.readPassword("Enter password: ");
		String username = SHA1.getSHA1String(new String(str));
		authKey = SHA1.getSHA1String(String.format("%s:%s", str, new String(ch)));
        return username;
	}

	public String getAuthKey(){
		return authKey;
	}

	@Override
	public boolean isHasArgs() {
		return false;
	}
    
}
