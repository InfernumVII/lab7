package server.commands;
import managers.DragonManager;


public class ClearCommand implements Command{
    private DragonManager dragonManager;

    public ClearCommand(DragonManager dragonManager) {
        this.dragonManager = dragonManager;
    }

    @Override
    public boolean isHasArgs(){
        return false;
    }

    @Override
    public Object execute(Object arg){
        dragonManager.clearDragonSet();
        return "Драконы были очищены!";
    }

    @Override
    public String getDescription(){
        return "очистить коллекцию";
    }
    
}
