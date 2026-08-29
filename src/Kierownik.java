public class Kierownik extends Pracownik {

    public static final double STAWKA_KIEROWNIK = 40;

    public Kierownik(
            String imie,
            String nazwisko,
            int wiek,
            String data) {

        super(
                imie,
                nazwisko,
                wiek,
                Stanowisko.KIEROWNIK,
                data
        );
    }

    public Kierownik(
            String imie,
            String nazwisko,
            int wiek,
            Stanowisko stanowisko,
            String data) {

        super(
                imie,
                nazwisko,
                wiek,
                stanowisko,
                data
        );
    }

    public Kierownik(
            int id,
            String imie,
            String nazwisko,
            int wiek,
            String data) {

        super(
                id,
                imie,
                nazwisko,
                wiek,
                Stanowisko.KIEROWNIK,
                data
        );
    }

    public Kierownik(
            int id,
            String imie,
            String nazwisko,
            int wiek,
            Stanowisko stanowisko,
            String data) {

        super(
                id,
                imie,
                nazwisko,
                wiek,
                stanowisko,
                data
        );
    }

    @Override
    public String toString() {
        return super.toString();
    }
}