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

    private boolean myTurn = false;
    private String name;
    private Color color;
    private int points = 0;
    private JLabel jLmyTurn = new JLabel("");
    boolean ai= false;
    boolean minMax= true;

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
    public void setAi(Boolean tai){
        ai=tai;
    }
    public void setMinMax(Boolean tminMax){
        minMax=tminMax;
    }


    public void setMyTurn(boolean b) {
        if (b)
            jLmyTurn.setText("min tur");
        else
            jLmyTurn.setText("");
        myTurn = b;
    }

    public boolean isItMyTurn() {
        return myTurn;
    }

    public Color getColor() {
        return color;
    }
    public void addPoint(){
        points++;
    }
}
