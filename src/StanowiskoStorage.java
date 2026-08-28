import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StanowiskoStorage {

    private static final String FILE_NAME =
            "stanowiska.txt";

    public static void save() {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(FILE_NAME)
                     )) {

            for (Stanowisko stanowisko :
                    Stanowisko.values()) {

                writer.println(
                        stanowisko.getNazwa()
                                + ";"
                                + stanowisko.isKierownicze()
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Blad podczas zapisywania stanowisk."
            );

            e.printStackTrace();
        }
    }

    public static void load() {

        File file =
                new File(FILE_NAME);

        if (!file.exists()) {

            save();
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file)
                     )) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                String[] data =
                        line.split(";");

                String nazwa =
                        data[0].trim();

                boolean kierownicze =
                        false;

                if (data.length >= 2) {

                    kierownicze =
                            Boolean.parseBoolean(
                                    data[1].trim()
                            );
                }

                Stanowisko.dodajStanowisko(
                        nazwa,
                        kierownicze
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Blad podczas wczytywania stanowisk."
            );

            e.printStackTrace();
        }

        save();
    }
}