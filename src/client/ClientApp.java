package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

import managers.CommandManager;
import server.ServerApp;
import server.ServerSettings;
import temp.Command;
import temp.Settings;
import temp.UdpNetwork;

public class ClientApp extends UdpNetwork {

    public ClientApp(Settings settings){
        try {
            inetSocketAddress = getSocketAddress(settings);
            datagramChannel = createDatagramChannel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
    public static void main(String[] args) {
        Settings settings = new ClientSettings();
        UdpNetwork client = new ClientApp(settings);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String in = scanner.nextLine().trim();
            String[] parsedCommand = CommandManager.parseCommand(in);
            
            if (parsedCommand != null){
                try {
                    client.sendObject(new Command(parsedCommand[0], parsedCommand[1]));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    

}
