package gp.toy;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.function.Add;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.impl.TournamentSelector;
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
    private static final int GOAL = 372;
    private static final int POP_SIZE = 100;

    /**
     * Creates the initial population.
     *
     * @return - initial, random population
     * @throws InvalidConfigurationException
     */
    @Override
    public GPGenotype create() throws InvalidConfigurationException {
        GPConfiguration conf = getGPConfiguration();

        Class[] types = {
            CommandGene.IntegerClass
        };

        Class[][] argTypes = {{}};

        CommandGene[][] nodes = {{
                new Add(conf, CommandGene.IntegerClass),
                new Terminal(conf, CommandGene.IntegerClass, 1, 50, true, 0, true)
            }};
        return GPGenotype.randomInitialGenotype(conf, types, argTypes, nodes, 20, true);
    }

    public SimpleGP(GPConfiguration conf) throws InvalidConfigurationException {
        super(conf);
    }

    public static void main(String[] args) throws InvalidConfigurationException {
        GPConfiguration conf = new GPConfiguration();
        conf.setPopulationSize(POP_SIZE);
        conf.setFitnessEvaluator(new DeltaGPFitnessEvaluator());
        conf.setSelectionMethod(new TournamentSelector(4));
        conf.setMaxInitDepth(6);
        conf.setStrictProgramCreation(false);
        conf.setProgramCreationMaxTries(3);
        conf.setMaxCrossoverDepth(5);
        conf.setUseProgramCache(true);
        conf.setFitnessFunction(new GPFitnessFunction() {

            @Override
            protected double evaluate(IGPProgram individual) {
                int fitness = Math.abs(GOAL - individual.execute_int(0, new Object[0]));
//                System.out.println("Fitness: " + fitness);
                return fitness;
            }
        });

        GPGenotype genotype = new SimpleGP(conf).create();
        genotype.setVerboseOutput(true);
//        genotype.outputSolution(genotype.getAllTimeBest());
        genotype.evolve(100);
//        genotype.calcFitness();
    }
}
