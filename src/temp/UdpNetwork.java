package temp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Scanner;

import client.ClientSettings;
import utility.BytesConversions;

public abstract class UdpNetwork {
    protected DatagramChannel datagramChannel;
    protected InetSocketAddress inetSocketAddress;
    protected Selector selector;
    protected InetSocketAddress lastSender;

    public InetSocketAddress getLastSender(){
        return lastSender;
    }

    protected InetSocketAddress getSocketAddress(Settings settings) throws UnknownHostException{
        InetAddress ip = InetAddress.getByName(settings.getIp());
        int port = settings.getPort();
        return new InetSocketAddress(ip, port);
    }

    protected DatagramChannel createDatagramChannel() throws IOException{
        DatagramChannel datagramChannel = DatagramChannel.open(); 
        datagramChannel.configureBlocking(false); //Сетевые каналы должны использоваться в неблокирующем режиме. (В рамках моего кода лучше использовать блокируемый режим)
        selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);
        return datagramChannel;
    }


    protected DatagramChannel createDatagramChannel(InetSocketAddress address) throws IOException{
        DatagramChannel datagramChannel = createDatagramChannel();
        datagramChannel.bind(inetSocketAddress);
        return datagramChannel;
    }
    
    public void send(byte[] bytes) throws IOException{
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        datagramChannel.send(buffer, inetSocketAddress);
    }
    public void send(byte[] bytes, InetSocketAddress inetSocketAddress) throws IOException{
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        datagramChannel.send(buffer, inetSocketAddress);
    }

    public void sendObject(Object object) throws IOException{
        byte[] serialized = BytesConversions.objectToBytes(object);
        send(BytesConversions.intToBytes(serialized.length));
        send(serialized);
        
    }

    public void sendObject(Object object, InetSocketAddress inetSocketAddress) throws IOException{
        byte[] serialized = BytesConversions.objectToBytes(object);
        send(BytesConversions.intToBytes(serialized.length), inetSocketAddress);
        send(serialized, inetSocketAddress);
        
    }

    public byte[] receive(int len) throws IOException{
        byte[] buf = new byte[len];
        ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
        selector.select(); // Блокировка до получения данных
        //SocketAddress senderAddress = datagramChannel.receive(byteBuffer); //java.io.EOFException
        lastSender = (InetSocketAddress) datagramChannel.receive(byteBuffer);
        return buf;
    }
    public byte[] handleLen() throws IOException {
        return receive(4);
    }
    
    public Object handleObject() throws IOException, ClassNotFoundException {
        byte[] lenBytes = handleLen();
        int length = BytesConversions.bytesToInt(lenBytes); ////https://ru.stackoverflow.com/questions/817289/Как-узнать-длину-пакета-по-datagramchannel (Другой - это сначала передать int или long, содержащий размер передаваемых данных, а потом передать столько данных.)
        byte[] buf = receive(length);
        return BytesConversions.bytesToObject(buf);
    }
    public Command handleCommand() throws IOException, ClassNotFoundException {
        Object obj = handleObject();
        return (Command) obj;
    }
    public Answer handleAnswer() throws IOException, ClassNotFoundException{
        Object obj = handleObject();
        return (Answer) obj;
    }

    
}
