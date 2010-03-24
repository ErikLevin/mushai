/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mushai;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author bark
 */

public class Playboard extends JComponent {
int playboardSize=4;
    public Playboard(){
        super();

        this.setPreferredSize(new Dimension(200,200));
        add(new JLabel("ololll"));
        setLayout(new GridLayout(playboardSize,playboardSize));
        for(int i=0;i<playboardSize;i++){
            add(new Tile());
        }

    }
}
