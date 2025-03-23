package server;

import java.io.IOException;

import network.Settings;
import network.exceptions.TimeOutException;
import server.local.ServerTerminal;

public class ServerMain {
    public static void main(String[] args) {

        try {
            Settings settings = new ServerSettings();
            ServerUdpNetwork server = new ServerUdpNetwork(settings);
            ServerTerminal sTerminal = new ServerTerminal(server.getDragonManager());
            Runtime.getRuntime().addShutdownHook(new ServerShutDownThread(sTerminal.getCommandManager())); // https://stackoverflow.com/questions/5124439/java-console-program-and-ctrl-c
            ServerTerminalThread sTerminalThread = new ServerTerminalThread(sTerminal);
            
            sTerminalThread.start();
            server.start(true);
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        } 
    }
}
