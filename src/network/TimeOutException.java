package network;

public class TimeOutException extends Exception {
    public TimeOutException(){
        super("Сервер недоступен или не отвечает");
    }
}
