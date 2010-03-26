/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player extends JPanel {

    boolean myTurn = false;
    String name;
    Color color;
    int points = 0;
    JLabel jLmyTurn = new JLabel("");

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

    public void isMyTurn() {
        jLmyTurn.setText("min tur");
        myTurn = true;
    }

    public boolean isItMyturn() {

        return myTurn;
    }

    public void isNotMyTurn() {
        jLmyTurn.setText("");
        myTurn = false;
    }

    public Color getColor() {
        return color;
    }
}
