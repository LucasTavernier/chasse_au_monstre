import org.junit.Test;

import static org.junit.Assert.*;

public class ChasseurTest {

    Plateau plateau = new Plateau(10, 0);
    Case caseMonstre = plateau.plateau[3][3];
    Monstre monstre = new Monstre(caseMonstre, "monstreTest");
    Chasseur chasseur = new Chasseur(new Case(2,2,TypeCase.CHASSEUR), "chasseurTest");

    @Test
    public void tir() {
        caseMonstre.update(TypeCase.MONSTRE);

        Case casePremierTir = plateau.plateau[2][3];
        casePremierTir.setShooted();

        assertTrue(casePremierTir.hadBeenShooted());
    }
}