package client;

import java.io.IOException;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            // Создаем DatagramSocket
            DatagramSocket socket = new DatagramSocket();

            // Адрес и порт сервера
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 8080;

            // Сообщение для отправки
            String message = "Привет от клиента!";
            byte[] buffer = message.getBytes();

            // Создаем пакет для отправки
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);

            // Отправляем пакет
            socket.send(packet);
            System.out.println("Сообщение отправлено серверу: " + message);

            // Получаем ответ от сервера
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Получено сообщение от сервера: " + receivedMessage);

            // Закрываем сокет
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}