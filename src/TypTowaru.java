import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TypTowaru {

    private static final List<TypTowaru> typy = new ArrayList<>();

    public static final TypTowaru JEDZENIE =
            new TypTowaru("JEDZENIE");

    public static final TypTowaru ODZIEZ =
            new TypTowaru("ODZIEZ");

    public static final TypTowaru MEBLE =
            new TypTowaru("MEBLE");

    public static final TypTowaru ELEKTRONIKA =
            new TypTowaru("ELEKTRONIKA");

    public static final TypTowaru CHEMIA =
            new TypTowaru("CHEMIA");

    private final String nazwa;

    private TypTowaru(String nazwa) {
        this.nazwa = nazwa;
        typy.add(this);
    }

    public String getNazwa() {
        return nazwa;
    }

    public static TypTowaru dodajTyp(String nazwa) {

        if (nazwa == null || nazwa.isBlank()) {
            throw new IllegalArgumentException(
                    "Nazwa typu nie moze byc pusta."
            );
        }

        String poprawionaNazwa =
                nazwa.trim().toUpperCase();

        for (TypTowaru typ : typy) {

            if (typ.nazwa.equals(poprawionaNazwa)) {
                return typ;
            }
        }

        return new TypTowaru(poprawionaNazwa);
    }

    public static TypTowaru valueOf(String nazwa) {

        if (nazwa == null) {
            throw new IllegalArgumentException(
                    "Nazwa typu nie moze byc pusta."
            );
        }

        String poprawionaNazwa =
                nazwa.trim().toUpperCase();

        for (TypTowaru typ : typy) {

            if (typ.nazwa.equals(poprawionaNazwa)) {
                return typ;
            }
        }

        return dodajTyp(poprawionaNazwa);
    }

    public static TypTowaru[] values() {
        return typy.toArray(new TypTowaru[0]);
    }

    public static boolean czyPodstawowy(
            TypTowaru typ) {

        return typ == JEDZENIE
                || typ == ODZIEZ
                || typ == MEBLE
                || typ == ELEKTRONIKA
                || typ == CHEMIA;
    }

    public static boolean usunTyp(
            TypTowaru typ) {

        if (typ == null) {
            return false;
        }

        if (czyPodstawowy(typ)) {
            return false;
        }

        return typy.remove(typ);
    }

    @Override
    public String toString() {
        return nazwa;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof TypTowaru)) {
            return false;
        }

        TypTowaru typTowaru = (TypTowaru) o;

        return nazwa.equals(typTowaru.nazwa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nazwa);
    }
}