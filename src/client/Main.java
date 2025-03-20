package client;

import network.Settings;

public class Main {
    public static void main(String[] args) {
        Settings settings = new ClientSettings();

        ClientApp clientApp;
        try {
            clientApp = new ClientApp(settings);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        
        NetTerminal terminal = new NetTerminal(clientApp);
        terminal.start(true);
    }
}
