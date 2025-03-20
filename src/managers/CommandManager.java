package managers;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class CommandManager<V> {
    private final static int HISTORY_SIZE = 5;
    private Deque<String> history = new ArrayDeque<String>(HISTORY_SIZE);
    protected Map<String, V> commands = new HashMap<>();

    public void registerCommand(String name, V command){
        commands.put(name, command);
    }
    public abstract Object executeCommand(String name, Object arg);

    public Deque<String> getHistory(){
        return history;
    }

    public Map<String, V> getCommands() {
        return commands;
    }

    public Set<String> listedNames(){
        return commands.keySet();
    }

    protected void storeCommand(String command){
        if (history.size() >= HISTORY_SIZE) {
            history.removeFirst(); 
        }
        history.addLast(command);
    }
}
