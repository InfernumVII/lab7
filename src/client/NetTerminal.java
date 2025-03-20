package client;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

import client.managers.ClientCommandManager;
import managers.exceptions.ParseCommandException;
import network.exceptions.TimeOutException;
import network.models.Answer;
import network.models.NetCommand;

public class NetTerminal {
    private Scanner scanner;
    private ClientCommandManager cManager;
    private boolean outputState = true;
    private ClientApp clientApp;

    public NetTerminal(ClientApp clientApp){
        scanner = new Scanner(System.in);
        cManager = new ClientCommandManager();
        cManager.initDefaultCommands(this);

        this.clientApp = clientApp;
    }

    public void setScanner(Scanner s){
        this.scanner = s;
    }

    public Scanner getScanner(){
        return scanner;
    }

    public void swapOutput(){
        outputState = !outputState;
    }
    
    public boolean getOutputState(){
        return outputState;
    }

    public void start(boolean condition){
        while (condition) {
            if (outputState == true) { System.out.print("> "); }
            String in;
            try {
                in = scanner.nextLine().trim();
                handleIter(in, outputState);
            } catch (Exception e) {
                break;
            }
        }
    }

    public void handleIter(String in, boolean showPrints) throws ParseCommandException, ClassNotFoundException, IOException, TimeOutException{
        NetCommand netCommand = cManager.getCommandFromRawInput(in);
        Answer answer = clientApp.sendAndGetAnswer(netCommand);
        if (showPrints == true) { smartPrint(answer.answer()); }
    }

    public void smartPrint(Object in){
        if (in instanceof Collection){
            ((Collection) in).forEach(System.out::println);
        } else {
            System.out.println(in);
        }
    }


}
