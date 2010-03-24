/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mushai;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author bark
 */

public class Playboard extends JPanel {
    public Playboard(){
        super();
        int playboardSize=Settings.getPlayboardSize();
        this.setPreferredSize(new Dimension(200,200));

        setLayout(new GridLayout(playboardSize,playboardSize));
        for(int i=0;i<playboardSize*playboardSize;i++){
            add(new Square());
        }

    }
}
