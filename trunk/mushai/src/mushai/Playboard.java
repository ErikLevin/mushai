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
import javax.swing.JPanel;

/**
 *
 * @author bark
 */

public class Playboard extends JPanel {
int playboardSize=4;
    public Playboard(){
        super();

        this.setPreferredSize(new Dimension(200,200));

        setLayout(new GridLayout(playboardSize,playboardSize));
        //add(new JLabel("ololll"));
        for(int i=0;i<playboardSize*playboardSize;i++){
            add(new Square());
        }

    }
}
