package gp.toy;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;

/**
 * For testing JGap. Intend to try out a simple GP problem with numeric
 * operators (+, -, *, /) and terminals that try to reach some number.
 * @author Erik Levin
 */
public class SimpleGP extends GPProblem {

    /**
     * The number that the evolved program should give as result.
     */
    private final int GOAL = 571;

    /**
     * Creates the initial population.
     *
     * @return - initial, random population
     * @throws InvalidConfigurationException
     */
    @Override
    public GPGenotype create() throws InvalidConfigurationException {


        return null;
    }

    public SimpleGP(GPConfiguration conf) throws InvalidConfigurationException {
        super(conf);
    }
}
