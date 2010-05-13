/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import gp.MinimaxVsGenetic;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import minmax.PlayboardModel;
import org.jgap.gp.IGPProgram;

public class Player extends JPanel {

    private boolean myTurn = false;
    private String name;
    private Color color;
    private int points = 0;
    private JLabel JLpoints;
    IGPProgram genetic;
    Object[] geneticArgs;
    private JLabel jLmyTurn = new JLabel("");
    private PlayerType type = PlayerType.HUMAN;

    public Move makeMove(Playboard board, int turn) {
        if (type == PlayerType.GENETIC) {
            System.out.println("My gene: " + genetic.toStringNorm(0));
            return (Move) genetic.execute_object(0, new Object[]{new PlayboardModel(board, turn)});
        }
        throw new RuntimeException("Genetic couldn't find a move");
    }

    public enum PlayerType {

        HUMAN, MINIMAX, GENETIC
    }

    public Player(String tname, Color tcolor) {
        super();
        name = tname;
        color = tcolor;
        setLayout(new GridLayout(3, 1));
        JLabel JLname = new JLabel(name);
        JLname.setFont(JLname.getFont().deriveFont(20f));
        add(JLname);
        JLpoints = new JLabel("Wins: " + points);
        JLpoints.setFont(JLpoints.getFont().deriveFont(20f));
        add(JLpoints);
        add(jLmyTurn);
        setBackground(color);
    }

    public void setType(PlayerType type) {
        if (type == PlayerType.GENETIC) {
            ObjectInputStream reader = null;
            try {
                reader = new ObjectInputStream(new FileInputStream("mushai.guy"));
                genetic = (IGPProgram) reader.readObject();
            } catch (IOException ex) {
                Logger.getLogger(MinimaxVsGenetic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                System.err.println("That makes me a saaad panda");
            }
        }
        this.type = type;
    }

    public void setMyTurn(boolean b) {
        if (b) {
            jLmyTurn.setText("Make a move");
        } else {
            jLmyTurn.setText("");
        }
        myTurn = b;
    }

    public boolean isItMyTurn() {
        return myTurn;
    }

    public PlayerType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public void addPoint() {
        points++;
        JLpoints.setText("Wins: " + points);
    }
    
    public int getPoints() {
        return points;
    }

    public String getPlayerName() {
        return name;
    }
}
