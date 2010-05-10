package gp;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import minmax.PlayboardModel;
import mushai.Controller;
import mushai.Playboard;
import mushai.Player;
import mushai.Settings;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.GreaterThan;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.DefaultGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.impl.ProgramChromosome;
import org.jgap.gp.terminal.Terminal;
import org.jgap.impl.StockRandomGenerator;

/**
 * Genetic programming algorithm to evolve a program that plays the game.
 * 
 */
public class AgentGP extends GPProblem {

    private static final int POP_SIZE = 50;
    private static GPConfiguration conf;
    private IGPProgram bestGuy = null;

    public IGPProgram getBestGuy() {
        return bestGuy;
    }

    /**
     * For saving the best chromosome to a file. Doesn't work.
     *
     * @param genotype - Genotype to save best individual from
     * @throws IOException
     * @throws Exception
     */
    private static void saveChromosome(IGPProgram bestGuy) throws IOException, Exception {
        ObjectOutputStream writer = null;
        try {
            writer = new ObjectOutputStream(new FileOutputStream("mushai.guy"));

//            Chromosome chrom = new Chromosome(conf);
//            chrom.setValueFromPersistentRepresentation(bestGuy);


//            System.out.println("Saved and restored guy: " + chrom.toString());
//            System.out.println("Best guy to save: " + bestGuy);
            writer.writeObject(bestGuy);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
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

    public AgentGP() throws InvalidConfigurationException {
        super();
        Settings.addPlayer(new Player("0", Color.YELLOW));
        Settings.addPlayer(new Player("1", Color.GREEN));
        Settings.getPlayer(0).setMyTurn(true);
        Playboard graphicalBoard = new Playboard(player1Pieces, player2Pieces);
        PlayboardModel board = new PlayboardModel(graphicalBoard, 0);

        conf = new GPConfiguration();

        conf.setPopulationSize(POP_SIZE);
        conf.setFitnessEvaluator(new DefaultGPFitnessEvaluator());
        conf.setFitnessFunction(new TraverseFitnessFunction(board, player1Pieces, player2Pieces));
        conf.setRandomGenerator(new StockRandomGenerator());
        conf.setMutationProb(0.5f);

//        conf.getEventManager().addEventListener(GeneticEvent.GPGENOTYPE_NEW_BEST_SOLUTION, new GeneticEventListener() {
//            @Override
//            public void geneticEventFired(GeneticEvent a_firedEvent) {
//                GPGenotype genotype = (GPGenotype) a_firedEvent.getSource();
////                System.out.println("New best guy: " + genotype.getAllTimeBest().execute_int(0, null));
//            }
//        });

        this.setGPConfiguration(conf);

        GPGenotype genotype = create();
        genotype.setVerboseOutput(true);
        genotype.evolve(50);
        bestGuy = genotype.getAllTimeBest();
        System.out.println("best guy: " + genotype.getAllTimeBest().toStringNorm(0) + " with fitness "
                + genotype.getAllTimeBest().getFitnessValue());
    }

    public static void main(String[] args) throws InvalidConfigurationException, IOException, Exception {
        AgentGP gpProblem = new AgentGP();
        saveChromosome(gpProblem.getBestGuy());
    }
}
