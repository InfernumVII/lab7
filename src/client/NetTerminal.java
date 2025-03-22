package client;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

import client.managers.ClientCommandManager;
import network.exceptions.TimeOutException;
import network.models.Answer;
import network.models.NetCommand;
import server.managers.exceptions.ParseCommandException;


//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY
//LEGACY

public class NetTerminal {
    private Scanner scanner;
    private ClientCommandManager cManager;
    private boolean outputState = true;
    private ClientUdpNetwork clientApp;

    public NetTerminal(ClientUdpNetwork clientApp){
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

    public void start(boolean condition) throws ClassNotFoundException, ParseCommandException, IOException, TimeOutException{
        while (condition) {
            if (outputState == true) { System.out.print("> "); }
            String in = scanner.nextLine().trim();
            handleIter(in, outputState);
        }
    }

    public void handleIter(String in, boolean showPrints) throws ParseCommandException, ClassNotFoundException, IOException, TimeOutException{
        NetCommand netCommand = cManager.getCommandFromRawInput(in);
        Answer answer = clientApp.sendAndGetAnswer(netCommand);
        if (showPrints == true) { smartPrint(answer.answer()); }
    }

    public void smartPrint(Object in){
        if (in instanceof Collection){
            ((Collection) in).stream().forEachOrdered(System.out::println);
        } else {
            System.out.println(in);
        }
    }


}
