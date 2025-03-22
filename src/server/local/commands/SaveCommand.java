package server.local.commands;

import server.managers.DragonManager;

public class SaveCommand implements Command {
    private DragonManager dragonManager;

    public SaveCommand(DragonManager dragonManager){
        this.dragonManager = dragonManager;
    }

    @Override
    public void execute() {
        dragonManager.saveDragonsToCSV();
        System.out.println("Драконы успешно сохранены в файл");
    }
}
