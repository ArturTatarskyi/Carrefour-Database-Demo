import java.util.ArrayList;
import java.util.List;

public class Menu {

    private List<Pracownik> pracownicy = new ArrayList<>();
    private List<Towar> towary = new ArrayList<>();
    private List<Dostawca> dostawcy = new ArrayList<>();
    private List<Wynagrodzenie> wynagrodzenia = new ArrayList<>();

    public List<Pracownik> getPracownicy() {
        return pracownicy;
    }

    public List<Towar> getTowary() {
        return towary;
    }

    public List<Dostawca> getDostawcy() {
        return dostawcy;
    }

    public List<Wynagrodzenie> getWynagrodzenia() {
        return wynagrodzenia;
    }

    public Menu() {

        // ===== TYPY TOWAROW =====

        TypTowaruStorage.load();

        // ===== STANOWISKA =====

        StanowiskoStorage.load();

        // ===== PRACOWNICY =====

        pracownicy.addAll(
                PracownikStorage.load()
        );

        if (pracownicy.isEmpty()) {

            Pracownik p1 = new Pracownik(
                    "Jan",
                    "Kowalski",
                    30,
                    Stanowisko.PRACOWNIK,
                    "01.01.2020"
            );

            Pracownik p2 = new Pracownik(
                    "Adam",
                    "Nowak",
                    25,
                    Stanowisko.KASJER,
                    "02.02.2021"
            );

            Pracownik p3 = new Pracownik(
                    "Artur",
                    "Lis",
                    28,
                    Stanowisko.MAGAZYNIER,
                    "10.03.2022"
            );

            Pracownik p4 = new Kierownik(
                    "Anna",
                    "Mazur",
                    40,
                    "01.05.2018"
            );

            Pracownik p5 = new Kierownik(
                    "Piotr",
                    "Zielinski",
                    45,
                    "01.03.2016"
            );

            pracownicy.addAll(
                    List.of(p1, p2, p3, p4, p5)
            );

            PracownikStorage.save(pracownicy);
        }

        // ===== WYNAGRODZENIA =====

        wynagrodzenia.addAll(
                WynagrodzenieStorage.load(pracownicy)
        );

        if (wynagrodzenia.isEmpty() && pracownicy.size() >= 5) {

            Pracownik p1 = pracownicy.get(0);
            Pracownik p2 = pracownicy.get(1);
            Pracownik p4 = pracownicy.get(3);
            Pracownik p5 = pracownicy.get(4);

            wynagrodzenia.add(
                    new Wynagrodzenie(p1, 40, "01.2024")
            );

            wynagrodzenia.add(
                    new Wynagrodzenie(p2, 50, "01.2024")
            );

            wynagrodzenia.add(
                    new Wynagrodzenie(p4, 30, "01.2024")
            );

            wynagrodzenia.add(
                    new Wynagrodzenie(p5, 45, "01.2024")
            );

            WynagrodzenieStorage.save(wynagrodzenia);
        }

        // ===== TOWARY =====

        towary = TowarStorage.load();

        if (towary.isEmpty()) {

            towary.add(
                    new Towar(
                            "Chleb",
                            TypTowaru.JEDZENIE,
                            4.5,
                            100
                    )
            );

            towary.add(
                    new Towar(
                            "Koszulka",
                            TypTowaru.ODZIEZ,
                            49.9,
                            50
                    )
            );

            towary.add(
                    new Towar(
                            "Sofa",
                            TypTowaru.MEBLE,
                            1999,
                            2
                    )
            );

            towary.add(
                    new Towar(
                            "Laptop",
                            TypTowaru.ELEKTRONIKA,
                            3500,
                            5
                    )
            );

            towary.add(
                    new Towar(
                            "Jablka",
                            TypTowaru.JEDZENIE,
                            3.99,
                            200
                    )
            );

            TowarStorage.save(towary);
        }

        // ===== DOSTAWCY =====

        dostawcy.addAll(
                DostawcaStorage.load()
        );

        if (dostawcy.isEmpty()) {

            dostawcy.add(
                    new Dostawca(
                            "FoodPol",
                            TypTowaru.JEDZENIE,
                            "01.01.2019"
                    )
            );

            dostawcy.add(
                    new Dostawca(
                            "TextilMax",
                            TypTowaru.ODZIEZ,
                            "05.05.2018"
                    )
            );

            dostawcy.add(
                    new Dostawca(
                            "MebleLux",
                            TypTowaru.MEBLE,
                            "10.10.2020"
                    )
            );

            dostawcy.add(
                    new Dostawca(
                            "TechWorld",
                            TypTowaru.ELEKTRONIKA,
                            "01.03.2022"
                    )
            );

            dostawcy.add(
                    new Dostawca(
                            "FreshFarm",
                            TypTowaru.JEDZENIE,
                            "15.06.2021"
                    )
            );

            DostawcaStorage.save(dostawcy);
        }
    }
}