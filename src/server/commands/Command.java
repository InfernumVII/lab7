package server.commands;

public interface Command {
    Object execute(Object arg); //TODO return OBJECT
    String getDescription();
    boolean isHasArgs();
    default String stringArgument(){
        return "";
    }
}