/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mushai;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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


        setLayout( new BorderLayout() );
        
        setResizable(false);
        setUndecorated(false);
        Playboard pb=new Playboard();
        add(pb,BorderLayout.CENTER );
        JPanel playersPanel= new JPanel();
        playersPanel.setLayout(new GridLayout(Settings.getNrOfPlayers(),1));
       
        playersPanel.add(new Player("bark", Color.yellow));//fix temp
        playersPanel.add(new Player("ai", Color.green));//fix temp
        playersPanel.setPreferredSize(new Dimension(100,50));
        add(playersPanel,BorderLayout.EAST);
        pack();
        this.setVisible(true);
    }


}
