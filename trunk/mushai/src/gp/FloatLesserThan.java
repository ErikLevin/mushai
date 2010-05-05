/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.function.LesserThan;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 *
 * @author Erik Levin
 */
class FloatLesserThan extends CommandGene {

    public FloatLesserThan(GPConfiguration a_conf, Class a_returnType) throws InvalidConfigurationException {
        super(a_conf, 2, a_returnType);
    }

    @Override
    public float execute_float(ProgramChromosome c, int n, Object[] args) {
        if (c.execute_float(n, 0, args) < c.execute_float(n, 1, args)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "&1 < &2";
    }
}
