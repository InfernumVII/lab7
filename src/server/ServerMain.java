package server;

import network.Settings;
import network.exceptions.TimeOutException;

public class ServerMain {
    public static void main(String[] args) {
        try {
            Settings settings = new ServerSettings();
            ServerUdpNetwork server = new ServerUdpNetwork(settings);
            server.initCommandManager();
            server.start(true);
        } catch (ClassNotFoundException | TimeOutException e) {
            System.out.println(e.getMessage());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
