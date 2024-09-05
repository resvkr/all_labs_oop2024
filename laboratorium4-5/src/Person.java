import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Person implements Serializable {
    private String name;
    private LocalDate birthdate;
    private LocalDate deathdate;
    List<Person> parents;

    public Person(String name, LocalDate birthdate, LocalDate deathdate) throws NegativeLifespanException {
        this.name = name;
        this.birthdate = birthdate;
        this.deathdate = deathdate;
        this.parents = new ArrayList<>();// Ініціалізація списку батьків

        if (deathdate != null && deathdate.isBefore(birthdate)) {
            throw new NegativeLifespanException("Дата смерті не може бути раніше дати народження");
        }
    }

    public static Person fromCsvLine(String line) throws NegativeLifespanException {
        // Розділяємо рядок на частини за комами

        String[] parts = line.split(",");


        String name = parts[0].trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate birthDate = LocalDate.parse(parts[1].trim(), formatter);

        LocalDate deathDate = null;
        if (parts.length > 2 && !parts[2].isEmpty()) {
            deathDate = LocalDate.parse(parts[2].trim(), formatter);
        }

        return new Person(name, birthDate, deathDate);
    }

    public void addParent(Person parent) throws ParentingAgeException {
        if (parent.birthdate != null && parent.birthdate.isAfter(this.birthdate.minusYears(15))) {
            throw new ParentingAgeException("Батько/мати є молодшими за 15 років.");
        }
        if (parent.deathdate != null && parent.deathdate.isBefore(this.birthdate)) {
            throw new ParentingAgeException("Батько/мати померли до народження дитини.");
        }
        parents.add(parent);
    }

    public static List<Person> fromCsv(String filepath) throws AmbiguousPersonException {
        List<Person> people = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        Map<String, Integer> nameCount = new HashMap<>();
        Map<String, Person> nameToPersonMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                lines.add(line);
                try {
                    Person person = fromCsvLine(line);
                    String name = person.name;

                    if (nameCount.containsKey(name)) {
                        nameCount.put(name, nameCount.get(name) + 1);
                        throw new AmbiguousPersonException("Знайдено більше ніж одну особу з ім'ям" + name);
                    } else {
                        nameCount.put(name, 1);
                        nameToPersonMap.put(name, person);
                    }

                    people.add(person);
                } catch (NegativeLifespanException e) {
                    System.err.println("Помилка при обробці рядка:" + e.getMessage());

                    // Запитуємо користувача, чи підтверджує помилку

                }
            }

            // Другий прохід для налаштування посилань на батьків

            for (int i = 0; i < people.size(); i++) {
                Person person = people.get(i);
                String[] parts = lines.get(i).split(",");

                if (parts.length > 4) {
                    String parent1Name = parts[3].trim();
                    String parent2Name = parts[4].trim();

                    if (!parent1Name.isEmpty() && nameToPersonMap.containsKey(parent1Name)) {
                        person.parents.add(nameToPersonMap.get(parent1Name));
                    }
                    if (!parent2Name.isEmpty() && nameToPersonMap.containsKey(parent2Name)) {
                        person.parents.add(nameToPersonMap.get(parent1Name));
                    }


                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return people;
    }

    // Метод для запису списку осіб у файл у бінарному форматі
    public static void toBinaryFile(List<Person> people, String filepath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(people);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для зчитування списку осіб з бінарного файлу
    public static List<Person> fromBinaryFile(String filepath) throws FileNotFoundException {
        List<Person> people = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            try {
                people = (List<Person>) ois.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return people;
    }


    public String toPlantUml(Function<String, String> postProcess, Predicate<Person> condition) {
        StringBuilder uml = new StringBuilder();

        String objectString = "object: " + name + " {\n" + " name: " + name + " \n" + " }\n";

        if(condition.test(this)){
            uml.append(postProcess.apply(objectString));
        }else {
            uml.append(objectString);
        }

        for (Person parent : parents) {
            if (parent != null) {
                String parentString = "object: " + parent.name + " {\n" + " name: " + parent.name + " \n" + " }\n";
                if(condition.test(parent)){
                    uml.append(postProcess.apply(parentString));
                }else {
                    uml.append(parentString);
                }

                uml.append(name).append("-->").append(parent.name).append("\n");
            }


        }
        return uml.toString();
    }


    public static String toPlantUmlDiagram(List<Person> people) {
        StringBuilder uml = new StringBuilder();

        uml.append("@startuml\n");
        for (Person person : people) {
            uml.append("object ").append(person.name).append(" {\n");
            uml.append("name: ").append(person.name).append(" {\n");
            uml.append("}\n");

            for (Person parent : person.parents) {
                if (parent != null) {
                    uml.append("object ").append(parent.name).append(" {\n");
                    uml.append("  name: ").append(parent.name).append("\n");
                    uml.append("}\n");

                    uml.append(person.name).append("-->").append(parent.name).append("\n");
                }
            }

        }
        return uml.toString();
    }

    public static List<Person> containsSubstring(List<Person> people, String substring) {
        List<Person> personWithSubstring = new ArrayList<>();

        for (Person person : people) {
            if (person.name.contains(substring)) {
                personWithSubstring.add(person);
            }
        }

        return personWithSubstring;
    }

    public static List<Person> sortByBirthDate(List<Person> people) {

        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).birthdate.isAfter(people.get(i + 1).birthdate)) {

                Person personTemp = people.get(i);
                people.set(i, people.get(i++));
                people.set(i++, personTemp);
            }
        }


        return people;
    }


    public static List<Person> sortDeadPeopeList(List<Person> people) {
        List<Person> deadPeople = new ArrayList<>();

        for (Person person : people) {
            if (person.deathdate == null) {
                deadPeople.add(person);
            }
        }

        for (int i = 0; i < deadPeople.size(); i++) {
            for (int j = i + 1; j < deadPeople.size(); j++) {
                if (deadPeople.get(i).deathdate.getYear() > deadPeople.get(j).deathdate.getYear()) {
                    Person temp = deadPeople.get(i);
                    deadPeople.set(i, deadPeople.get(j));
                    deadPeople.set(j, temp);
                }
            }
        }

        return deadPeople;
    }

    static public Person getOldeestPerson(List<Person> people) throws NegativeLifespanException {
        Person oldestPerson = new Person(null,null,null);
        for (Person person : people) {
            if (person.deathdate.getYear() - person.birthdate.getYear() < oldestPerson.deathdate.getYear() - oldestPerson.birthdate.getYear()) {
                oldestPerson = person;
            }
        }
        return oldestPerson;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", deathdate=" + deathdate +
                ", parents=" + parents +
                '}';
    }
}
