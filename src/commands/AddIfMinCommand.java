package commands;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

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
 * Команда для добавления нового дракона в коллекцию, если его значение меньше, чем у наименьшего элемента коллекции.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class AddIfMinCommand implements CommandInterface {
    private CommandManager commandManager;
    private DragonManager dragonManager;

    /**
     * Конструктор команды AddIfMinCommand.
     *
     * @param commandManager объект {@link CommandManager} для управления командами.
     * @param dragonManager  объект {@link DragonManager} для управления коллекцией драконов.
     */
    public AddIfMinCommand(CommandManager commandManager, DragonManager dragonManager) {
        this.commandManager = commandManager;
        this.dragonManager = dragonManager;
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
     * Выполняет команду добавления нового дракона в коллекцию, если его значение меньше, чем у наименьшего элемента коллекции.
     * Запрашивает у пользователя данные для создания нового объекта {@link Dragon}.
     *
     * @param arg аргумент команды (в данной команде не используется).
     */
    @Override
    public void execute(String arg){
        ConsoleInputHandler.printIfInputIsIn("Добавление нового дракона.");
        ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler(commandManager);
        Dragon dragon = new Dragon.Builder()
                    .withId(dragonManager.getUniqueId())
                    .withName(consoleInputHandler.promtForString("Введите имя дракона:", false))
                    .withCoordinates(new Coordinates(consoleInputHandler.promptForLong("Введите координату x:", false, -420, Long.MAX_VALUE),
                                                        consoleInputHandler.promptForLong("Введите координату y:", false, Long.MIN_VALUE, 699)))
                    .withDate(LocalDate.now())
                    .withAge(consoleInputHandler.promptForLong("Введите возраст дракона:", false, 0, Long.MAX_VALUE))
                    .withColor(consoleInputHandler.promptForEnum("Введите цвет дракона: %s", Color.values(), false))
                    .withType(consoleInputHandler.promptForEnum("Введите тип дракона: %s", DragonType.values(), false))
                    .withCharacter(consoleInputHandler.promptForEnum("Введите характер дракона: %s", DragonCharacter.values(), false))
                    .withHead(new DragonHead(consoleInputHandler.promptForFloat("Введите кол-во глаз у дракона:", true, -Float.MAX_VALUE, Float.MAX_VALUE)))
                    .build();
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
            ConsoleInputHandler.printIfInputIsIn("Новый дракон успешно добавлен.");
        } else {
            ConsoleInputHandler.printIfInputIsIn("Ваш дракон имеет большее значение, чем у минимального элемента коллекции.");
        }
        
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    /**
     * Возвращает строковое представление аргумента команды.
     *
     * @return строковое представление аргумента команды.
     */
    @Override
    public String stringArgument(){
        return "{element}";
    }
    
}
