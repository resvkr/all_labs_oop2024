package com.ebabak.demo;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private final Socket socket;// Сокет для зв'язку з клієнтом
    private final Server server;// Посилання на сервер, до якого цей клієнт підключений



    private Scanner in;// Сканер для зчитування даних з сокета

    public Client(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.in = new Scanner(socket.getInputStream());// Ініціалізуємо сканер для зчитування даних з сокета
    }

    @Override
    public void run() {
        while (true) {
            // Перевіряємо, чи є нові дані від клієнта
            if (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println("Client received: " + line);

                if (line.split(" ").length == 4) {
                    server.setPoints(line, this);
                } else if (line.length() == 6) {
                    server.setColor(line, this);
                } else {
                    System.out.println("Invalid input");
                }
            }

            if (socket.isClosed()) {
                try {
                    disconnect();// Відключаємося від сервера
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }


    }
    // Метод для відключення клієнта
    private void disconnect() throws IOException {
        in.close();
        socket.close();
        server.remove(this);
    }
}
