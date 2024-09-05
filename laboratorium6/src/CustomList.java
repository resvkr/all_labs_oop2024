import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomList<T> extends AbstractList<T> {
    private Node head;
    private Node tail;
    private int size;
    //Клас Node зазвичай містить дані (payload) та посилання на наступний елемент у списку
    @Override
    public boolean add(T t){
        Node newNode = new Node(t);
        if (tail==null){
            tail = head = newNode;
        }else {
            tail.next = newNode;
            tail=newNode;
        }
        size ++;
        return true;
    }

    @Override
    public T get(int index) {
        if (index<0&&index>=size){
            throw new RuntimeException("Index out of list");
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current=current.next;
        }
        return current.data;
    }

    @Override
    public int size() {
        return size;
    }

    private class Node {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }


    public CustomList(Node head, Node tail) {
        this.head = null;
        this.tail = null;
    }

    // Метод для додавання елемента в кінець списку
    public void addLast(T value) {
        Node newNode = new Node(value);
        if (tail == null) {
            head = tail = null;
        } else {
            tail.next = null;
            tail = newNode;
        }
    }

    // Метод для отримання значення з кінця списку
    public T getLast() {
        if (tail == null) {
            throw new RuntimeException("List is empty");
        } else {
            return tail.data;
        }
    }

    // Метод для додавання елемента на початок списку
    public void addFirst(T value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = tail = null;
        } else {
            newNode.next = head;
            head = newNode;
        }
    }

    // Метод для отримання значення з початку списку
    public T getFirst() {
        if (head == null) {
            throw new RuntimeException("List is empty");
        } else {
            return head.data;
        }
    }

    // Метод для видалення та повернення елемента з початку списку
    public T removeFirst() {
        if (head == null) {
            throw new RuntimeException("List is empty");
        } else {
            T value = head.data;
            head = null;

            if (head == null) {
                tail = null;
            }

            return value;
        }

    }

    // Метод для видалення та повернення елемента з кінця списку
    public T removeLast() {
        if (tail == null) { // Якщо список порожній
            throw new RuntimeException("Список порожній");
        }
        T value;
        if (head == tail) { // Якщо в списку один елемент
            value = tail.data;
            head = tail = null;
        }else {
            // Якщо в списку більше одного елемента
            Node current = head;
            while (current.next != tail) { // Проходимо до передостаннього вузла
                current = current.next;
            }
            value = tail.data;
            tail = current; // Оновлюємо хвіст, щоб він став передостаннім вузлом
            tail.next = null; // Останній вузол тепер не має наступного
        }
        return value;
    }


    // Метод повертає ітератор для списку
    @Override
    public Iterator<T> iterator(){
        return new Iterator<T>() {

            private Node current = head;

            @Override
            public boolean hasNext() {
                return current!=null;
            }

            @Override
            public T next() {
                if (!hasNext()){
                    try {
                        throw new NoSuchMethodException();
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }

                T data  = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public Stream<T> stream() {
        // Метод, який перетворює колекцію у потік (Stream), дозволяючи застосовувати операції потокової обробки на елементах колекції.

        return StreamSupport.stream(
                // Створюємо новий потік (Stream) на основі специфікації колекції, використовуючи StreamSupport.

                Spliterators.spliterator(iterator(), size, Spliterator.ORDERED),
                // Використовуємо Spliterator для ітерації по елементах колекції.
                // iterator() - отримує ітератор для колекції.
                // size - кількість елементів у колекції.
                // Spliterator.ORDERED - позначає, що порядок елементів у потоці повинен зберігатися.

                false
                // Аргумент "false" означає, що створюється послідовний потік (не паралельний).
        );
    }

    public static <T> List<T> filterByType(List<?> list, Class<T> clazz) {
        List<T> result = new ArrayList<>();

        for (Object object: list){
            // Перевіряємо, чи є об'єкт нащадком вказаного класу
            if(clazz.isAssignableFrom(object.getClass())){
                // Безпечно перетворюємо об'єкт до типу T і додаємо в результат
                result.add(clazz.cast(object));
            }
        }
        return result;
    }

    public static <T> int countRange(List<T> list, Predicate<T> predicate) {
        // Метод, який підраховує кількість елементів у списку, що відповідають умові, заданій предикатом.
        // <T> - узагальнений тип (generic), що дозволяє методу працювати з будь-яким типом даних.
        // list - список елементів, які будуть перевірятися.
        // predicate - умова (функція), яку має задовольняти елемент.

        int count = 0;
        // Змінна, яка зберігає кількість елементів, що відповідають умові.

        for (T element : list) {
            // Проходимо по кожному елементу списку за допомогою циклу "for-each".

            if (predicate.test(element)) {
                // Якщо поточний елемент задовольняє умову предиката (test повертає true),
                // то збільшуємо лічильник.

                count++;
                // Збільшуємо лічильник на 1, якщо умова виконана.
            }
        }

        return count;
        // Повертаємо загальну кількість елементів, що відповідають предикату.
    }

}