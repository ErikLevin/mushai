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
    JTextField player1Name, player2Name, nrOfSqares;

    public SettingsWindow() {
        setPreferredSize(new Dimension(400, 200));
        //leftside
        setLayout(new GridLayout(3, 2));
        JPanel jp = new JPanel(new GridLayout(1, 2));
        jp.add(new JLabel("player 1 name:"), 0, 0);
        player1Name = new JTextField("name:");
        jp.add(player1Name, 1, 0);

        add(jp, 0, 0);
        String[] player1 = {"human", "ai minmax", "generisk"};
        player1list = new JComboBox(player1);
        player1list.setSelectedIndex(0);
        //add(player1list, 0, 1);

        //rigth side
        JPanel jpr = new JPanel(new GridLayout(1, 2));
        jpr.add(new JLabel("player 2 name:"), 0, 0);
        player2Name = new JTextField("name:");
        jpr.add(player2Name, 0, 1);
        add(jpr, 0, 1);
        String[] player2 = {"human", "ai minmax", "generisk"};
        player2list = new JComboBox(player2);
        player2list.setSelectedIndex(1);
        //add(player2list, 1, 1);
        /////
        JButton ok = new JButton("ok");
        ok.addActionListener(this);

        JPanel jp2 = new JPanel(new GridLayout(1, 2));
        nrOfSqares = new JTextField("8");
        jp2.add(new JLabel("playboard size:"), 0, 0);
        jp2.add(nrOfSqares, 0, 1);
        add(jp2, 2, 2);
        add(ok, 3, 2);
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        Settings.nullPlayers();
        Player player1 = new Player(player1Name.getText(), Color.yellow);
        Settings.addPlayer(player1);
        if (player1list.getSelectedIndex() == 1) {
            player1.setType(Player.PlayerType.MINIMAX);
        } else if (player1list.getSelectedIndex() == 2) {
//            JOptionPane.showMessageDialog(this, null, "Genetic AI not supported yet", JOptionPane.ERROR_MESSAGE);
            player1.setType(Player.PlayerType.GENETIC);
        }

        Player player2 = new Player(player2Name.getText(), Color.cyan);
        player2.setMyTurn(true);

        Settings.addPlayer(player2);
        if (player2list.getSelectedIndex() == 1) {
            player2.setType(Player.PlayerType.MINIMAX);
        } else if (player2list.getSelectedIndex() == 2) {
//          JOptionPane.showMessageDialog(this, null, "Genetic AI not supported yet", JOptionPane.ERROR_MESSAGE);
            player2.setType(Player.PlayerType.GENETIC);
        }

        int size = Integer.parseInt(nrOfSqares.getText());


        System.out.println(size + "size");


        Settings.setPlayboardSize(size);

        Window win = new Window();
        Controller controller = new Controller(win);
        this.dispose();
    }
}
