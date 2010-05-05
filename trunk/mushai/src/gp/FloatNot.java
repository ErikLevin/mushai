/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 *
 * @author Erik Levin
 */
class FloatNot extends CommandGene {

    public FloatNot(GPConfiguration conf, Class FloatClass) throws InvalidConfigurationException {
        super(conf, 1, CommandGene.FloatClass);
    }

    @Override
    public float execute_float(ProgramChromosome c, int n, Object[] args) {
        if (c.execute_float(n, 0, args) == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "¬&1";
    }
}
