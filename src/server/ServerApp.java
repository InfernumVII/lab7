package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

import commands.AddCommand;
import commands.AddIfMinCommand;
import commands.ClearCommand;
import commands.CountByTypeCommand;
import commands.ExecuteSciptCommand;
import commands.ExitCommand;
import commands.FilterByCharacterCommmand;
import commands.FilterLessThanHeadCommand;
import commands.HelpCommand;
import commands.HistoryCommand;
import commands.InfoCommand;
import commands.RemoveByIdCommand;
import commands.RemoveGreaterCommand;
import commands.SaveCommand;
import commands.ShowCommand;
import commands.UpdateCommand;
import managers.CommandManager;
import managers.DragonManager;
import temp.Command;
import temp.Settings;
import temp.UdpNetwork;
import utility.CSV;

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
        UdpNetwork server = new ServerApp(settings);

        DragonManager dragonManager = new DragonManager();
        /* 
        String fileName = System.getenv("DRAGON_FILE");
        if (fileName == null || fileName.isEmpty()) {
            System.err.println("Ошибка: переменная окружения DRAGON_FILE не задана.");
        } else {
            List<String[]> fileData = CSV.read(fileName);
            if (fileData != null){
                dragonManager.collectParsedDragons(fileData);
                System.out.println("Коллекция успешно загружена из файла: " + fileName);
            } 
        }*/


        CommandManager manager = new CommandManager();
        ServerApp.initCommands(manager, dragonManager);

        


        while (true) { //Майнер
            try {
                Command command = server.handleCommand();
                System.out.println(command);
                manager.executeCommand(command.command(), command.argument());

                
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            
        }
    }

    

    



    private static void initCommands(CommandManager manager, DragonManager dragonManager){
        manager.registerCommand("help", new HelpCommand(manager));
        manager.registerCommand("info", new InfoCommand(dragonManager));
        manager.registerCommand("show", new ShowCommand(dragonManager));
        //manager.registerCommand("add", new AddCommand(manager, dragonManager));
        //manager.registerCommand("update", new UpdateCommand(dragonManager, manager));
        manager.registerCommand("remove_by_id", new RemoveByIdCommand(dragonManager));
        manager.registerCommand("clear", new ClearCommand(dragonManager));
        //manager.registerCommand("save", new SaveCommand(dragonManager));
        //manager.registerCommand("execute_script", new ExecuteSciptCommand(manager));
        //manager.registerCommand("exit", new ExitCommand());
        //manager.registerCommand("add_if_min", new AddIfMinCommand(manager, dragonManager));
        //manager.registerCommand("remove_greater", new RemoveGreaterCommand(dragonManager, manager));
        manager.registerCommand("history", new HistoryCommand(manager));
        //manager.registerCommand("count_by_type", new CountByTypeCommand(dragonManager));
        //manager.registerCommand("filter_by_character", new FilterByCharacterCommmand(dragonManager));
        //manager.registerCommand("filter_less_than_head", new FilterLessThanHeadCommand(dragonManager));
    }







    


}
