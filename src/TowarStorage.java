import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TowarStorage {

    private static final String FILE_NAME = "towary.txt";

    public static void save(List<Towar> towary) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Towar towar : towary) {
                writer.println(
                        towar.getId() + ";" +
                        towar.getNazwa() + ";" +
                        towar.getTyp() + ";" +
                        towar.getCena() + ";" +
                        towar.getIlosc()
                );
            }

        } catch (IOException e) {
            System.out.println("Blad podczas zapisywania towarow.");
            e.printStackTrace();
        }
    }

    public static List<Towar> load() {

        List<Towar> towary = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return towary;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(";");

                if (data.length != 5) {
                    continue;
                }

                int id = Integer.parseInt(data[0]);
                String nazwa = data[1];
                TypTowaru typ = TypTowaru.valueOf(data[2]);
                double cena = Double.parseDouble(data[3]);
                int ilosc = Integer.parseInt(data[4]);

                towary.add(
                        new Towar(id, nazwa, typ, cena, ilosc)
                );
            }

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Blad podczas wczytywania towarow.");
            e.printStackTrace();
        }

        return towary;
    }
}