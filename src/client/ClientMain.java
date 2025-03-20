package client;

import network.Settings;
import network.UdpNetwork;
import network.exceptions.TimeOutException;
import server.managers.exceptions.ParseCommandException;

public class ClientMain {
    public static void main(String[] args) {
        try {
            Settings settings = new ClientSettings();
            //TODO починить наследование UdpNetwork client = new ClientUdpNetwork(settings);
            ClientUdpNetwork client = new ClientUdpNetwork(settings);
            NetTerminal terminal = new NetTerminal(client);
            terminal.start(true);
        } catch (ClassNotFoundException | ParseCommandException | TimeOutException e) {
            System.out.println(e.getMessage());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
