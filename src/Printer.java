import java.util.List;

public class Printer {
    public static <T> void drukuj(List<T> lista) {
        for (T t : lista) {
            System.out.println(t);
        }
        System.out.println("\nAby kontynuowac - nacisnij ENTER");
        try { System.in.read(); } catch (Exception ignored) {}
    }
}
