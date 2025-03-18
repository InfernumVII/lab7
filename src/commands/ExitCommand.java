package commands;

/**
 * Команда для очистки коллекции драконов.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class ExitCommand implements CommandInterface {

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
     * Выполняет команду очистки коллекции драконов.
     *
     * @param arg аргумент команды (в данной команде не используется).
     */
    @Override
    public void execute(String arg){
        System.exit(0);
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "завершить программу (без сохранения в файл)";
    }
    
}
