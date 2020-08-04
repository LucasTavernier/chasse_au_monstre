import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class TypeCaseTest {

    Random random = new Random();
    TypeCase[] listeType = TypeCase.values();
    TypeCase type1 = listeType[random.nextInt(listeType.length)];

    @Test
    public void toStringID() {
        assertEquals(type1.name().charAt(0), type1.toStringID());
    }
}