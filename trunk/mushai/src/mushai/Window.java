/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mushai;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author bark
 */
public class Window extends JFrame {

    public Window(){
        super();
        setTitle("calender");
        setPreferredSize(new Dimension(400,400));
        setLayout(new GridLayout(1,1));
        setResizable(false);
        setUndecorated(true);

        Playboard pb=new Playboard();
        
        add(pb);
        pack();
        this.setVisible(true);
    }


}
