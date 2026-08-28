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

    public Dostawca(int id, String nazwa, TypTowaru typTowaru, String dataUmowy) {
        this.id = id;
        this.nazwa = nazwa;
        this.typTowaru = typTowaru;
        this.dataUmowy = dataUmowy;
    }

    public int getId() {
        return id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public TypTowaru getTypTowaru() {
        return typTowaru;
    }

    public String getDataUmowy() {
        return dataUmowy;
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