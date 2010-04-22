package gp.toy;

import org.jgap.Chromosome;
import org.jgap.ChromosomeForTesting;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.GeneticEvent;
import org.jgap.event.GeneticEventListener;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Subtract;
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
    private static final int GOAL = 111;
    private static final int POP_SIZE = 3;

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
                //Arithmetic operators as functions
                new Add(conf, CommandGene.IntegerClass),
                new Subtract(conf, CommandGene.IntegerClass),
                //new Multiply(conf, CommandGene.IntegerClass),
                //Integers in [1,50] are the terminals
                new Terminal(conf, CommandGene.IntegerClass, -50, 50, true, 0, true)
            }};
        return GPGenotype.randomInitialGenotype(conf, types, argTypes, nodes, 20, true);
    }

    public SimpleGP(GPConfiguration conf) throws InvalidConfigurationException {
        super(conf);
    }

    public static void main(String[] args) throws InvalidConfigurationException {
        GPConfiguration conf = new GPConfiguration();

        conf.setPopulationSize(POP_SIZE);
        conf.setFitnessEvaluator(new DeltaGPFitnessEvaluator()); //Delta = lowest fitness wins
//        conf.setSelectionMethod(new TournamentSelector(4));
       // conf.setMaxInitDepth(10);
//        conf.setStrictProgramCreation(true);
//        conf.setProgramCreationMaxTries(3);
//        conf.setMaxCrossoverDepth(10);
//        conf.setUseProgramCache(true);
        conf.setMutationProb(0.01f);
        conf.setFitnessFunction(new GPFitnessFunction() {

            @Override
            protected double evaluate(IGPProgram individual) {
                int fitness = Math.abs(GOAL - individual.execute_int(0, new Object[0]));
//                System.out.println("Fitness: " + fitness);
                fitness += individual.getChromosome(0).getDepth(0);

                return fitness;
            }
        });

        conf.getEventManager().addEventListener(GeneticEvent.GPGENOTYPE_NEW_BEST_SOLUTION, new GeneticEventListener() {

            public void geneticEventFired(GeneticEvent a_firedEvent) {
                GPGenotype genotype = (GPGenotype) a_firedEvent.getSource();
                System.out.println("New best guy: " + genotype.getAllTimeBest().execute_int(0, null));
            }
        });

        conf.setFunctionProb(0.5);
        
        System.out.println(conf.getCrossMethod());

        GPGenotype genotype = new SimpleGP(conf).create();
        genotype.setVerboseOutput(true);        
        genotype.evolve(100);
//        genotype.calcFitness();
    }
}
