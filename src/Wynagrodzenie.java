public class Wynagrodzenie implements Comparable<Wynagrodzenie> {

    private Pracownik pracownik;
    private int godziny;
    private double kwota;
    private String okres; // np. 01.2024

    public Wynagrodzenie(Pracownik pracownik, int godziny, String okres) {
        this.pracownik = pracownik;
        this.godziny = godziny;
        this.okres = okres;

        double stawka = (pracownik instanceof Kierownik)
                ? Kierownik.STAWKA_KIEROWNIK
                : Pracownik.STAWKA;

        this.kwota = stawka * godziny;
    }

    public int getPracownikId() {
        return pracownik.getId();
    }

    public String getOkres() {
        return okres;
    }

    @Override
    public int compareTo(Wynagrodzenie o) {
        return this.pracownik.nazwisko.compareTo(o.pracownik.nazwisko);
    }

    @Override
    public String toString() {
        return pracownik.getId() + " | " +
                pracownik.imie + " " + pracownik.nazwisko +
                " | " + pracownik.stanowisko +
                " | godziny: " + godziny +
                " | kwota: " + kwota + " zl" +
                " | okres: " + okres;
    }
}
