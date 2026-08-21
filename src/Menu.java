import java.util.*;

public class Menu {

    private Scanner sc = new Scanner(System.in);

    private List<Pracownik> pracownicy = new ArrayList<>();
    private List<Towar> towary = new ArrayList<>();
    public List<Towar> getTowary() {
    return towary;
    }
    private List<Dostawca> dostawcy = new ArrayList<>();
    private List<Wynagrodzenie> wynagrodzenia = new ArrayList<>();

    public Menu() {

        // ===== PRACOWNICY (5) =====
        Pracownik p1 = new Pracownik("Jan", "Kowalski", 30, Stanowisko.PRACOWNIK, "01.01.2020");
        Pracownik p2 = new Pracownik("Adam", "Nowak", 25, Stanowisko.KASJER, "02.02.2021");
        Pracownik p3 = new Pracownik("Artur", "Lis", 28, Stanowisko.MAGAZYNIER, "10.03.2022");
        Pracownik p4 = new Kierownik("Anna", "Mazur", 40, "01.05.2018");
        Pracownik p5 = new Kierownik("Piotr", "Zielinski", 45, "01.03.2016");

        pracownicy.addAll(List.of(p1, p2, p3, p4, p5));

        // ===== WYNAGRODZENIA (4) =====
        wynagrodzenia.add(new Wynagrodzenie(p1, 40, "01.2024"));
        wynagrodzenia.add(new Wynagrodzenie(p2, 50 ,"01.2024"));
        wynagrodzenia.add(new Wynagrodzenie(p4, 30, "01.2024"));
        wynagrodzenia.add(new Wynagrodzenie(p5, 45, "01.2024"));

        // ===== TOWARY =====
towary = TowarStorage.load();

if (towary.isEmpty()) {
    towary.add(new Towar("Chleb", TypTowaru.JEDZENIE, 4.5, 100));
    towary.add(new Towar("Koszulka", TypTowaru.ODZIEZ, 49.9, 50));
    towary.add(new Towar("Sofa", TypTowaru.MEBLE, 1999, 2));
    towary.add(new Towar("Laptop", TypTowaru.ELEKTRONIKA, 3500, 5));
    towary.add(new Towar("Jablka", TypTowaru.JEDZENIE, 3.99, 200));

    TowarStorage.save(towary);
}
        // ===== DOSTAWCY (5) =====
        dostawcy.add(new Dostawca("FoodPol", TypTowaru.JEDZENIE, "01.01.2019"));
        dostawcy.add(new Dostawca("TextilMax", TypTowaru.ODZIEZ, "05.05.2018"));
        dostawcy.add(new Dostawca("MebleLux", TypTowaru.MEBLE, "10.10.2020"));
        dostawcy.add(new Dostawca("TechWorld", TypTowaru.ELEKTRONIKA, "01.03.2022"));
        dostawcy.add(new Dostawca("FreshFarm", TypTowaru.JEDZENIE, "15.06.2021"));
    }

    public void start() {
        while (true) {
            System.out.println("""
            \n--- SKLEP CARREFOUR ---
            1. Pracownicy
            2. Towary
            3. Dostawcy
            4. Wynagrodzenia
            5. Wyjscie
            """);

            int wybor = sc.nextInt();
            sc.nextLine();

            switch (wybor) {
                case 1 -> menuPracownicy();
                case 2 -> menuTowary();
                case 3 -> menuDostawcy();
                case 4 -> menuWynagrodzenia();
                case 5 -> System.exit(0);
            }
        }
    }

    // ================= PRACOWNICY =================
    private void menuPracownicy() {
        System.out.println("1.Wyswietl  2.Dodaj  3.Usun  4.Sortuj nazwisko");
        int w = sc.nextInt(); sc.nextLine();

        if (w == 1) Printer.drukuj(pracownicy);

        if (w == 2) {
            System.out.println("Wpisz imie:");
            String imie = sc.nextLine();
            System.out.println("Wpisz nazwisko:");
            String nazw = sc.nextLine();
            System.out.println("Wpisz wiek:");
            int wiek = sc.nextInt(); sc.nextLine();
            System.out.println("Wpisz stanowisko (PRACOWNIK, KASJER, MAGAZYNIER, KIEROWNIK):");
            Stanowisko st = Stanowisko.valueOf(sc.nextLine());
            System.out.println("Wpisz date zatrudnienia (dd.mm.rrrr):");
            String data = sc.nextLine();

            Pracownik p = (st == Stanowisko.KIEROWNIK)
                    ? new Kierownik(imie, nazw, wiek, data)
                    : new Pracownik(imie, nazw, wiek, st, data);

            pracownicy.add(p);
        }

        if (w == 3) {
            System.out.println("Wpisz ID pracownika:");
            int id = sc.nextInt();

            boolean usunieto = pracownicy.removeIf(p -> p.getId() == id);

            if (usunieto) {
                wynagrodzenia.removeIf(wyn -> wyn.getPracownikId() == id);
                System.out.println("Pracownik usunięty");
            } else {
                System.out.println("Danego pracownika nie istnieje");
            }
        }

        if (w == 4) {
            Collections.sort(pracownicy);
            Printer.drukuj(pracownicy);
        }
    }

    // ================= TOWARY =================
    private void menuTowary() {
        System.out.println("1.Wyswietl  2.Dodaj  3.Usun  4.Sortuj nazwa");
        int w = sc.nextInt(); sc.nextLine();

        if (w == 1) Printer.drukuj(towary);

        if (w == 2) {
            System.out.println("Wpisz nazwe towaru:");
            String n = sc.nextLine();
            System.out.println("Wpisz typ (JEDZENIE, ODZIEZ, MEBLE, ELEKTRONIKA):");
            TypTowaru t = TypTowaru.valueOf(sc.nextLine());
            System.out.println("Wpisz cene (z przecinkiem):");
            double c = sc.nextDouble();
            System.out.println("Wpisz ilosc:");
            int i = sc.nextInt();
            towary.add(new Towar(n, t, c, i));
        }

        if (w == 3) {
            System.out.println("Wpisz ID towaru:");
            int id = sc.nextInt();

            boolean usunieto = towary.removeIf(t -> t.getId() == id);

            if (usunieto) {
                System.out.println("Towar usunięty");
            } else {
                System.out.println("Danego towaru nie istnieje");
            }
        }

        if (w == 4) {
            Collections.sort(towary);
            Printer.drukuj(towary);
        }
    }

    // ================= DOSTAWCY =================
    private void menuDostawcy() {
        System.out.println("1.Wyswietl  2.Dodaj  3.Usun  4.Sortuj nazwa");
        int w = sc.nextInt(); sc.nextLine();

        if (w == 1) Printer.drukuj(dostawcy);

        if (w == 2) {
            System.out.println("Wpisz nazwe dostawcy:");
            String n = sc.nextLine();
            System.out.println("Wpisz typ towaru (JEDZENIE, ODZIEZ, MEBLE, ELEKTRONIKA):");
            TypTowaru t = TypTowaru.valueOf(sc.nextLine());
            System.out.println("Wpisz date podpisania umowy:");
            String d = sc.nextLine();
            dostawcy.add(new Dostawca(n, t, d));
        }

        if (w == 3) {
            System.out.println("Wpisz ID dostawcy:");
            int id = sc.nextInt();

            boolean usunieto = dostawcy.removeIf(d -> d.getId() == id);

            if (usunieto) {
                System.out.println("Dostawca usunięty");
            } else {
                System.out.println("Danego dostawcy nie istnieje");
            }
        }

        if (w == 4) {
            Collections.sort(dostawcy);
            Printer.drukuj(dostawcy);
        }
    }

    // ================= WYNAGRODZENIA =================
    private void menuWynagrodzenia() {

        System.out.println("1.Wyswietl  2.Dodaj  3.Sortuj nazwisko");
        int w = sc.nextInt();
        sc.nextLine();

        if (w == 1) {
            Printer.drukuj(wynagrodzenia);
        }

        if (w == 2) {

            System.out.println("Wpisz ID pracownika:");
            int id = sc.nextInt();
            sc.nextLine();

            Pracownik pracownik = null;
            for (Pracownik p : pracownicy) {
                if (p.getId() == id) {
                    pracownik = p;
                    break;
                }
            }

            if (pracownik == null) {
                System.out.println("Nie ma takiego pracownika");
                return;
            }

            System.out.println("Wpisz okres (np. 01.2024):");
            String okres = sc.nextLine();

            for (Wynagrodzenie wyna : wynagrodzenia) {
                if (wyna.getPracownikId() == id && wyna.getOkres().equals(okres)) {
                    System.out.println("To wynagrodzenie juz istnieje!");
                    return;
                }
            }

            System.out.println("Wpisz liczbe przepracowanych godzin:");
            int godziny = sc.nextInt();

            wynagrodzenia.add(new Wynagrodzenie(pracownik, godziny, okres));
            System.out.println("Dodano wynagrodzenie");
        }

        if (w == 3) {
            Collections.sort(wynagrodzenia);
            Printer.drukuj(wynagrodzenia);
        }
    }
}
