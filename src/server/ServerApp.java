package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

import network.Settings;
import network.UdpNetwork;
import network.models.Answer;
import network.models.NetCommand;
import server.commands.*;
import server.managers.CommandManager;
import server.managers.DragonManager;
import server.managers.utility.CSV;
//TODO: принцип единой ответственности
public class ServerApp extends UdpNetwork {

    public ServerApp(Settings settings) throws IOException{
        inetSocketAddress = getSocketAddress(settings);
        datagramChannel = createDatagramChannel(inetSocketAddress);
    }

    
    public 
    
    public static void main(String[] args) {
        Settings settings = new ServerSettings();
        ServerApp server = new ServerApp(settings);

        DragonManager dragonManager = new DragonManager();
        
        


        CommandManager manager = new CommandManager();
        server.initCommands(manager, dragonManager);

        while (true) { //Майнер
            try {
                NetCommand command = server.handleCommand();
                System.out.println(command);
                Answer answer = new Answer(manager.executeCommand(command.command(), command.arg()));
                server.sendObject(answer, server.getLastSender());
                
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            
        }
    }

    








    


}
