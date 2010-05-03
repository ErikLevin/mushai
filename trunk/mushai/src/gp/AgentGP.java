package gp;

import java.awt.Color;
import mushai.Playboard;
import mushai.Player;
import mushai.Settings;
import mushai.Window;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.GeneticEvent;
import org.jgap.event.GeneticEventListener;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.And;
import org.jgap.gp.function.GreaterThan;
import org.jgap.gp.function.IfElse;
import org.jgap.gp.function.LesserThan;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Not;
import org.jgap.gp.function.Or;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.DefaultGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;

/**
 * Genetic programming algorithm to evolve a program that plays the game.
 * 
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
            CommandGene.FloatClass
        };

        Class[][] argTypes = {{}};

        CommandGene[][] nodes = {{
                // ----- Base functions ------
                new Add(conf, CommandGene.FloatClass),
                new Subtract(conf, CommandGene.FloatClass),
                new Multiply(conf, CommandGene.FloatClass),
                new IfElse(conf, CommandGene.FloatClass), // ???
                new GreaterThan(conf, CommandGene.FloatClass),
                new LesserThan(conf, CommandGene.FloatClass),
                new And(conf, CommandGene.BooleanClass), //make own logical operators, that work on numbers?
                new Or(conf), //Returns boolean
                new Not(conf), //Returns boolean
                // ------- Game specific functions -------
                new IsPieceAt(conf, CommandGene.BooleanClass),
                // ------- Base terminals ---------
                new Terminal(conf, CommandGene.FloatClass, 0, 5, false, 0, true)
            }
        };

        return GPGenotype.randomInitialGenotype(conf, types, argTypes, nodes,
                20, true);
    }

    public AgentGP(
            GPConfiguration conf) throws InvalidConfigurationException {
        super(conf);


    }

    public static void main(String[] args) throws InvalidConfigurationException {
        GPConfiguration conf = new GPConfiguration();
        Settings.addPlayer(new Player("0", Color.yellow));
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
        System.out.println("best guy: " + genotype.getFittestProgram().toStringNorm(0));
//        genotype.calcFitness();

    }
}
