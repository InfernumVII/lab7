package server.commands;
import collection.Dragon.Builder;
import server.managers.CommandManager;
import server.managers.DragonManager;

import java.time.LocalDate;
import java.util.StringJoiner;

import client.commands.utility.ConsoleInputHandler;
import collection.Color;
import collection.Coordinates;
import collection.Dragon;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;


/**
 * Команда для добавления нового дракона в коллекцию.
 * Реализует интерфейс {@link Command}.
 */
public class AddCommand implements Command {
    private DragonManager dragonManager;


    public AddCommand(DragonManager dragonManager) {
        this.dragonManager = dragonManager;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code false}, так как команда не принимает аргументов.
     */
    @Override
    public boolean isHasArgs(){
        return true;
    }


    /**
     * Выполняет команду добавления нового дракона в коллекцию.
     * Запрашивает у пользователя данные для создания нового объекта {@link Dragon}.
     *
     * @param arg аргумент команды (в данной команде не используется).
     */
    @Override
    public Object execute(Object arg){
        StringJoiner stringJoiner = new StringJoiner("\n");
        Builder dragonBuilder = (Builder) arg;
        stringJoiner.add("Добавление нового дракона.");
        Dragon dragon = dragonBuilder
                    .withId(dragonManager.getUniqueId())
                    .withDate(LocalDate.now())
                    .build();

        dragonManager.addDragon(dragon);
        stringJoiner.add("Новый дракон успешно добавлен.");
        return stringJoiner.toString();
        
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "добавить новый элемент в коллекцию";
    }
    
}