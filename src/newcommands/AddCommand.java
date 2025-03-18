package newcommands;
import java.time.LocalDate;

import clientCommands.PromptForLongCommand;
import clientCommands.records.PromptForLongCommandArgs;
import clientCommands.records.PromtForStringCommandArgs;
import collection.Color;
import collection.Coordinates;
import collection.Dragon;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;
import managers.CommandManager;
import managers.DragonManager;
import server.ServerApp;
import temp.Answer;
import temp.ClientCommand;
import temp.UdpNetwork;
import utility.ConsoleInputHandler;


/**
 * Команда для добавления нового дракона в коллекцию.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class AddCommand implements CommandInterface {
    private DragonManager dragonManager;
    private CommandManager commandManager;
    private ServerApp server;

    /**
     * Конструктор команды AddCommand.
     *
     * @param commandManager объект {@link CommandManager} для управления командами.
     * @param dragonManager  объект {@link DragonManager} для управления коллекцией драконов.
     */
    public AddCommand(CommandManager commandManager, DragonManager dragonManager, ServerApp server) {
        this.commandManager = commandManager;
        this.dragonManager = dragonManager;
        this.server = server;
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
     * Выполняет команду добавления нового дракона в коллекцию.
     * Запрашивает у пользователя данные для создания нового объекта {@link Dragon}.
     *
     * @param arg аргумент команды (в данной команде не используется).
     */
    @Override
    public String execute(String arg){
        ConsoleInputHandler.printIfInputIsIn("Добавление нового дракона.");
        Dragon dragon = new Dragon.Builder()
                    .withId(dragonManager.getUniqueId())
                    .withName(server.sendRequestToClientWithAnswer(new Answer(null, new ClientCommand("promtForString", new PromtForStringCommandArgs("Введите имя дракона:", false)))))
                    .withCoordinates(new Coordinates(Long.parseLong(server.sendRequestToClientWithAnswer(new Answer(null, new ClientCommand("promptForLong", new PromptForLongCommandArgs("Введите координату x:", false, -420, Long.MAX_VALUE))))),
                    Long.parseLong(server.sendRequestToClientWithAnswer(new Answer(null, new ClientCommand("promptForLong", new PromptForLongCommandArgs("Введите координату y:", false, Long.MIN_VALUE, 699)))))))
                    .build();

        dragonManager.addDragon(dragon);
        ConsoleInputHandler.printIfInputIsIn("Новый дракон успешно добавлен.");
        return null;
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