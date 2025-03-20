package client;

import java.io.IOException;

import javax.management.RuntimeErrorException;

import network.Settings;
import network.exceptions.TimeOutException;
import server.managers.exceptions.ParseCommandException;

public class Main {
    public static void main(String[] args) {
        Settings settings = new ClientSettings();

        ClientUdpNetwork client;
        try {
            client = new ClientUdpNetwork(settings);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        NetTerminal terminal = new NetTerminal(client);
        try {
            terminal.start(true);
        } catch (ClassNotFoundException | ParseCommandException | TimeOutException e) {
            System.out.println(e.getMessage());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
