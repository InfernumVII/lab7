package server;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

import collection.Dragon;
import managers.DragonManager;
import utility.CSV;
import utility.DragonCSVParser;

import java.io.*;

public class Server {
    public static void main(String[] args){
        /*
        ByteArrayInputStream bis = new ByteArrayInputStream(serialized);
        try (ObjectInput in = new ObjectInputStream(bis)) {
            while (bis.available() != 0) {
                Dragon dragon = (Dragon) in.readObject();
                System.out.println(dragon);
            }
            
            //Dragon dragon2 = (Dragon) in.readObject();
           
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
        

        try {
            InetAddress ip = InetAddress.getByName("0.0.0.0");
            int port = 1111;
            InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.bind(inetSocketAddress);
            while (true) {
                
                byte[] lengthBytes = new byte[4];
                ByteBuffer lengthByteBuffer = ByteBuffer.wrap(lengthBytes);
                datagramChannel.receive(lengthByteBuffer);
                byte[] buf = new byte[bytesToInt(lengthBytes)]; //https://ru.stackoverflow.com/questions/817289/Как-узнать-длину-пакета-по-datagramchannel (Другой - это сначала передать int или long, содержащий размер передаваемых данных, а потом передать столько данных.)

                ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
                datagramChannel.receive(byteBuffer);

                ByteArrayInputStream bis = new ByteArrayInputStream(buf);
                try (ObjectInput in = new ObjectInputStream(bis)) {
                    while (bis.available() != 0) {
                        Dragon dragon = (Dragon) in.readObject();
                        System.out.println(dragon);
                    }
                    
                    //Dragon dragon2 = (Dragon) in.readObject();
                
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static int bytesToInt(byte[] bytes){
        try {
            int out;
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            DataInputStream in = new DataInputStream(bis);
            out = in.readInt();
            return out;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        

            
            //Dragon dragon2 = (Dragon) in.readObject();
           
        
    }
}