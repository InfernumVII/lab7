package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Scanner;

import managers.CommandManager;
import managers.DragonManager;
import newcommands.HelpCommand;
import server.ServerApp;
import server.ServerSettings;
import temp.Command;
import temp.Answer;
import temp.Settings;
import temp.UdpNetwork;

public class ClientApp extends UdpNetwork {
    private Scanner scanner;

    public ClientApp(Settings settings){
        scanner = new Scanner(System.in);
        try {
            inetSocketAddress = getSocketAddress(settings);
            datagramChannel = createDatagramChannel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
    public Scanner getScanner(){
        return scanner;
    }
    public static void main(String[] args) {
        Settings settings = new ClientSettings();
        ClientApp client = new ClientApp(settings);
        
        while (true) {
            System.out.print("> ");
            String in = client.getScanner().nextLine().trim();
            String[] parsedCommand = CommandManager.parseCommand(in);
            
            if (parsedCommand != null){
                try {
                    client.sendObject(new Command(parsedCommand[0], parsedCommand[1]));
                    Answer serverAnswer = client.handleAnswer();
                    System.out.println(serverAnswer.answer());
                    
                    
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }





    

}
