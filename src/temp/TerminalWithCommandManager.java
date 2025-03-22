package temp;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

import managers.CommandManager;
import server.managers.exceptions.ParseCommandException;

public class TerminalWithCommandManager<T extends CommandManager<?>> {
    private Scanner scanner;
    protected T cManager;
    protected boolean outputState = true;
    private final PrintStream defaultPStream = System.out;
    private final InputStream dInputStream = System.in;
    

    public TerminalWithCommandManager(T cManager){
        scanner = new Scanner(dInputStream);
        this.cManager = cManager;
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

    public void printIfOutputStateTrue(Object in){
        if (outputState == true) { System.out.print(in.toString()); }
    }

    public void start(boolean condition, Consumer<String> afterFunction){
        while (condition) {
            printIfOutputStateTrue("> ");
            String in = scanner.nextLine().trim();
            afterFunction.accept(in);
        }
    }

    public void start(boolean condition){
        start(condition, in -> {
            try {
                cManager.executeCommandFromRawInput(in);
            } catch (ParseCommandException e) {
                printIfOutputStateTrue(e.getMessage());
            }
        });
    }



}
