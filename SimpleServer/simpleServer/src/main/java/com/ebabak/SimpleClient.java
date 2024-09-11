package com.ebabak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args) {

        // IP-адреса та порт сервера

        String serverAddress = "localhost";
        int serverPort = 8080;

        try(Socket socket = new Socket(serverAddress, serverPort)){

            // Отримуємо потоки для читання та запису
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Підключено до сервера. Введіть повідомлення:");

            String userMessage;

            while ((userMessage = userInput.readLine())!= null){
                // Відправляємо повідомлення серверу
                out.println(userMessage);

                // Отримуємо відповідь від сервера
                String response = in.readLine();
                System.out.println("Сервер відповів: " + response);

                // Завершуємо роботу, якщо ввели "exit"
                if (userMessage.equalsIgnoreCase("exit")){
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
