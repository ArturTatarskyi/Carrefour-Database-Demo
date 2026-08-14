import java.util.Comparator;

public class TowarComparatorNazwa implements Comparator<Towar> {
    @Override
    public int compare(Towar o1, Towar o2) {
        return o1.compareTo(o2);
    }
}
