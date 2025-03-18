package clientCommands;

import java.util.Arrays;
import java.util.Scanner;

import client.ClientApp;
import clientCommands.records.PromptForEnumCommandArgs;
import clientCommands.records.PromptForFloatCommandArgs;
import clientCommands.records.PromptForLongCommandArgs;
import clientCommands.records.PromtForStringCommandArgs;
import utility.ConsoleInputHandler;

public class PromptForEnumCommand<E extends Enum<E>> implements Command {

    private ClientApp clientApp;
    public PromptForEnumCommand(ClientApp clientApp){
        this.clientApp = clientApp;
    }
    @Override
    public boolean isHasArgs(){
        return false;
    }
    @Override
    public String execute(Object args){
        PromptForEnumCommandArgs pArgs = (PromptForEnumCommandArgs) args;
        String prompt = pArgs.prompt();
        E[] enums = (E[]) pArgs.enums();
        boolean allowNull = pArgs.allowNull();
        String joinedEnums = Arrays.toString(enums);
        while (true) {
            System.out.println(String.format(prompt, joinedEnums));
            String in = clientApp.getScanner().nextLine();
            if (in.isEmpty()){
                if (allowNull){
                    return enums[0].name();
                } else {
                    System.out.println("Значение поля не может быть пустым.");
                    continue;
                }
            }
            boolean isInEnums = false;
            for (E enu : enums) {
                if (in.equalsIgnoreCase(enu.name()) || in.equals(Integer.toString(enu.ordinal() + 1))) {
                    return enu.name();
                }
            }
            if (isInEnums == false){
                System.out.println(String.format("Поле должно быть одним из вариантов: (%s)", joinedEnums));
                continue;
            }
            


        }
    }

    @Override
    public String getDescription(){
        return "desc";
    }
    
}
