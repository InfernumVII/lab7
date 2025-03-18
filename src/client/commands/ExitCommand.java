package client.commands;


public class ExitCommand implements Command {


    @Override
    public boolean isHasArgs(){
        return false;
    }

    @Override
    public Object execute(String arg){
        System.exit(0);
        return null;
    }
}
