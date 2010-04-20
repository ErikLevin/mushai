/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.toy;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.impl.GPConfiguration;
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
public class NumTerminalTest {

    private static GPConfiguration conf;
    private NumTerminal num;

    @BeforeClass
    public static void setUpClass() throws Exception {
        conf = new GPConfiguration();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        try {
            num = new NumTerminal(conf);
        } catch (InvalidConfigurationException ex) {
            System.err.println("Invalid configuration: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testNewNumIsValid() {
        isValid(num);
    }

    /**
     * Checks that a NumTerminal is an integer in [0, 100]
     * @param num
     */
    public void isValid(NumTerminal num) {
        assertTrue(num.isIntegerType());
        int value = num.execute_int(null, 0, null);
        System.out.println("Num value: " + value);
        assertTrue(value >= 0 && value <= 100);
    }

    @Test
    public void testStillValidAfterMutation() throws InvalidConfigurationException {
        num.applyMutation(0, 1.0);
        isValid(num);
    }
}
