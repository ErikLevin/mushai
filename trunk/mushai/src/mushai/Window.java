/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mushai;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author bark
 */
public class Window extends JFrame {

    public Window(){
        super();
        setTitle("calender");
        setPreferredSize(new Dimension(650,500));
        setLayout(new FlowLayout());
        setResizable(false);
        setUndecorated(false);
        Playboard pb=new Playboard();
        pb.setPreferredSize(new Dimension(500,  500));
        add(pb);
        JPanel playersPanel= new JPanel();
        playersPanel.setLayout(new GridLayout(Settings.getNrOfPlayers(),1));
        playersPanel.setPreferredSize(new Dimension(120,500));
        playersPanel.add(new Player("bark", Color.yellow));//fix temp
        playersPanel.add(new Player("ai", Color.green));//fix temp
        add(playersPanel);
        pack();
        this.setVisible(true);
    }


}
