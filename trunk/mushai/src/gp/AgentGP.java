package gp;

import mushai.Playboard;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.GeneticEvent;
import org.jgap.event.GeneticEventListener;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.impl.DefaultGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;

/**
 * For testing JGap. Intend to try out a simple GP problem with numeric
 * operators (+, -, *, /) and terminals that try to reach some number.
 * @author Erik Levin
 */
public class AgentGP extends GPProblem {

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

        CommandGene[][] nodes = {{}};
        return GPGenotype.randomInitialGenotype(conf, types, argTypes, nodes, 20, true);
    }

    public AgentGP(GPConfiguration conf) throws InvalidConfigurationException {
        super(conf);
    }

    public static void main(String[] args) throws InvalidConfigurationException {
        GPConfiguration conf = new GPConfiguration();
        Playboard board = new Playboard(1, 0);

        conf.setPopulationSize(POP_SIZE);
        conf.setFitnessEvaluator(new DefaultGPFitnessEvaluator());
        conf.setFitnessFunction(new TraverseFitnessFunction(board));

        conf.getEventManager().addEventListener(GeneticEvent.GPGENOTYPE_NEW_BEST_SOLUTION, new GeneticEventListener() {

            public void geneticEventFired(GeneticEvent a_firedEvent) {
                GPGenotype genotype = (GPGenotype) a_firedEvent.getSource();
                System.out.println("New best guy: " + genotype.getAllTimeBest().execute_int(0, null));
            }
        });

        GPGenotype genotype = new AgentGP(conf).create();
        genotype.setVerboseOutput(false);
        genotype.evolve(100);
//        genotype.calcFitness();
    }
}
