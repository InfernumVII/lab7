package server.commands;

import collection.User;

public interface Command {
    Object execute(Object arg, User authUser); 
    String getDescription();
    boolean isHasArgs();
    default String stringArgument(){
        return "";
    }
}