package clientCommands;

import java.util.Scanner;

import client.ClientApp;
import clientCommands.records.PromtForStringCommandArgs;
import utility.ConsoleInputHandler;

public class PromtForStringCommand implements Command {

    private ClientApp clientApp;
    public PromtForStringCommand(ClientApp clientApp){
        this.clientApp = clientApp;
    }
    @Override
    public boolean isHasArgs(){
        return false;
    }
    @Override
    public String execute(Object args){
        while (true){
            PromtForStringCommandArgs pArgs = (PromtForStringCommandArgs) args;
            System.out.println(pArgs.prompt());
            boolean allowNull = pArgs.allowNull();
            String in = clientApp.getScanner().nextLine();
            if (!allowNull && in.isEmpty()) {
                System.out.println("Значение поля не может быть пустым.");
                continue;
            }
            return in;
        }
    }

    @Override
    public String getDescription(){
        return "desc";
    }
    
}
