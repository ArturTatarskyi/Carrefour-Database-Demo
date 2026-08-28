import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stanowisko {

    private static final List<Stanowisko> stanowiska =
            new ArrayList<>();

    public static final Stanowisko PRACOWNIK =
            new Stanowisko(
                    "PRACOWNIK",
                    false
            );

    public static final Stanowisko KASJER =
            new Stanowisko(
                    "KASJER",
                    false
            );

    public static final Stanowisko MAGAZYNIER =
            new Stanowisko(
                    "MAGAZYNIER",
                    false
            );

    public static final Stanowisko KIEROWNIK =
            new Stanowisko(
                    "KIEROWNIK",
                    true
            );

    private final String nazwa;
    private final boolean kierownicze;

    private Stanowisko(
            String nazwa,
            boolean kierownicze) {

        this.nazwa = nazwa;
        this.kierownicze = kierownicze;

        stanowiska.add(this);
    }

    public String getNazwa() {
        return nazwa;
    }

    public boolean isKierownicze() {
        return kierownicze;
    }

    public static Stanowisko dodajStanowisko(
            String nazwa,
            boolean kierownicze) {

        if (nazwa == null
                || nazwa.isBlank()) {

            throw new IllegalArgumentException(
                    "Nazwa stanowiska nie moze byc pusta."
            );
        }

        String poprawionaNazwa =
                nazwa.trim().toUpperCase();

        for (Stanowisko stanowisko :
                stanowiska) {

            if (stanowisko.nazwa
                    .equals(poprawionaNazwa)) {

                return stanowisko;
            }
        }

        return new Stanowisko(
                poprawionaNazwa,
                kierownicze
        );
    }

    public static Stanowisko dodajStanowisko(
            String nazwa) {

        return dodajStanowisko(
                nazwa,
                false
        );
    }

    public static Stanowisko valueOf(
            String nazwa) {

        if (nazwa == null) {

            throw new IllegalArgumentException(
                    "Nazwa stanowiska nie moze byc pusta."
            );
        }

        String poprawionaNazwa =
                nazwa.trim().toUpperCase();

        for (Stanowisko stanowisko :
                stanowiska) {

            if (stanowisko.nazwa
                    .equals(poprawionaNazwa)) {

                return stanowisko;
            }
        }

        return dodajStanowisko(
                poprawionaNazwa,
                false
        );
    }

    public static Stanowisko[] values() {

        return stanowiska.toArray(
                new Stanowisko[0]
        );
    }

    public static boolean czyPodstawowe(
            Stanowisko stanowisko) {

        return stanowisko == PRACOWNIK
                || stanowisko == KASJER
                || stanowisko == MAGAZYNIER
                || stanowisko == KIEROWNIK;
    }

    public static boolean usunStanowisko(
            Stanowisko stanowisko) {

        if (stanowisko == null) {
            return false;
        }

        if (czyPodstawowe(stanowisko)) {
            return false;
        }

        return stanowiska.remove(
                stanowisko
        );
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

        if (!(o instanceof Stanowisko)) {
            return false;
        }

        Stanowisko stanowisko =
                (Stanowisko) o;

        return nazwa.equals(
                stanowisko.nazwa
        );
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                nazwa
        );
    }
}