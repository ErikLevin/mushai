/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mushai;

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
        super.setSize(200,200);
        setResizable(false);
        setUndecorated(true);
        Playboard pb=new Playboard();
        add(pb);
        add(new JLabel("smdas"));
        pack();
        this.setVisible(true);
    }


}
