package mushai;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import minmax.MiniMax;
import org.jgap.InvalidConfigurationException;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 * Class that lets an evolved chromosome compete against the minimax method in a
 * game of Traverse.
 *
 * @author MushAI
 */
public class MinimaxVsGenetic {

    public static void main(String[] args) {
        // <editor-fold defaultstate="collapsed" desc="Setup players and board">
        Player player = new Player("0", Color.yellow);
        player.setMyTurn(true);
        Settings.addPlayer(player);
        Settings.addPlayer(new Player("1", Color.blue));
        Playboard pb = new Playboard(4, 4, 6);
        Controller c = new Controller(pb);// </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Setup agents">
        BufferedReader reader = null;
        MiniMax miniMax = null;
        ProgramChromosome genetic = null;
        try {
            miniMax = new MiniMax(c, pb);
            reader = new BufferedReader(new FileReader("mushai.gt"));
            genetic = new ProgramChromosome(new GPConfiguration());
            String s = "";
            String bestGuyString = "";
            while ((s = reader.readLine()) != null) {
                bestGuyString += s;
            }
            genetic.setValueFromPersistentRepresentation(bestGuyString);
            reader.close();
        } catch (IOException ex) {
            System.err.println("File mushai.gt not found");
            System.exit(0);
        } catch (UnsupportedRepresentationException ex) {
            System.err.println("Unsupported representation");
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(MinimaxVsGenetic.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
        // </editor-fold>

        List<Move> moves;
        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {
                System.out.println("-------------------");
                System.out.println("Round: " + (1 + (i / 2)));
                Move bestMove = miniMax.findBestMove(1);
                c.move(bestMove);
            } else {
                genetic.execute_void(0, 0, new Playboard[]{c.getBoard()});
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

