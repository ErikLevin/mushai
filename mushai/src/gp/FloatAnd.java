package gp;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.function.And;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 * An and operator that uses floats:
 * 
 * AND(A,B) -> If both A != 0 and B != 0, return 1 otherwise return 0
 *
 * @author MushAI
 */
public class FloatAnd extends CommandGene {

    public FloatAnd(GPConfiguration a_conf, Class a_returnType) throws InvalidConfigurationException {
        super(a_conf, 2, a_returnType);
    }

    @Override
    public float execute_float(ProgramChromosome c, int n, Object[] args) {
        if (c.execute_float(n, 0, args) != 0 && c.execute_float(n, 1, args) != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "&1 && &2";
    }
}
