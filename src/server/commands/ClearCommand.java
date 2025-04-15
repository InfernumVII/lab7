package server.commands;
import server.managers.DragonManager;
import server.managers.ServerCommandManager;
import server.psql.auth.User;
import server.psql.exceptions.UserNotFound;


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
        try {
            User user = ServerCommandManager.getAuthInstance().getUserByAuthKey(authKey);
            if (!dragonManager.preClearDragonSet(authKey))
                return "Нет драконов для очистки";
            dragonManager.clearDragonSet(user.getId());
            return "Драконы были очищены!";
        } catch (UserNotFound e) {
            return "Ошибка авторизации";
        }
    }

    @Override
    public String getDescription(){
        return "очистить коллекцию";
    }
    
}
