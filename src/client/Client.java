package client;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

import collection.Dragon;
import managers.DragonManager;
import utility.CSV;
import utility.DragonCSVParser;

import java.io.*;
import java.math.BigInteger;

public class Client {
    public static void main(String[] args){
    
        
        
        List<String[]> parsedDragons = CSV.read("test.1.csv");
        DragonManager dragonManager = new DragonManager();
        for (String[] strings : parsedDragons) {
            Dragon dragon = DragonCSVParser.parseDragonFromRow(strings);
            dragonManager.addDragon(dragon);
            //System.out.println(dragon);
        }
        
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
        byte[] serialized;
        try (ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream)) {
            for (Dragon dragon : dragonManager.getDragonSet()) {
                out.writeObject(dragon);
            }
            serialized = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            InetAddress ip = InetAddress.getByName("localhost");
            int port = 1111;
            InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
            DatagramChannel datagramChannel = DatagramChannel.open();
            ByteBuffer byteBuffer = ByteBuffer.wrap(serialized);
            datagramChannel.send(ByteBuffer.wrap(intToBytes(serialized.length)), inetSocketAddress);
            datagramChannel.send(byteBuffer, inetSocketAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    public static byte[] intToBytes(int x) { //https://stackoverflow.com/questions/2183240/java-integer-to-byte-array
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bos);
            out.writeInt(x);
            byte[] int_bytes = bos.toByteArray();
            return int_bytes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}