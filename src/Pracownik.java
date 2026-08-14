import java.util.Objects;

public class Pracownik extends Osoba implements Comparable<Pracownik> {

    protected int id;
    protected int wiek;
    protected Stanowisko stanowisko;
    protected String dataZatrudnienia;

    public static final double STAWKA = 30;

    public Pracownik(String imie, String nazwisko, int wiek,
                     Stanowisko stanowisko, String data) {
        super(imie, nazwisko);
        this.id = GeneratorID.generujID();
        this.wiek = wiek;
        this.stanowisko = stanowisko;
        this.dataZatrudnienia = data;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getRola() {
        return stanowisko.name();
    }

    @Override
    public int compareTo(Pracownik o) {
        return this.nazwisko.compareTo(o.nazwisko);
    }

    @Override
    public String toString() {
        return id + " | " + imie + " " + nazwisko +
                " | " + stanowisko +
                " | wiek: " + wiek +
                " | zatrudniony: " + dataZatrudnienia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pracownik)) return false;
        Pracownik p = (Pracownik) o;
        return id == p.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
