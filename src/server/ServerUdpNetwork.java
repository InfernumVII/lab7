package server;

import java.io.IOException;

import network.Settings;
import network.UdpNetwork;
import network.exceptions.TimeOutException;
import network.models.Answer;
import network.models.NetCommand;
import server.managers.DragonManager;
import server.managers.ServerCommandManager;
import server.managers.exceptions.DragonFileExistException;
import server.managers.exceptions.DragonFindException;

public class ServerUdpNetwork extends UdpNetwork {
    private DragonManager dManager;
    private ServerCommandManager serverCommandManager;
    
    public ServerUdpNetwork(Settings settings) throws IOException{
        inetSocketAddress = getSocketAddress(settings);
        datagramChannel = createDatagramChannel(inetSocketAddress);

        initCommandManager();
        dManager.addDragonsFromDragonFileEnv();
    }

    public void initCommandManager() throws DragonFileExistException{
        dManager = new DragonManager();
        serverCommandManager = new ServerCommandManager();
        serverCommandManager.initDefaultCommands(dManager);
    }

    public DragonManager getDragonManager(){
        return dManager;
    }

    public void start(boolean condition) throws ClassNotFoundException, IOException {
        System.out.println("Сервер запущен");
        while (condition) {
            NetCommand command = handleCommand();
            //System.out.println(command);
            Answer answer = new Answer(serverCommandManager.executeCommand(command.command(), command.arg()));
            sendObject(answer, getLastSender());
        }
        
    }
}
