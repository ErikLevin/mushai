package gp;

import java.awt.Color;
import java.util.List;
import minmax.MiniMax;
import mushai.Controller;
import mushai.Model;
import mushai.Move;
import mushai.Playboard;
import mushai.Player;
import mushai.Settings;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.impl.ProgramChromosome;

/**
 * Class that lets an evolved chromosome compete against the minimax method in a
 * game of Traverse.
 *
 * @author MushAI
 */
public class MinimaxVsGenetic {

    public static void main(String[] args) throws InvalidConfigurationException {
        // <editor-fold defaultstate="collapsed" desc="Setup players and board">
        Player player = new Player("0", Color.yellow);
        player.setMyTurn(true);
        Settings.addPlayer(player);
        Settings.addPlayer(new Player("1", Color.blue));
        Playboard pb = new Playboard(1, 1, 4);
        Controller c = new Controller(pb);// </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Setup agents">
        AgentGP gp = new AgentGP();
        ProgramChromosome genetic = gp.getBestGuy();
        Object[] geneticArgs = new Object[]{c.getBoard()};
        MiniMax miniMax = new MiniMax(c, pb);

//        reader = new BufferedReader(new FileReader("mushai.gt"));
//        genetic = new ProgramChromosome(new GPConfiguration());
//        String s = "";
//        String bestGuyString = "";
//        while ((s = reader.readLine()) != null) {
//            bestGuyString += s;
//        }
//        genetic.setValueFromPersistentRepresentation(bestGuyString);
//        reader.close();

        // </editor-fold>

        List<Move> moves;
        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {
                System.out.println("-------------------");
                System.out.println("Round: " + (1 + (i / 2)));
                Move bestMove = miniMax.findBestMove(1);
                c.move(bestMove);
            } else {
                genetic.execute_void(0, 0, geneticArgs);
            }
            System.out.println(pb);
            System.out.println("Fitness after move: " + Model.getBoardFitness(pb));
            System.out.println("\n\n");
            if (Model.getBoardFitness(pb) > 1000 || Model.getBoardFitness(pb) == 0) {
//                break;
            }
        }
    }
}

