import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PracownikStorage {

    private static final String FILE_NAME = "pracownicy.txt";

    public static void save(List<Pracownik> pracownicy) {

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Pracownik pracownik : pracownicy) {

                writer.println(
                        pracownik.getId() + ";" +
                        pracownik.getImie() + ";" +
                        pracownik.getNazwisko() + ";" +
                        pracownik.getWiek() + ";" +
                        pracownik.getStanowisko() + ";" +
                        pracownik.getDataZatrudnienia()
                );
            }

        } catch (IOException e) {
            System.out.println(
                    "Blad podczas zapisywania pracownikow."
            );
            e.printStackTrace();
        }
    }

    public static List<Pracownik> load() {

        List<Pracownik> pracownicy = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return pracownicy;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(";");

                if (data.length != 6) {
                    continue;
                }

                int id = Integer.parseInt(data[0]);
                String imie = data[1];
                String nazwisko = data[2];
                int wiek = Integer.parseInt(data[3]);
                Stanowisko stanowisko =
                        Stanowisko.valueOf(data[4]);
                String dataZatrudnienia = data[5];

                Pracownik pracownik;

if (stanowisko.isKierownicze()) {

    pracownik = new Kierownik(
            id,
            imie,
            nazwisko,
            wiek,
            stanowisko,
            dataZatrudnienia
    );

} else {

    pracownik = new Pracownik(
            id,
            imie,
            nazwisko,
            wiek,
            stanowisko,
            dataZatrudnienia
    );
}

pracownicy.add(pracownik);
            }

        } catch (IOException | IllegalArgumentException e) {

            System.out.println(
                    "Blad podczas wczytywania pracownikow."
            );

            e.printStackTrace();
        }

        return pracownicy;
    }
}