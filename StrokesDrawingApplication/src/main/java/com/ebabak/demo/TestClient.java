package com.ebabak.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TestClient {
    private static final String SERVER_ADDRESS = "localhost"; // або IP адреса сервера
    private static final int SERVER_PORT = 8082; // порт, на якому працює сервер

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner in = new Scanner(socket.getInputStream())) {


            // Надсилаємо кілька тестових команд до сервера
            out.println("FF0000");

            out.println("50 50 150 150");

            // Чекаємо на відповідь від сервера
            while (in.hasNextLine()) {
                String response = in.nextLine();
                System.out.println("Server response: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
