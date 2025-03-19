package server.commands;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import client.commands.utility.ConsoleInputHandler;
import managers.CommandManager;
/**
 * Команда для вывода справки по доступным командам.
 * Реализует интерфейс {@link Command}.
 */
public class HelpCommand implements Command {
    private CommandManager commandManager;

    /**
     * Конструктор команды HelpCommand.
     *
     * @param commandManager объект {@link CommandManager} для управления командами.
     */
    public HelpCommand(CommandManager commandManager){
        this.commandManager = commandManager;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code false}, так как команда не принимает аргументов.
     */
    @Override
    public boolean isHasArgs(){
        return false;
    }

    /**
     * Выполняет команду вывода справки по доступным командам.
     *
     * @param arg аргумент команды (в данной команде не используется).
     */
    @Override
    public String execute(Object arg){
        StringJoiner stringJoiner = new StringJoiner("\n");
        Map<String, Command> commands = commandManager.getCommands();
        stringJoiner.add("Справка по командам: ");
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            
            String commandName = entry.getKey();
            Command command = entry.getValue();
            if (command.isHasArgs()){
                stringJoiner.add(String.format("%s %s - %s", commandName, command.stringArgument(), command.getDescription()));
            } else {
                stringJoiner.add(String.format("%s - %s", commandName, command.getDescription()));
            }
        }
        return stringJoiner.toString();
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "вывести справку по доступным командам";
    }
    
}
