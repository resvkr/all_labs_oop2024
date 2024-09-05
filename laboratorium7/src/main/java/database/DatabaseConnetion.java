package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnetion {

    // Поле, яке зберігає об'єкт Connection для управління з'єднанням з базою даних.
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }


    // Метод для підключення до бази даних за вказаним шляхом.
    public void connect(String dbPath)throws SQLException{
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            System.out.println("База даних підключена");
        }catch (SQLException e){
            System.out.println("Підключення бази даних провалено");
            throw e;
        }
    }

    // Метод для відключення від бази даних.
    public void disconnect()throws SQLException{
        try {
            if (connection!=null&&!connection.isClosed()){
                connection.close();
                System.out.println("База даних відключена");
            }
        }catch (SQLException e){
            System.out.println("Не вдалось відключити базу даних");
            throw e;
        }
    }
}
