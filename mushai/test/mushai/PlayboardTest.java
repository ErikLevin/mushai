package mushai;

import java.awt.Color;
import java.awt.Graphics;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Erik Levin
 */
public class PlayboardTest {

    public PlayboardTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Settings.addPlayer(new Player("A", Color.yellow));
        Settings.addPlayer(new Player("B", Color.BLUE));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of initializeBoard method, of class Playboard.
     */
    @Test
    public void testNewBoardHas0Fitness() {
        System.out.println("initializeBoard");
        int noPlayer1Pieces = 3;
        int noPlayer2Pieces = 3;
        Playboard instance = new Playboard();
        instance.initializeBoard(noPlayer1Pieces, noPlayer2Pieces);
        assertEquals(0, instance.getFitness());
    }

    /**
     * Test of getFitness method, of class Playboard.
     */
    @Test
    public void testGetFitness() {
        System.out.println("getFitness");
        Playboard instance = new Playboard(3,3);
        int expResult = 0;
        int result = instance.getFitness();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
