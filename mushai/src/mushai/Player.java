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
    int points=0;
    
    public Player(String tname,Color tcolor){
       super();
       name=tname;
       color=tcolor;
       setLayout(new GridLayout(2,1));
        JLabel JLname =new JLabel(name);
        JLname.setFont(JLname.getFont().deriveFont(20f));
       add(JLname);
        setLayout(new GridLayout(2,1));
        JLabel JLpoints =new JLabel(points+"");
        JLpoints.setFont(JLpoints.getFont().deriveFont(20f));
       add(JLpoints);

       setBackground(color);
    }


}
