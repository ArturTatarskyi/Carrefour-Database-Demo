import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DostawcaStorage {

    private static final String FILE_NAME = "dostawcy.txt";

    public static void save(List<Dostawca> dostawcy) {

        try (PrintWriter writer = new PrintWriter(
                new FileWriter(FILE_NAME))) {

            for (Dostawca dostawca : dostawcy) {
                writer.println(
                        dostawca.getId() + "|" +
                        dostawca.getNazwa() + "|" +
                        dostawca.getTypTowaru() + "|" +
                        dostawca.getDataUmowy()
                );
            }

        } catch (IOException e) {
            System.out.println(
                    "Blad zapisu dostawcow: " + e.getMessage()
            );
        }
    }

    public static List<Dostawca> load() {

        List<Dostawca> dostawcy = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return dostawcy;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|");

                if (parts.length == 4) {

                    String nazwa = parts[1];
                    TypTowaru typ = TypTowaru.valueOf(parts[2]);
                    String data = parts[3];

                    dostawcy.add(
                            new Dostawca(nazwa, typ, data)
                    );
                }
            }

        } catch (IOException | IllegalArgumentException e) {

            System.out.println(
                    "Blad odczytu dostawcow: " + e.getMessage()
            );
        }

        return dostawcy;
    }
}