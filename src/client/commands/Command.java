package client.commands;

public interface Command {
    Object execute(String arg);
    boolean isHasArgs();
}