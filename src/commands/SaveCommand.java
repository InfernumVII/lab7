package commands;


import collection.Dragon;
import managers.DragonManager;
import utility.CSV;
import utility.ConsoleInputHandler;
import utility.DragonCSVParser;

/**
 * Команда для сохранения коллекции в файл.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class SaveCommand implements CommandInterface {
    private DragonManager dragonManager;

    /**
     * Конструктор команды SaveCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления коллекцией драконов.
     */
    public SaveCommand(DragonManager dragonManager){
        this.dragonManager = dragonManager;
    }
    
    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code true}, так как команда требует имя файла в качестве аргумента.
     */
    @Override
    public boolean isHasArgs(){
        return true;
    }

    /**
     * Выполняет команду сохранения коллекции в файл.
     *
     * @param arg строка, содержащая имя файла.
     */
    @Override
    public void execute(String arg){
        CSV.writeOneLine(arg, new String[]{"id", "name", "coordinates", "creationDate", "age", "color", "type", "character", "head"});

        for (Dragon dragon : dragonManager.getSortedDragons()) {
            String[] row = DragonCSVParser.parseRowFromDragon(dragon);
            if (row != null) {
                CSV.writeOneLine(arg, row, true);
            }
        }
        ConsoleInputHandler.printIfInputIsIn(String.format("Драконы сохранены в файл: %s", arg));
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "сохранить коллекцию в файл";
    }

    /**
     * Возвращает строковое представление аргумента команды.
     *
     * @return строковое представление аргумента команды.
     */
    @Override
    public String stringArgument(){
        return "file_name";
    }
    
}
