public class Towar implements Comparable<Towar> {

    private int id;
    private String nazwa;
    private TypTowaru typ;
    private double cena;
    private int ilosc;

    public Towar(String nazwa, TypTowaru typ, double cena, int ilosc) {
        this.id = GeneratorID.generujID();
        this.nazwa = nazwa;
        this.typ = typ;
        this.cena = cena;
        this.ilosc = ilosc;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Towar o) {
        return this.nazwa.compareTo(o.nazwa);
    }

    @Override
    public String toString() {
        return id + " | " + nazwa + " | " + typ +
                " | " + cena + " zl | ilosc: " + ilosc;
    }
}
