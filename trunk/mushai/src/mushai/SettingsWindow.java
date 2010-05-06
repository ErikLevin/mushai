/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author bark
 */
public class SettingsWindow extends JFrame implements ActionListener {

    JComboBox player1list, player2list;
    JTextField player1Name, player2Name;

    public SettingsWindow() {
        setPreferredSize(new Dimension(400, 200));
        //leftside
        setLayout(new GridLayout(3, 2));
        JPanel jp = new JPanel(new GridLayout(1, 2));
        jp.add(new JLabel("player 1 name:"), 0, 0);
        player1Name = new JTextField("name:");
        jp.add(player1Name, 0, 1);
        add(jp, 0, 0);
        String[] player1 = {"human", "ai minmax", "generisk"};
        player1list = new JComboBox(player1);
        player1list.setSelectedIndex(0);
        add(player1list, 0, 1);

        //rigth side
        JPanel jpr = new JPanel(new GridLayout(1, 2));
        jpr.add(new JLabel("player 1 name:"), 0, 0);
        player2Name = new JTextField("name:");
        jpr.add(player2Name, 0, 1);
        add(jpr, 1, 0);
        String[] player2 = {"human", "ai minmax", "generisk"};
        player2list = new JComboBox(player2);
        player2list.setSelectedIndex(0);
        add(player2list, 1, 1);
        /////
        JButton ok = new JButton("ok");
        ok.addActionListener(this);
        add(ok, 0, 4);
  

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        Player player1 = new Player(player1Name.getText(), Color.yellow);
        Settings.addPlayer(player1);
        if (player1list.getSelectedIndex() == 1) {
            player1.setAi(true);
            player1.setMinMax(true);
        } else if (player1list.getSelectedIndex() == 2) {
            player1.setAi(true);
            player1.setMinMax(false);
        }
        player1.setMyTurn(true);

        Player player2 = new Player(player2Name.getText(), Color.cyan);
        Settings.addPlayer(player2);
        if (player2list.getSelectedIndex() == 1) {
            player2.setAi(true);
            player2.setMinMax(true);
        } else if (player1list.getSelectedIndex() == 2) {
            player2.setAi(true);
            player2.setMinMax(false);
        }
        Window win = new Window();
        Controller controller = new Controller(win);
	this.dispose();
    }
}
