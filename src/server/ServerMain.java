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
            server.initCommandManager();
            server.getDragonManager().addDragonsFromDragonFileEnv();

            ServerTerminal sTerminal = new ServerTerminal(server.getDragonManager());
            ServerTerminalThread sTerminalThread = new ServerTerminalThread(sTerminal);
            sTerminalThread.start();
            
            
            server.start(true);
        } catch (ClassNotFoundException | TimeOutException e) {
            System.out.println(e.getMessage());
        } catch (IOException e){
            throw new RuntimeException(e);
        } 
    }
}
