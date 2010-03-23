/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mushai;

import java.awt.GridLayout;
import javax.swing.JFrame;

/**
 *
 * @author bark
 */

public class Playboard extends JFrame {
int playboardSize=4;
    public Playboard(){
        super.setLayout(new GridLayout(playboardSize,playboardSize));
        for(int i=0;i<playboardSize;i++){
            add(new Square());
        }
    }
}
