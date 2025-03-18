package servercommands;
import java.util.Deque;
import java.util.StringJoiner;

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
    public String execute(Object arg){
        StringJoiner stringJoiner = new StringJoiner("\n");
        Deque<String> history = commandManager.getHistory();
        for (String string : history) {
            stringJoiner.add(string);
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
        return "вывести последние 5 команд (без их аргументов)";
    }
}
