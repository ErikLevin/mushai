/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import gp.AgentGP;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.impl.ProgramChromosome;

public class Player extends JPanel {

    private boolean myTurn = false;
    private String name;
    private Color color;
    private int points = 0;
    private JLabel jLmyTurn = new JLabel("");
    private PlayerType type = PlayerType.HUMAN;
    /**
     * The chromosome that plays if player is genetic AI. Null otherwise.
     */
    private ProgramChromosome chromosome = null;

    void makeMove(Playboard board) {
        if (type == PlayerType.GENETIC) {
            chromosome.execute_void(new Object[]{board});
        }
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
        JLabel JLpoints = new JLabel(points + "");
        JLpoints.setFont(JLpoints.getFont().deriveFont(20f));
        add(JLpoints);
        add(jLmyTurn);
        setBackground(color);
    }

    public void setType(PlayerType type) {
        if (type == PlayerType.GENETIC) {
            AgentGP gp = null;
            try {
                gp = new AgentGP();
            } catch (InvalidConfigurationException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
//            chromosome = gp.getBestGuy();
        }
        this.type = type;
    }

    public void setMyTurn(boolean b) {
        if (b) {
            jLmyTurn.setText("min tur");
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
    }
}
