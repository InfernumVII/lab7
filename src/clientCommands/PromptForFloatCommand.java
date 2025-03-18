package clientCommands;

import java.util.Scanner;

import client.ClientApp;
import clientCommands.records.PromptForFloatCommandArgs;
import clientCommands.records.PromptForLongCommandArgs;
import clientCommands.records.PromtForStringCommandArgs;
import utility.ConsoleInputHandler;

public class PromptForFloatCommand implements Command {

    private ClientApp clientApp;
    public PromptForFloatCommand(ClientApp clientApp){
        this.clientApp = clientApp;
    }
    @Override
    public boolean isHasArgs(){
        return false;
    }
    @Override
    public String execute(Object args){
        while (true) {
            PromptForFloatCommandArgs pArgs = (PromptForFloatCommandArgs) args;
            String prompt = pArgs.prompt();
            boolean allowNull = pArgs.allowNull();
            Float min = pArgs.min();
            Float max = pArgs.max();
            System.out.println(prompt);
            String inString = clientApp.getScanner().nextLine();
            if (inString.isEmpty()) {
                if (allowNull) {
                    return "0f"; 
                } else {
                    System.out.println("Значение поля не может быть пустым.");
                    continue;
                }
            }
            try {
                Float in = Float.parseFloat(inString);

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
