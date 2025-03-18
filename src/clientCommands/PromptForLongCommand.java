package clientCommands;

import java.util.Scanner;

import client.ClientApp;
import clientCommands.records.PromptForLongCommandArgs;
import clientCommands.records.PromtForStringCommandArgs;
import utility.ConsoleInputHandler;

public class PromptForLongCommand implements Command {

    private ClientApp clientApp;
    public PromptForLongCommand(ClientApp clientApp){
        this.clientApp = clientApp;
    }
    @Override
    public boolean isHasArgs(){
        return false;
    }
    @Override
    public String execute(Object args){
        while (true) {
            PromptForLongCommandArgs pArgs = (PromptForLongCommandArgs) args;
            String prompt = pArgs.prompt();
            boolean allowNull = pArgs.allowNull();
            long min = pArgs.min();
            long max = pArgs.max();
            System.out.println(String.format(prompt, min, max));
            String inString = clientApp.getScanner().nextLine();
            if (inString.isEmpty()) {
                if (allowNull) {
                    return "0"; 
                } else {
                    System.out.println("Значение поля не может быть пустым.");
                    continue;
                }
            }
            try {
                long in = Long.parseLong(inString);

                if (in <= min || in > max) {
                    System.out.printf("Число должно быть между %s и %s.\n", min, max);
                } else {
                    return inString; 
                }
            } catch (NumberFormatException e) {
                System.out.println("Поле должно быть числом.");
            }
        }
    }

    @Override
    public String getDescription(){
        return "desc";
    }
    
}
