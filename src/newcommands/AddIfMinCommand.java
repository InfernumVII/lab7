package newcommands;
import collection.Dragon.Builder;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringJoiner;

import collection.Color;
import collection.Coordinates;
import collection.Dragon;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;
import managers.CommandManager;
import managers.DragonManager;
import utility.ConsoleInputHandler;


/**
 * Команда для добавления нового дракона в коллекцию.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class AddIfMinCommand implements CommandInterface {
    private DragonManager dragonManager;


    public AddIfMinCommand(DragonManager dragonManager) {
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
    public String execute(Object arg){
        
        StringJoiner stringJoiner = new StringJoiner("\n");
        Builder dragonBuilder = (Builder) arg;
        stringJoiner.add("Добавление нового дракона.");
        //ConsoleInputHandler.printIfInputIsIn("Добавление нового дракона.");
        Dragon dragon = dragonBuilder
                    .withId(dragonManager.getUniqueId())
                    .withDate(LocalDate.now())
                    .build();
        if (dragonManager.getDragonSet().isEmpty()){
            dragonManager.addDragon(dragon);
            stringJoiner.add("Новый дракон успешно добавлен.");
        } else {
            Dragon minDragon = Collections.min(dragonManager.getDragonSet(), new Comparator<Dragon>() {
                @Override
                public int compare(Dragon d1, Dragon d2) {
                    int xCompare = Long.compare(d1.getCoordinates().getX(), d2.getCoordinates().getX());
                    if (xCompare != 0) {
                        return xCompare; 
                    }
                    return Long.compare(d1.getCoordinates().getY(), d2.getCoordinates().getY());
                }
            });
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