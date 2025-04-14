package server.commands;
import server.managers.DragonManager;
import server.managers.ServerCommandManager;


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
    public Object execute(Object arg, String authKey){
        if (!ServerCommandManager.getAuthInstance().keyIsExists(authKey))
            return "Ошибка авторизации";
        dragonManager.clearDragonSet();
        return "Драконы были очищены!";
    }

    @Override
    public String getDescription(){
        return "очистить коллекцию";
    }
    
}
