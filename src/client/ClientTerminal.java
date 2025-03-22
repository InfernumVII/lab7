package client;

import java.io.IOException;
import java.util.Collection;

import client.commands.Command;
import client.managers.ClientCommandManager;
import managers.CommandManager;
import network.exceptions.TimeOutException;
import network.models.Answer;
import network.models.NetCommand;
import server.managers.exceptions.ParseCommandException;
import temp.TerminalWithCommandManager;

public class ClientTerminal extends TerminalWithCommandManager<ClientCommandManager> {
    private ClientUdpNetwork cUdpNetwork;
    public ClientTerminal(ClientUdpNetwork cUdpNetwork) {
        super(new ClientCommandManager());
        this.cUdpNetwork = cUdpNetwork;
        cManager.initDefaultCommands(this);
    }
    
    public void startLoop(){
        start(true, t -> {
            try {
                handleIter(t);
            } catch (ParseCommandException | TimeOutException e) {
                printIfOutputStateTrue(e.getMessage());
            } catch (IOException | ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        });
    }

    public void handleIter(String in) throws ParseCommandException, ClassNotFoundException, IOException, TimeOutException{
        NetCommand netCommand = cManager.getCommandFromRawInput(in);
        Answer answer = cUdpNetwork.sendAndGetAnswer(netCommand);
        if (outputState == true) { smartPrint(answer.answer()); }
    }

    public void smartPrint(Object in){
        if (in instanceof Collection){
            ((Collection<?>) in).stream().forEachOrdered(System.out::println);
        } else {
            System.out.println(in);
        }
    }
    
}
