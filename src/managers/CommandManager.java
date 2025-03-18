package managers;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import newcommands.CommandInterface;


/**
 * Класс для управления командами и их выполнением.
 * Реализует хранение, регистрацию, выполнение команд, а также отслеживание истории выполненных команд.
 */
public class CommandManager {
    private final static int HISTORY_SIZE = 5;
    /**
     * Разбивает введённую команду на имя команды и её аргумент.
     *
     * @param command строка, содержащая команду и её аргумент.
     * @return массив строк, где первый элемент — имя команды, а второй — аргумент (может быть null).
     */
    public static String[] parseCommand(String command){
        String[] input = command.split(" ");
        String commandName = input[0];
        String commandArg = null;
        if (input.length > 2){
            System.out.println("У команды не может быть больше чем 1 аргумент.");
            return null;
        }
        if (input.length == 2){
            commandArg = input[1];
        } 
        return new String[]{commandName, commandArg};
    }
    /**
     * Возвращает максимальный размер истории команд.
     *
     * @return максимальный размер истории команд.
     */
    public static int getHistorySize() {
        return HISTORY_SIZE;
    }
    private Map<String, CommandInterface> commands = new HashMap<>();
    private Scanner scanner;


    private Deque<String> history = new ArrayDeque<String>(HISTORY_SIZE);
    private boolean inputIsIn = true;

    /**
     * Конструктор по умолчанию для класса CommandManager.
     * Инициализирует объект Scanner для чтения ввода из стандартного потока ввода (System.in).
     */
    public CommandManager (){
        scanner = new Scanner(System.in);
    }

    /**
     * Регистрирует команду в менеджере.
     *
     * @param name имя команды.
     * @param command объект команды, реализующий интерфейс {@link CommandInterface}.
     */
    public void registerCommand(String name, CommandInterface command){
        commands.put(name, command);
    }

    /**
     * Выполняет команду, если она зарегистрирована в менеджере.
     *
     * @param name имя команды.
     * @param arg аргумент команды (может быть null).
     */
    public String executeCommand(String name, String arg){
        String answer;
        CommandInterface command = commands.get(name);
        if (command != null) {
            if (command.isHasArgs() == true && arg == null){
                return "У команды должны быть аргументы.";
            }
            else if (command.isHasArgs() == false && arg != null){
                return "У команды не может быть аргументов.";
            } else {
                answer = command.execute(arg);
                storeCommand(name);
            }
            
        } else {
            return "Неизвестная команда: " + name;
        }
        return answer;
    }

    /**
     * Возвращает историю выполненных команд.
     *
     * @return история выполненных команд.
     */
    public Deque<String> getHistory(){
        return history;
    }

    /**
     * Возвращает карту зарегистрированных команд.
     *
     * @return карта зарегистрированных команд.
     */
    public Map<String, CommandInterface> getCommands() {
        return commands;
    }

    /**
     * Устанавливает карту зарегистрированных команд.
     *
     * @param commands карта зарегистрированных команд.
     */
    public void setCommands(Map<String, CommandInterface> commands) {
        this.commands = commands;
    }

    /**
     * Устанавливает историю выполненных команд.
     *
     * @param history история выполненных команд.
     */
    public void setHistory(Deque<String> history) {
        this.history = history;
    }

    /**
     * Возвращает объект Scanner, используемый для ввода данных.
     *
     * @return объект Scanner
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * Устанавливает объект Scanner, который будет использоваться для ввода данных.
     *
     * @param scanner объект Scanner
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Устанавливает флаг, указывающий, был ли введен ввод.
     *
     * @param inputIsIn true, если ввод был выполнен, иначе false
     */
    public void setInputIsIn(boolean inputIsIn) {
        this.inputIsIn = inputIsIn;
    }

    /**
     * Возвращает значение флага, указывающего, был ли введен ввод.
     *
     * @return true, если ввод был выполнен, иначе false
     */
    public boolean isInputIsIn() {
        return inputIsIn;
    }

    /**
     * Добавляет выполненную команду в историю.
     * Если история превышает размер HISTORY_SIZE, удаляет самую старую команду.
     *
     * @param command имя выполненной команды.
     */
    private void storeCommand(String command){
        if (history.size() >= HISTORY_SIZE) {
            history.removeFirst(); 
        }
        history.addLast(command);
    }
}
