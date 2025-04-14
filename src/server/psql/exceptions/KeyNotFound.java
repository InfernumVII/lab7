package server.psql.exceptions;

public class KeyNotFound extends Exception {
    public KeyNotFound(){
        super("Key does not exists");
    }
}
