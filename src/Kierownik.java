public class Kierownik extends Pracownik {

    public static final double STAWKA_KIEROWNIK = 40;

    public Kierownik(String imie, String nazwisko, int wiek, String data) {
        super(imie, nazwisko, wiek, Stanowisko.KIEROWNIK, data);
    }

    @Override
    public String toString() {
        return "KIEROWNIK | " + super.toString();
    }
}
