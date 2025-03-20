package server.commands;
import java.util.Deque;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import client.commands.utility.ConsoleInputHandler;
import server.managers.CommandManager;

/**
 * Команда для вывода последних 5 выполненных команд (без аргументов).
 * Реализует интерфейс {@link Command}.
 */
public class HistoryCommand implements Command {
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
    public Object execute(Object arg){
        StringJoiner stringJoiner = new StringJoiner("\n");
        Deque<String> history = commandManager.getHistory();
        history.stream()
            .forEachOrdered(string -> stringJoiner.add(string));
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
