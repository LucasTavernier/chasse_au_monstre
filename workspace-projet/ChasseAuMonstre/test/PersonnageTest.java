import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonnageTest {

    Case case1 = new Case(3,3,TypeCase.EAU);
    Personnage personnage = new Personnage(case1,"persoTest");

    @Test
    public void getPosition() {
        Case caseTest = new Case(3, 3, TypeCase.EAU);

        assertEquals(caseTest.getX(), personnage.getPosition().getX());
        assertEquals(caseTest.getY(), personnage.getPosition().getY());
        assertEquals(caseTest.typeActuel, personnage.getPosition().typeActuel);

    }

    @Test
    public void deplacement() {
        //case 1 ( voir en haut ) est la case où le personnage se trouve initialement.

        Case caseTest = new Case(4, 3, TypeCase.FORET); // case où le personnage va se déplacer.
        int tour = 3;

        personnage.getPosition().update(TypeCase.MONSTRE);
        personnage.deplacement(caseTest, tour);
        assertEquals(case1.getTourVisite(), tour);
        assertEquals(case1.ancienType, TypeCase.MONSTRE); // principe de la méthode
        assertEquals(case1.typeActuel, TypeCase.EAU);     // back() de la classe Case

        assertTrue(case1.isVisitedByMonstre());

        assertEquals(caseTest.getX(), personnage.getPosition().getX());
        assertEquals(caseTest.getY(), personnage.getPosition().getY());
        assertEquals(caseTest.typeActuel, personnage.getPosition().typeActuel);
    }

    @Test
    public void getNom() {
        assertEquals("persoTest", personnage.getNom());
    }
}