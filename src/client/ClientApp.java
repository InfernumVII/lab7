package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

import clientCommands.PromptForEnumCommand;
import clientCommands.PromptForFloatCommand;
import clientCommands.PromptForLongCommand;
import clientCommands.PromtForStringCommand;
import managers.ClientCommandManager;
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
        ClientCommandManager clientCommandManager = new ClientCommandManager();
        ClientApp.initCommands(clientCommandManager, client);
        
        while (true) {
            System.out.print("> ");
            String in = client.getScanner().nextLine().trim();
            String[] parsedCommand = ClientCommandManager.parseCommand(in);
            
            if (parsedCommand != null){
                try {
                    client.sendObject(new Command(parsedCommand[0], parsedCommand[1]));
                    Answer serverAnswer = client.handleAnswer();
                    if (serverAnswer.answer() != null){
                        System.out.println(serverAnswer.answer());
                    } else {
                        Answer answer = new Answer(clientCommandManager.executeCommand(serverAnswer.command().command(), serverAnswer.command().args()), null);
                        client.sendObject(answer);
                        Answer serverAnswer2 = client.handleAnswer();
                        Answer answer2 = new Answer(clientCommandManager.executeCommand(serverAnswer2.command().command(), serverAnswer2.command().args()), null);
                        client.sendObject(answer2);
                    }
                    
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    private static void initCommands(ClientCommandManager manager, ClientApp clientApp){
        manager.registerCommand("promtForString", new PromtForStringCommand(clientApp));
        manager.registerCommand("promtForLong", new PromptForLongCommand(clientApp));
        manager.registerCommand("promtForFloat", new PromptForFloatCommand(clientApp));
        manager.registerCommand("promtForEnum", new PromptForEnumCommand(clientApp));
    }


    

}
