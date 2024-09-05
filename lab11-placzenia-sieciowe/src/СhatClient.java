import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class СhatClient{
    private static final String serverAddress = "localhost";
    private static final int serverPort = 12345;
    private String login; // Логін користувача


    public static void main(String[] args) throws IOException {
        // Створюємо новий екземпляр ChatClient та запускаємо метод start
        new СhatClient().start();
    }

    public void start() throws IOException {
        // Підключаємося до сервера за вказаною адресою та портом
        Socket socket = new Socket(serverAddress, serverPort);
        System.out.println("Підключено до сервера");


        // Створюємо об'єкти для читання та запису даних


        // Для читання повідомлень від сервера
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // Для відправки повідомлень на сервер
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        // Для зчитування вводу користувача з консолі
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));


        // Запитуємо логін користувача
        System.out.println("Введіть свій логін: ");
        login = userInput.readLine();
        out.println("Логін: " + login);// Надсилаємо логін на сервер


        // Запускаємо новий потік, який буде асинхронно отримувати повідомлення від сервера
        new Thread(() -> {

            // Змінна для зберігання отриманих повідомлень від сервера
            String serverMessage;

            try {
                // Входимо в нескінченний цикл для постійного зчитування повідомлень від сервера
                while ((serverMessage = in.readLine()) != null) {
                    // Виводимо отримане повідомлення на консоль
                    System.out.println("Сервер: " + serverMessage);
                }
            } catch (IOException e) {
                // Виводимо повідомлення про помилку, якщо не вдалося отримати дані від сервера
                System.out.println("Помилка при отриманні повідомлень: " + e.getMessage());
            }
            }).start();  // Завершення потоку, тут закривається лямбда-вираз



        // Основний цикл для зчитування вводу користувача та відправки його на сервер
           String userMessage;
           while ((userMessage = userInput.readLine())!=null){
               out.println(login + ": "+userMessage);// Надсилаємо повідомлення разом з логіном на сервер
           }



        // Якщо клієнт виходить, повідомляємо сервер про вихід
           out.println("Користувач " + login + " вийшов з чату.");
           socket.close();// Закриваємо з'єднання
        }
    }

