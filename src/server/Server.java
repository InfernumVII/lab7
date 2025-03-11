package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    public static void main(String[] args) {
        try {
            // Открываем канал сервера
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));

            System.out.println("Сервер запущен и ожидает подключения...");

            // Принимаем подключение от клиента
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("Клиент подключен: " + socketChannel.getRemoteAddress());

            // Чтение данных от клиента
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int bytesRead = socketChannel.read(buffer);
            if (bytesRead > 0) {
                buffer.flip();
                String message = new String(buffer.array()).trim();
                System.out.println("Получено сообщение от клиента: " + message);
            }

            // Отправка ответа клиенту
            String response = "Привет от сервера!";
            ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
            socketChannel.write(responseBuffer);

            // Закрываем соединение
            socketChannel.close();
            serverSocketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}