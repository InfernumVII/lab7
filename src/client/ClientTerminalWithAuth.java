package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Scanner;

import client.commands.RegistrationCommand;
import network.Settings;
import network.exceptions.TimeOutException;
import network.models.Answer;
import network.models.NetCommand;
import network.models.NetCommandAuth;
import server.managers.exceptions.ParseCommandException;

public class ClientTerminalWithAuth extends ClientTerminal {
    private RegistrationCommand registrationCommand = new RegistrationCommand();
    public ClientTerminalWithAuth(ClientUdpNetwork cUdpNetwork){
        super(cUdpNetwork);
        addRegistration();
    }

    private void addRegistration(){
        cManager.registerCommand("auth", registrationCommand);
        setScanner(new Scanner(makeSeqStream()));
    }

    private InputStream makeSeqStream(){
        InputStream firstStream = new ByteArrayInputStream("auth\n".getBytes());
        InputStream combinedStream = new SequenceInputStream(firstStream, System.in);
        return combinedStream;
    }

    @Override
    public void handleIter(String in){
        try {
            NetCommand netCommand = cManager.getCommandFromRawInput(in);
            NetCommandAuth netCommandAuth = new NetCommandAuth(netCommand.command(), netCommand.arg(), registrationCommand.getUser());
            Answer answer = cUdpNetwork.sendAndGetAnswer(netCommandAuth);
            smartPrint(answer.answer());
        } catch (TimeOutException | ParseCommandException e) {
            System.err.println(e.getMessage());
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        
    }

    
}
