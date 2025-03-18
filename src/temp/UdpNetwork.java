package temp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import client.ClientSettings;
import utility.BytesConversions;

public abstract class UdpNetwork {
    protected DatagramChannel datagramChannel;
    protected InetSocketAddress inetSocketAddress;

    protected InetSocketAddress getSocketAddress(Settings settings) throws UnknownHostException{
        InetAddress ip = InetAddress.getByName(settings.getIp());
        int port = settings.getPort();
        return new InetSocketAddress(ip, port);
    }

    protected DatagramChannel createDatagramChannel() throws IOException{
        DatagramChannel datagramChannel = DatagramChannel.open(); 
        datagramChannel.configureBlocking(false); //Сетевые каналы должны использоваться в неблокирующем режиме. (В рамках моего кода лучше использовать блокируемый режим)
        return datagramChannel;
    }
    protected DatagramChannel createDatagramChannel(InetSocketAddress address) throws IOException{
        DatagramChannel datagramChannel = createDatagramChannel();
        datagramChannel.bind(inetSocketAddress);
        return datagramChannel;
    }
    
    public void send(byte[] bytes) throws IOException{
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        while (buffer.hasRemaining()) { // Убеждаемся что все данные были отправлены
            datagramChannel.send(buffer, inetSocketAddress);
            System.out.println(1);
        }
    }

    public void sendObject(Object object) throws IOException{
        byte[] serialized = BytesConversions.objectToBytes(object);
        send(BytesConversions.intToBytes(serialized.length));
        send(serialized);
        
    }
    public byte[] receive(int len) throws IOException, InterruptedException{
        byte[] buf = new byte[len];
        System.out.println(len);
        ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
        datagramChannel.receive(byteBuffer);
        InetSocketAddress senderAddress = (InetSocketAddress) datagramChannel.receive(byteBuffer); //java.io.EOFException
        while (senderAddress == null) {
            Thread.sleep(100);
            senderAddress = (InetSocketAddress) datagramChannel.receive(byteBuffer);
        } 
        System.out.println("Isus");
        
        System.out.println(byteBuffer.hasRemaining());
        return buf;
    }
    public byte[] handleLen() throws IOException, InterruptedException {
        return receive(4);
    }
    
    public Object handleObject() throws IOException, ClassNotFoundException, InterruptedException {
        byte[] lenBytes = handleLen();
        int length = BytesConversions.bytesToInt(lenBytes); ////https://ru.stackoverflow.com/questions/817289/Как-узнать-длину-пакета-по-datagramchannel (Другой - это сначала передать int или long, содержащий размер передаваемых данных, а потом передать столько данных.)
        byte[] buf = receive(length);
        return BytesConversions.bytesToObject(buf);
    }
    public Command handleCommand() throws IOException, ClassNotFoundException, InterruptedException {
        Object obj = handleObject();
        return (Command) obj;
    }
    
}
