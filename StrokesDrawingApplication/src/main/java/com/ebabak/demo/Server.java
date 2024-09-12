package com.ebabak.demo;

import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable{
    private final ServerSocket serverSocket;// Серверний сокет для прослуховування підключень
    private final List<Client> clients;// Список підключених клієнтів


    // Конструктор сервера, що ініціалізує сокет і список клієнтів
    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.clients = new ArrayList<>();
    }


    @Override
    public void run() {
        while(true) {
            try {
                // Приймаємо нове підключення і створюємо новий клієнтський об'єкт
                Client client = new Client(serverSocket.accept(), this);
                add(client);
                new Thread(client).start();  // Запускаємо новий потік для обробки клієнта
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void add(Client client){
        clients.add(client);
    }

    public void remove(Client client){
        clients.remove(client);
    }


    // Встановлюємо колір для конкретного клієнта
    public void setColor(String hexColor, Client client){
        Color color = Color.web("#"+hexColor);
        DrawApplication.setColor(client,color);
    }

    // Встановлюємо точки для малювання
    public void setPoints(String points, Client client){
        DrawApplication.drawLines(points, client);
    }
}
