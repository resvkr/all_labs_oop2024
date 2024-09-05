import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) throws NegativeLifespanException, AmbiguousPersonException {

        String line = "Marek Kowalski,15.05.1899,25.06.1957,,";
        System.out.println(Person.fromCsvLine(line));

        String filePath = "C:\\Users\\User\\IdeaProjects\\laboratorium4\\src\\family.csv";

        List<Person> people= Person.fromCsv(filePath);
        for (Person person: people){
            System.out.println(person);
        }


        Person child = new Person("John", null, null);
        Person parent1 = new Person("Mary", null, null);
        Person parent2 = new Person("Peter", null, null);
        child.parents = List.of(parent1, parent2);

        Function<String,String> yellowColor = (s)-> s.replace("object", "object #yellow");
        Function<String, String> doingNothing = Function.identity();



    }


}
