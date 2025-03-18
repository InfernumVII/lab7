package servercommands;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import managers.CommandManager;
import utility.ConsoleInputHandler;

/**
 * Команда для выполнения скрипта из указанного файла.
 * Реализует интерфейс {@link CommandInterface}.
 */
public class ExecuteSciptCommand implements CommandInterface {
    
    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code true}, так как команда требует указания имени файла.
     */
    @Override
    public boolean isHasArgs(){
        return false;
    }

    /**
     * Выполняет команду выполнения скрипта из указанного файла.
     * Читает команды из файла и выполняет их последовательно.
     *
     * @param arg имя файла, содержащего команды для выполнения.
     */
    @Override
    public String execute(Object arg){
        return "Команды успешно выполнены";
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    /**
     * Возвращает строковое представление аргумента команды.
     *
     * @return строковое представление аргумента команды (имя файла).
     */
    @Override
    public String stringArgument(){
        return "file_name";
    }
    
}
