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
import org.jgap.gp.function.IfElse;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.DefaultGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.impl.CauchyRandomGenerator;
import org.jgap.impl.GaussianRandomGenerator;
import org.jgap.impl.StockRandomGenerator;

/**
 * Genetic programming algorithm to evolve a program that plays the game.
 * 
 */
public class AgentGP extends GPProblem {

    private static final int POP_SIZE = 20;
    private Controller controller;
    private static final int player1Pieces = 1;
    private static final int player2Pieces = 1;

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

        Class[][] argTypes = {{Playboard.class}};

        CommandGene[][] nodes = {{
                // ----- Basic functions ------
                new Add(conf, CommandGene.FloatClass),
                new Subtract(conf, CommandGene.FloatClass),
                new Multiply(conf, CommandGene.FloatClass),
                new IfNotZero(conf, CommandGene.FloatClass),
                //new IfElse(conf, CommandGene.FloatClass),
                new GreaterThan(conf, CommandGene.FloatClass),
                new FloatLesserThan(conf, CommandGene.FloatClass),
                new FloatAnd(conf, CommandGene.FloatClass),
                new FloatOr(conf, CommandGene.FloatClass),
                new FloatNot(conf, CommandGene.FloatClass), //Returns boolean
                // ------- Game specific functions -------
                new MakeMove(conf), //Is VoidClass
                // ------- Basic terminals ---------
                new Terminal(conf, CommandGene.FloatClass, 0, 5, false, 0, true),
                // ------- Sensors --------

                new IsPieceAt(conf, CommandGene.FloatClass), //                new CurrentBoardStatus(conf, CommandGene.FloatClass)
            }
        };



        return GPGenotype.randomInitialGenotype(conf, types, argTypes, nodes, 100, true);


    }

    public AgentGP(
            GPConfiguration conf) throws InvalidConfigurationException {
        super(conf);
        //controller = new Controller(board);


    }

    public static void main(String[] args) throws InvalidConfigurationException {
        Settings.addPlayer(new Player("0", Color.YELLOW));
        Settings.addPlayer(new Player("1", Color.GREEN));
        Settings.getPlayer(0).setMyTurn(true);
        Playboard board = new Playboard(player1Pieces, player2Pieces);

        GPConfiguration conf = new GPConfiguration();

        conf.setPopulationSize(POP_SIZE);
        conf.setFitnessEvaluator(new DefaultGPFitnessEvaluator());
        conf.setFitnessFunction(new TraverseFitnessFunction(board, player1Pieces, player2Pieces));
        conf.setRandomGenerator(new StockRandomGenerator());
        conf.setMutationProb(0.5f);

        conf.getEventManager().addEventListener(GeneticEvent.GPGENOTYPE_NEW_BEST_SOLUTION, new GeneticEventListener() {

            public void geneticEventFired(GeneticEvent a_firedEvent) {
                GPGenotype genotype = (GPGenotype) a_firedEvent.getSource();
//                System.out.println("New best guy: " + genotype.getAllTimeBest().execute_int(0, null));


            }
        });

        AgentGP gpProblem = new AgentGP(conf);
        GPGenotype genotype = gpProblem.create();
        genotype.setVerboseOutput(true);
        genotype.evolve(20);
        System.out.println("best guy: " + genotype.getAllTimeBest().toStringNorm(0) + " with fitness "
                + genotype.getAllTimeBest().getFitnessValue());
//        genotype.calcFitness();

    }
}
