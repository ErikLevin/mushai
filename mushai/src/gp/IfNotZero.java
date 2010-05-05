package gp;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.function.IfElse;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 *
 * @author MushAI
 */
public class IfNotZero extends IfElse {

    public IfNotZero(GPConfiguration a_conf, Class a_type) throws InvalidConfigurationException {
        super(a_conf, a_type);
    }

    @Override
    public float execute_float(ProgramChromosome c, int n, Object[] args) {
        check(c);
        int child;

        if (c.execute_float(n, 0, args) != 0) { //If condition child is nonzero
            child = 1;                          //Return value of first branch
        } else {
            child = 2;                          //Else return second branch
        }
        return c.execute_float(n, child, args);
    }
}
