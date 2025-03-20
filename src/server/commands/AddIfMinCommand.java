package server.commands;
import collection.Dragon.Builder;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringJoiner;

import client.commands.utility.ConsoleInputHandler;
import collection.Color;
import collection.Coordinates;
import collection.Dragon;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;
import managers.CommandManager;
import managers.DragonManager;


/**
 * Команда для добавления нового дракона в коллекцию.
 * Реализует интерфейс {@link Command}.
 */
public class AddIfMinCommand implements Command {
    private DragonManager dragonManager;

    public AddIfMinCommand(DragonManager dragonManager) {
        this.dragonManager = dragonManager;
    }

    @Override
    public boolean isHasArgs(){
        return true;
    }

    @Override
    public Object execute(Object arg){
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("Добавление нового дракона.");

        Builder dragonBuilder = (Builder) arg;
        Dragon dragon = dragonBuilder
                    .withId(dragonManager.getUniqueId())
                    .withDate(LocalDate.now())
                    .build();
            
        if (dragonManager.getDragonSet().isEmpty()){
            dragonManager.addDragon(dragon);
            stringJoiner.add("Новый дракон успешно добавлен.");
        } else {
            Dragon minDragon = dragonManager.getMinDragonByXAndY();
            if (dragon.getCoordinates().getX() + dragon.getCoordinates().getY() < minDragon.getCoordinates().getX() + minDragon.getCoordinates().getY()){
                dragonManager.addDragon(dragon);
                stringJoiner.add("Новый дракон успешно добавлен.");
            } else {
                stringJoiner.add("Ваш дракон имеет большее значение, чем у минимального элемента коллекции.");
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
        return "добавить новый элемент в коллекцию";
    }
    
}