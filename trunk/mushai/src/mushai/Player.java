/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mushai;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author bark
 */
public class Player extends JPanel {
    String name;
    Color color;
    
    public Player(String tname,Color tcolor){
       super();
       name=tname;
       color=tcolor;
       setLayout(new GridLayout(1,3));
       add(new JLabel(name));
       setBackground(color);
    }


}
