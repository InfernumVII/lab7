package server.commands;

public interface Command {
    Object execute(Object arg); 
    String getDescription();
    boolean isHasArgs();
    default String stringArgument(){
        return "";
    }
}