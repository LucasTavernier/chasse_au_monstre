import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Coord2DTest {

    Coord2D coordonnees = new Coord2D(10,5);

    @Test
    public void getX() {
        assertEquals(10,coordonnees.getX());
    }

    @Test
    public void getY() {
        assertEquals(5,coordonnees.getY());
    }
}