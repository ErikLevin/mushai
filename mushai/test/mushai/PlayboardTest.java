package mushai;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
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
        Settings.addPlayer(new Player("A", Color.YELLOW));
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
     * Test of resetBoard method, of class Playboard.
     */
    @Test
    public void testNewBoardHasBaseFitness() throws Exception {
        System.out.println("initializeBoard");
        Playboard instance = new Playboard(3, 3);
        assertEquals(Settings.getPlayboardSize() * Settings.getPlayboardSize(), instance.getFitness());
    }

    /**
     * Test of getFitness method, of class Playboard.
     */
    @Test
    public void testGetFitness() throws Exception {
        System.out.println("getFitness");
        Playboard instance = new Playboard(3, 3);
        Controller con = new Controller(instance);
        con.move(new Point(0, 0), new Point(0, 1));
        int expResult = Settings.getPlayboardSize() * Settings.getPlayboardSize() + 1;
        int result = instance.getFitness();
        assertEquals(expResult, result);
    }
}
