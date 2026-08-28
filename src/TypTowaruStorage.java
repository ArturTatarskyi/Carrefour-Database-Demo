import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TypTowaruStorage {

    private static final String FILE_NAME =
            "typy_towarow.txt";

    public static void save() {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(FILE_NAME)
                     )) {

            for (TypTowaru typ : TypTowaru.values()) {

                writer.println(
                        typ.getNazwa()
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Blad podczas zapisywania typow towarow."
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

                if (!line.isBlank()) {

                    TypTowaru.dodajTyp(
                            line
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Blad podczas wczytywania typow towarow."
            );

            e.printStackTrace();
        }
    }
}