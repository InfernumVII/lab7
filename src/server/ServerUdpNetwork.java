package server;

import java.io.IOException;

import network.Settings;
import network.UdpNetwork;
import network.exceptions.TimeOutException;
import network.models.Answer;
import network.models.NetCommand;
import server.managers.DragonManager;
import server.managers.ServerCommandManager;

public class ServerUdpNetwork extends UdpNetwork {
    private DragonManager dManager;
    private ServerCommandManager serverCommandManager;
    
    public ServerUdpNetwork(Settings settings) throws IOException{
        inetSocketAddress = getSocketAddress(settings);
        datagramChannel = createDatagramChannel(inetSocketAddress);
    }

    public void initCommandManager(){
        dManager = new DragonManager();
        serverCommandManager = new ServerCommandManager();
        serverCommandManager.initDefaultCommands(dManager);
    }

    public void start(boolean condition) throws ClassNotFoundException, IOException, TimeOutException{
        System.out.println("Сервер запущен");
        while (condition) {
            NetCommand command = handleCommand();
            System.out.println(command);
            Answer answer = new Answer(serverCommandManager.executeCommand(command.command(), command.arg()));
            sendObject(answer, getLastSender());
        }
        
    }
}
