package client;


import java.io.IOException;
import java.sql.Time;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import client.commands.AddCommand;
import client.commands.AddIfMinCommand;
import client.commands.ExecuteScriptCommand;
import client.commands.ExitCommand;
import client.commands.RemoveGreaterCommand;
import client.commands.UpdateCommand;
import client.managers.ClientCommandManager;
import managers.exceptions.ParseCommandException;
import network.Settings;
import network.UdpNetwork;
import network.exceptions.TimeOutException;
import network.models.Answer;
import network.models.NetCommand;

public class ClientApp extends UdpNetwork {

    public ClientApp(Settings settings) throws IOException{
        inetSocketAddress = getSocketAddress(settings);
        datagramChannel = createDatagramChannel();
    }

    public Answer sendAndGetAnswer(NetCommand command) throws IOException, ClassNotFoundException, TimeOutException {
        sendObject(command);
        return handleAnswer(1000);
    }

    
}





    


