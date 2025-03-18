package commands;
import collection.Dragon;
import collection.DragonCharacter;
import managers.DragonManager;
import utility.ArgHandler;
import utility.ConsoleInputHandler;

/**
 * Команда для вывода элементов коллекции, значение поля character которых равно заданному.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class FilterByCharacterCommmand implements CommandInterface {
    
    private DragonManager dragonManager;
    /**
     * Конструктор команды FilterByCharacterCommmand.
     *
     * @param dragonManager объект {@link DragonManager} для управления коллекцией драконов.
     */
    public FilterByCharacterCommmand(DragonManager dragonManager){
        this.dragonManager = dragonManager;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code true}, так как команда требует аргумента (характер дракона).
     */
    @Override
    public boolean isHasArgs(){
        return true;
    }

    /**
     * Выполняет команду вывода элементов коллекции, значение поля character которых равно заданному.
     *
     * @param arg аргумент команды (характер дракона).
     */
    @Override
    public void execute(String arg){
        if (ArgHandler.checkArgForEnumString(arg, DragonCharacter.values())){
            ConsoleInputHandler.printIfInputIsIn("Драконы с таким же характером: ");
            DragonCharacter dragonCharacter = DragonCharacter.valueOf(arg);
            for (Dragon dragon : dragonManager.getSortedDragons()) {
                if (dragon.getCharacter() == dragonCharacter){
                    ConsoleInputHandler.printIfInputIsIn(dragon);
                }
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
        return "вывести элементы, значение поля character которых равно заданному";
    }

    /**
     * Возвращает строковое представление аргумента команды.
     *
     * @return строковое представление аргумента команды (характер дракона).
     */
    @Override
    public String stringArgument(){
        return "character";
    }

    
}
