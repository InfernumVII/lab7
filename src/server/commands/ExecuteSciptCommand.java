package server.commands;

public class ExecuteSciptCommand implements Command {
    
    @Override
    public boolean isHasArgs(){
        return true;
    }

    @Override
    public Object execute(Object arg){
        return "Команды успешно выполнены";
    }

    @Override
    public String getDescription(){
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }


    @Override
    public String stringArgument(){
        return "file_name";
    }
    
}
