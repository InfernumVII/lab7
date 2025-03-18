package commands;
import java.util.Deque;

import managers.CommandManager;
import utility.ConsoleInputHandler;

/**
 * Команда для вывода последних 5 выполненных команд (без аргументов).
 * Реализует интерфейс {@link CommandInterface}.
 */
public class HistoryCommand implements CommandInterface {
    private CommandManager commandManager;

    /**
     * Конструктор команды HistoryCommand.
     *
     * @param commandManager объект {@link CommandManager} для управления командами.
     */
    public HistoryCommand(CommandManager commandManager) {
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
     * Выполняет команду вывода последних 5 выполненных команд.
     *
     * @param arg аргумент команды (в данной команде не используется).
     */
    @Override
    public void execute(String arg){
        Deque<String> history = commandManager.getHistory();
        for (String string : history) {
            ConsoleInputHandler.printIfInputIsIn(string);
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "вывести последние 5 команд (без их аргументов)";
    }
}
