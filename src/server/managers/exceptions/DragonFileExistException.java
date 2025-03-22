package server.managers.exceptions;

public class DragonFileExistException extends RuntimeException {
    public DragonFileExistException(){
        super("Ошибка: переменная окружения DRAGON_FILE не задана.");
    }
}
