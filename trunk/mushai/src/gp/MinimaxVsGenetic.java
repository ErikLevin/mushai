package gp;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import minmax.MiniMax;
import minmax.PlayboardModel;
import mushai.Controller;
import mushai.Model;
import mushai.Move;
import mushai.Playboard;
import mushai.Player;
import mushai.Settings;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.IGPProgram;

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
        Settings.setPlayboardSize(4);
        Controller c = new Controller(pb);// </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Setup agents">
        IGPProgram genetic = null;
        Object[] geneticArgs = new Object[]{new PlayboardModel(pb, 0)};
        MiniMax miniMax = new MiniMax(pb);

        ObjectInputStream reader = null;
        try {
            reader = new ObjectInputStream(new FileInputStream("mushai.guy"));
            genetic = (IGPProgram) reader.readObject();
        } catch (IOException ex) {
            Logger.getLogger(MinimaxVsGenetic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.err.println("That makes me a saaad panda");
        }
        // </editor-fold>
        List<Move> moves;
        for (int i = 0;
                i
                < 50; i++) {
            if (i % 2 == 0) {
                System.out.println("-------------------");
                System.out.println("Round: " + (1 + (i / 2)));
                Move bestMove = miniMax.findBestMove(1);
                c.move(bestMove);
            } else {
                genetic.execute_void(0, geneticArgs);
            }
            System.out.println(pb);
            System.out.println("Fitness after move: " + Model.getBoardFitness(pb));
            System.out.println("\n\n");
        }
    }
}

