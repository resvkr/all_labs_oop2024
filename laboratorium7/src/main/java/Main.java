import auth.Account;
import auth.AccountManager;
import database.DatabaseConnetion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {

        // Створюємо новий об'єкт для керування підключенням до бази даних.
        DatabaseConnetion dbConnetion = new DatabaseConnetion();

        try {
            // Підключення до бази даних
            dbConnetion.connect("test.db");


            // Отримання з'єднання
            Connection conn = dbConnetion.getConnection();

            // Створення таблиці
            // Використовуємо Statement для виконання SQL-команд.
            try (Statement statement = conn.createStatement()){
                String sql = "CREATE TABLE IF NOT EXISTS users (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "age INTEGER NOT NULL)";

                // Виконуємо SQL-запит для створення таблиці.
                statement.execute(sql);
                System.out.println("Таблиця створена або вже існує");
            }

            try(Statement statement = conn.createStatement()) {
                String sql = "INSERT INTO users (name, age) VALUES ('John Doe', 30)";
                statement.executeUpdate(sql);
                System.out.println("Дані вставлено");

            }

            try (Statement statement = conn.createStatement()){
                String sql = "SELECT * FROM users";
                // Виконуємо запит і отримуємо результати у вигляді ResultSet.
                ResultSet result = statement.executeQuery(sql);

                while (result.next()){
                    //в скобочках название столбца
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    int age = result.getInt("age");
                    System.out.println("ID"+id+"Name"+name+"Age"+age);
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        finally {
            dbConnetion.disconnect();
        }

        AccountManager accountManager = new AccountManager();

        // Реєструємо два нових акаунти з логінами і паролями.
        accountManager.register("user1", "password123");
        accountManager.register("user2", "password345");

        // Перевіряємо, чи вдалося автентифікувати акаунт "user1" з відповідним паролем.
        boolean isAuthenticated = accountManager.authenticate("user1", "password123");
        System.out.println("Authentication successful: " + isAuthenticated);

        // Отримуємо акаунт за логіном "user1".
        Account account1 = accountManager.getAccount("user1");
        System.out.println("Account ID: " + account1.id() + ", Username: " + account1.username());
    }

}
