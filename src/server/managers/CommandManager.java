package server.managers;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import server.commands.*;


/**
 * Класс для управления командами и их выполнением.
 * Реализует хранение, регистрацию, выполнение команд, а также отслеживание истории выполненных команд.
 */
public class CommandManager {
    private DragonManager dragonManager;

    public CommandManager(DragonManager dragonManager){
        this.dragonManager = dragonManager;
    }

    private final static int HISTORY_SIZE = 5;
    
    public static int getHistorySize() {
        return HISTORY_SIZE;
    }
    private Map<String, Command> commands = new HashMap<>();

    private Deque<String> history = new ArrayDeque<String>(HISTORY_SIZE);

    public void registerCommand(String name, Command command){
        commands.put(name, command);
    }

    public void initDefaultCommands(){
        registerCommand("help", new HelpCommand(this));
        registerCommand("info", new InfoCommand(dragonManager));
        registerCommand("show", new ShowCommand(dragonManager));
        registerCommand("add", new AddCommand(dragonManager));
        registerCommand("update", new UpdateCommand(dragonManager));
        registerCommand("remove_by_id", new RemoveByIdCommand(dragonManager));
        registerCommand("clear", new ClearCommand(dragonManager));
        registerCommand("execute_script", new ExecuteScriptCommand());
        registerCommand("add_if_min", new AddIfMinCommand(dragonManager));
        registerCommand("remove_greater", new RemoveGreaterCommand(dragonManager));
        registerCommand("history", new HistoryCommand(this));
        registerCommand("count_by_type", new CountByTypeCommand(dragonManager));
        registerCommand("filter_by_character", new FilterByCharacterCommand(dragonManager));
        registerCommand("filter_less_than_head", new FilterLessThanHeadCommand(dragonManager));
    }

    public Object executeCommand(String name, Object arg){
        Object answer;
        Command command = commands.get(name);
        if (command != null) {
            if (command.isHasArgs() == true && arg == null){
                return "У команды должны быть аргументы.";
            }
            else if (command.isHasArgs() == false && arg != null){
                return "У команды не может быть аргументов.";
            } else {
                answer = command.execute(arg);
                storeCommand(name);
            }
            
        } else {
            return "Неизвестная команда: " + name;
        }
        return answer;
    }

    public Deque<String> getHistory(){
        return history;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }

    public void setHistory(Deque<String> history) {
        this.history = history;
    }

    private void storeCommand(String command){
        if (history.size() >= HISTORY_SIZE) {
            history.removeFirst(); 
        }
        history.addLast(command);
    }
}
