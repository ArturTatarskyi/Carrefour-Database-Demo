import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeneratorID {

    private static final Random RANDOM = new Random();
    private static final Set<Integer> USED_IDS = new HashSet<>();

    public static int generujID() {

        int id;

        do {
            id = 10000 + RANDOM.nextInt(90000);
        } while (USED_IDS.contains(id));

        USED_IDS.add(id);

        return id;
    }
}