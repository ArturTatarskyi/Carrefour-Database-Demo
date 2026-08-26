import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WynagrodzenieStorage {

    private static final String FILE_NAME = "wynagrodzenia.txt";

    public static void save(List<Wynagrodzenie> wynagrodzenia) {

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Wynagrodzenie wynagrodzenie : wynagrodzenia) {

                writer.println(
                        wynagrodzenie.getPracownikId() + ";" +
                        wynagrodzenie.getGodziny() + ";" +
                        wynagrodzenie.getOkres()
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Blad podczas zapisywania wynagrodzen."
            );

            e.printStackTrace();
        }
    }

    public static List<Wynagrodzenie> load(
            List<Pracownik> pracownicy) {

        List<Wynagrodzenie> wynagrodzenia =
                new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return wynagrodzenia;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(";");

                if (data.length != 3) {
                    continue;
                }

                int pracownikId =
                        Integer.parseInt(data[0]);

                int godziny =
                        Integer.parseInt(data[1]);

                String okres = data[2];

                Pracownik pracownik = null;

                for (Pracownik p : pracownicy) {

                    if (p.getId() == pracownikId) {
                        pracownik = p;
                        break;
                    }
                }

                if (pracownik == null) {
                    continue;
                }

                wynagrodzenia.add(
                        new Wynagrodzenie(
                                pracownik,
                                godziny,
                                okres
                        )
                );
            }

        } catch (
                IOException |
                NumberFormatException e
        ) {

            System.out.println(
                    "Blad podczas wczytywania wynagrodzen."
            );

            e.printStackTrace();
        }

        return wynagrodzenia;
    }
}