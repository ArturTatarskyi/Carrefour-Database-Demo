public class Dostawca implements Comparable<Dostawca> {

    private int id;
    private String nazwa;
    private TypTowaru typTowaru;
    private String dataUmowy;

    public Dostawca(String nazwa, TypTowaru typTowaru, String dataUmowy) {
        this.id = GeneratorID.generujID();
        this.nazwa = nazwa;
        this.typTowaru = typTowaru;
        this.dataUmowy = dataUmowy;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Dostawca o) {
        return this.nazwa.compareTo(o.nazwa);
    }

    @Override
    public String toString() {
        return id + " | " + nazwa +
                " | dostarcza: " + typTowaru +
                " | umowa od: " + dataUmowy;
    }
}
