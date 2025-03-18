package commands;
import java.util.Map;

import managers.CommandManager;
import utility.ConsoleInputHandler;
/**
 * Команда для вывода справки по доступным командам.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class HelpCommand implements CommandInterface {
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
    public void execute(String arg){
        Map<String, CommandInterface> commands = commandManager.getCommands();
        ConsoleInputHandler.printIfInputIsIn("Справка по командам: ");
        for (Map.Entry<String, CommandInterface> entry : commands.entrySet()) {
            
            String commandName = entry.getKey();
            CommandInterface command = entry.getValue();
            if (command.isHasArgs()){
                ConsoleInputHandler.printIfInputIsIn(String.format("%s %s - %s", commandName, command.stringArgument(), command.getDescription()));
            } else {
                ConsoleInputHandler.printIfInputIsIn(String.format("%s - %s", commandName, command.getDescription()));
            }
        }
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
