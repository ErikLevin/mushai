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
class IsPieceAt extends CommandGene {

    public IsPieceAt(GPConfiguration conf, Class BooleanClass) throws InvalidConfigurationException {
        super(conf, 1, BooleanClass, 1);
    }

    @Override
    public float execute_float(ProgramChromosome c, int n, Object[] args) {
        return super.execute_float(c, n, args);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
