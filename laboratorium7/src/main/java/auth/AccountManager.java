package auth;
import com.password4j.Password;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private Map<Integer, Account> accountById = new HashMap<>();
    private Map<String, String> usernameToPassword = new HashMap<>();
    private int nextId = 1;

    //регістрація нововго користувача
    public void register(String username, String password){
        if(usernameToPassword.containsKey(username)){
            throw new IllegalArgumentException("Користувач вже існує");
        }
//створення зашифрованого паролю
       String hashedPassword = Password.hash(password).withBcrypt().getResult();
        Account account = new Account(nextId++, username);
        accountById.put(account.id(), account);
        usernameToPassword.put(username,hashedPassword);

    }

    // перевірка правильності пароля під час входу користувача в систему.

    public boolean authenticate(String username, String password) {
        String storedHash = usernameToPassword.get(username);
        // Перевіряємо, чи знайдений хеш і чи введений пароль відповідає збереженому хешу
        if (storedHash!=null && BCrypt.checkpw(password,storedHash)){
            return true;
        }
        return false;
    }

    public Account getAccount(String username){
        return accountById.get(username);
    }

    public Account getAccount(int id){
        return accountById.get(id);
    }

}
