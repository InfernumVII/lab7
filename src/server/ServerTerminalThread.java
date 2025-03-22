package server;

import server.local.ServerTerminal;

public class ServerTerminalThread extends Thread {
    private ServerTerminal serverTerminal;
    public ServerTerminalThread(ServerTerminal serverTerminal){
        setDaemon(false);
        this.serverTerminal = serverTerminal;
    }
    public void run(){
        serverTerminal.start(isAlive());
    }
}
