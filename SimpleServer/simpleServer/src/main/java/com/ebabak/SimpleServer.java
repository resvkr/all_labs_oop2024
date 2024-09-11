package com.ebabak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static void main(String[] args) {

        // Порт, на якому сервер слухає підключення
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("\"Сервер запущений та слухає порт " + port);

            // Очікуємо підключення клієнта
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клієнт підключився");

            // Отримуємо потоки для читання та запису
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Читаємо повідомлення від клієнта
            String inputLine;
            while ((inputLine = in.readLine())!=null){

                System.out.println(inputLine);

                // Відправляємо відповідь клієнту
                out.println(inputLine);

                if (inputLine.equalsIgnoreCase("exit")){
                    break;
                }

            }

            // Закриваємо з'єднання
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("З'єднання завершене");
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
