/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.toy;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.terminal.Terminal;

/**
 * Terminals. Can be integers from 0 to 100.
 */
public class NumTerminal extends Terminal{

    /**
     * Creates a new, random NumTerminal.
     * @param conf
     * @throws InvalidConfigurationException
     */
    public NumTerminal(GPConfiguration conf) throws InvalidConfigurationException {
        super(conf, CommandGene.IntegerClass, 0, 100, false, 0, true);
    }
}
