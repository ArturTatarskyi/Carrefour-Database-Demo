import java.util.Random;

public class GeneratorID {
    public static int generujID() {
        return 10000 + new Random().nextInt(90000);
    }
}
