package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

import managers.CommandManager;
import managers.DragonManager;
import managers.utility.CSV;
import network.Settings;
import network.UdpNetwork;
import network.models.Answer;
import network.models.Command;
import server.commands.*;
//TODO: принцип единой ответственности
public class ServerApp extends UdpNetwork {

    public ServerApp(Settings settings){
        try {
            inetSocketAddress = getSocketAddress(settings);
            datagramChannel = createDatagramChannel(inetSocketAddress);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
    
    
    public static void main(String[] args) {
        Settings settings = new ServerSettings();
        ServerApp server = new ServerApp(settings);

        DragonManager dragonManager = new DragonManager();
        
        String fileName = System.getenv("DRAGON_FILE");
        if (fileName == null || fileName.isEmpty()) {
            System.out.println("Ошибка: переменная окружения DRAGON_FILE не задана.");
        } else {
            List<String[]> fileData = CSV.read(fileName);
            if (fileData != null){
                dragonManager.collectParsedDragons(fileData);
                System.out.println("Коллекция успешно загружена из файла: " + fileName);
            } 
        }


        CommandManager manager = new CommandManager();
        server.initCommands(manager, dragonManager);

        while (true) { //Майнер
            try {
                Command command = server.handleCommand();
                System.out.println(command);
                Answer answer = new Answer(manager.executeCommand(command.command(), command.arg()));
                server.sendObject(answer, server.getLastSender());
                
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            
        }
    }

    

    private void initCommands(CommandManager manager, DragonManager dragonManager){ //TODO унести в command manager
        manager.registerCommand("help", new HelpCommand(manager));
        manager.registerCommand("info", new InfoCommand(dragonManager));
        manager.registerCommand("show", new ShowCommand(dragonManager));
        manager.registerCommand("add", new AddCommand(dragonManager));
        manager.registerCommand("update", new UpdateCommand(dragonManager));
        manager.registerCommand("remove_by_id", new RemoveByIdCommand(dragonManager));
        manager.registerCommand("clear", new ClearCommand(dragonManager));
        manager.registerCommand("execute_script", new ExecuteSciptCommand());
        manager.registerCommand("add_if_min", new AddIfMinCommand(dragonManager));
        manager.registerCommand("remove_greater", new RemoveGreaterCommand(dragonManager));
        manager.registerCommand("history", new HistoryCommand(manager));
        manager.registerCommand("count_by_type", new CountByTypeCommand(dragonManager));
        manager.registerCommand("filter_by_character", new FilterByCharacterCommmand(dragonManager));
        manager.registerCommand("filter_less_than_head", new FilterLessThanHeadCommand(dragonManager));
    }







    


}
