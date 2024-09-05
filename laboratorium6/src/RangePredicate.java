import java.util.function.Predicate;

public class RangePredicate <T extends Comparable<T>> implements Predicate<T> {
private final T lowerBound;
private final T upperBound;

    public RangePredicate(T lowerBound, T upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public boolean test(T value) {
        return value.compareTo(lowerBound)>0 && value.compareTo(upperBound)<0;


    }
}
