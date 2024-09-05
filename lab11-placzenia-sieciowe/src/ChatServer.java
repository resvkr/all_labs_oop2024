import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChatServer {

    private static Set<ClientHandler> clientHandlers = new HashSet<>();
    private static Map<ClientHandler, String> clientLogins = new HashMap<>();

    public static void main(String[] args) {

        // Створюємо серверний сокет на порту 12345
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Сервер запущено...");


            // Сервер працює в нескінченному циклі, очікуючи нових клієнтів
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Новий клієнт підключився");


                // Створюємо новий обробник клієнта і запускаємо його в окремому потоці
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Помилка сервера: " + e.getMessage());
        }
    }


    // Метод для розсилки повідомлення всім клієнтам
    public static void broadcastMessage(String message, ClientHandler client) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler != client) {
                clientHandler.sendMessage(message);
            }
        }
    }

    // Метод для відправки приватного повідомлення
    public static void sendPrivateMessage(String recipient, String message, ClientHandler sender) {
        boolean found = false;
        for (Map.Entry<ClientHandler, String> entry : clientLogins.entrySet()) {
            if (entry.getValue().equals(recipient)) {
                entry.getKey().sendMessage("Приватне повідомлення від " + clientLogins.get(sender) + ": " + message);
                found = true;
                break;
            }
        }
        if (!found) {
            sender.sendMessage("Користувач " + recipient + " не залогований.");
        }
    }


    // Метод для видалення клієнта з активних підключень
    public static void removeClient(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }


    // Метод для отримання списку залогованих користувачів
    public static String getOnlineUsers() {
        return "Залоговані користувачі:" + String.join(", ", clientLogins.values());
    }


    // Клас для обробки клієнтських з'єднань!!!!!
    static class ClientHandler implements Runnable {

        // Змінна для зберігання об'єкта Socket, який представляє з'єднання між сервером і конкретним клієнтом.
        // Змінна для зберігання об'єкта PrintWriter, який дозволяє відправляти дані (текстові повідомлення) від сервера до клієнта.
        // Змінна для зберігання об'єкта BufferedReader, який дозволяє отримувати дані (текстові повідомлення) від клієнта до сервера.
        // Логін користувача

        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String login;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try {
                // Встановлюємо вхідний та вихідний потоки для клієнта

                // Створюємо BufferedReader для зручного та ефективного читання текстових даних, які надходять від клієнта через сокет.
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Створюємо PrintWriter для відправки текстових даних клієнту через сокет з автоматичним очищенням буфера.
                out = new PrintWriter(socket.getOutputStream(), true);


                // Надсилаємо повідомлення, що клієнт підключився
                out.println("Ви підключені до сервера!");


                // Очікуємо на отримання логіну від клієнта
                String loginMessage = in.readLine();
                if (loginMessage != null && loginMessage.startsWith("Логін: ")) {
                    login = login.substring(7);// Витягуємо логін
                    clientLogins.put(this, login);
                    broadcastMessage("Користувач " + login + " приєднався до чату!", this);
                }


                String message;
                // Очікуємо на повідомлення від клієнта

                while ((message = in.readLine()) != null) {
                    System.out.println("Отримано: " + message);

                    // Розсилаємо отримане повідомлення всім клієнтам
                    ChatServer.broadcastMessage(message, this);
                }
            } catch (IOException e) {
                System.out.println("Помилка обробки клієнта: " + e.getMessage());
            } finally {
                try {
                    // Закриваємо з'єднання
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Не вдалося закрити з'єднання: " + e.getMessage());
                }

                // Видаляємо клієнта зі списку активних
                ChatServer.removeClient(this);
            }
        }


        // Метод для відправки повідомлення клієнту
        public void sendMessage(String message) {
            out.println(message);
        }
    }
}
