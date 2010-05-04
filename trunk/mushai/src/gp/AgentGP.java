package gp;

import java.awt.Color;
import mushai.Controller;
import mushai.Playboard;
import mushai.Player;
import mushai.Settings;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.GeneticEvent;
import org.jgap.event.GeneticEventListener;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.GreaterThan;
import org.jgap.gp.function.LesserThan;
import org.jgap.gp.function.Multiply;
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

    private static final int POP_SIZE = 100;
    private Playboard board;
    private Controller controller;

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
            CommandGene.VoidClass
        };

        Class[][] argTypes = {{}};

        CommandGene[][] nodes = {{
                // ----- Base functions ------
                new Add(conf, CommandGene.FloatClass),
                new Subtract(conf, CommandGene.FloatClass),
                new Multiply(conf, CommandGene.FloatClass),
                //new IfElse(conf, CommandGene.FloatClass), // Can't return float... Make our own?
                new GreaterThan(conf, CommandGene.FloatClass),
                new LesserThan(conf, CommandGene.FloatClass),
                //new And(conf, CommandGene.FloatClass), //make own logical operators, that work on numbers?
                //new Or(conf), //Returns boolean
                //new Not(conf), //Returns boolean
                // ------- Game specific functions -------
                new MakeMove(conf, new Controller(board)), //Is VoidClass
                // ------- Base terminals ---------
                new Terminal(conf, CommandGene.FloatClass, 0, 5, false, 0, true), // ------- Game specific terminals --------
            // ------- Sensors --------
            //new IsPieceAt(conf, CommandGene.FloatClass),
            }
        };

        return GPGenotype.randomInitialGenotype(conf, types, argTypes, nodes, 100, true);
    }

    public AgentGP(GPConfiguration conf, Playboard board) throws InvalidConfigurationException {
        super(conf);
        this.board = board;
        //controller = new Controller(board);
    }

    public static void main(String[] args) throws InvalidConfigurationException {
        GPConfiguration conf = new GPConfiguration();

        Settings.addPlayer(new Player("0", Color.YELLOW));
        Settings.getPlayer(0).setMyTurn(true);

        conf.setPopulationSize(POP_SIZE);
        conf.setFitnessEvaluator(new DefaultGPFitnessEvaluator());
        conf.setFitnessFunction(new TraverseFitnessFunction(1, 0));

        conf.getEventManager().addEventListener(GeneticEvent.GPGENOTYPE_NEW_BEST_SOLUTION, new GeneticEventListener() {

            public void geneticEventFired(GeneticEvent a_firedEvent) {
                GPGenotype genotype = (GPGenotype) a_firedEvent.getSource();
//                System.out.println("New best guy: " + genotype.getAllTimeBest().execute_int(0, null));
            }
        });

        AgentGP gpProblem = new AgentGP(conf, new Playboard(1, 0));
        GPGenotype genotype = gpProblem.create();
        genotype.setVerboseOutput(true);
        genotype.evolve(50);
        System.out.println("best guy: " + genotype.getAllTimeBest().toStringNorm(0) + " with fitness "
                + genotype.getAllTimeBest().getFitnessValue());
//        genotype.calcFitness();
    }
}
