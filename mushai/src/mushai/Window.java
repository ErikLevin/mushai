/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {

    Playboard pb;
    ArrayList<JButton> al;

    public Playboard getBoard() {
        return pb;
    }

    public Window() {
        super();
        setTitle("");
        setPreferredSize(new Dimension(650, 500));

        setLayout(new BorderLayout());

        setResizable(false);
        setUndecorated(false);
        pb = new Playboard(4, 4);
        if (Settings.paintGraphics()) {
            add(pb, BorderLayout.CENTER);
        }
        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new GridLayout(Settings.getNrOfPlayers(), 1));
        for (Player pl : Settings.getPlayers()) {
            playersPanel.add(pl);
        }
        playersPanel.setPreferredSize(new Dimension(100, 50));
        JPanel rigthMenu = new JPanel();

        al = new ArrayList<JButton>();
        rigthMenu.setLayout(new BorderLayout());
        rigthMenu.add(playersPanel, BorderLayout.CENTER);
        JPanel buttonMenu = new JPanel();
        JButton endTurn = new JButton("end game");
        endTurn.setFont(endTurn.getFont().deriveFont(8f));
        al.add(endTurn);
        buttonMenu.add(endTurn);
        JButton resetTurn = new JButton("reset turn");
        resetTurn.setFont(resetTurn.getFont().deriveFont(8f));
        al.add(resetTurn);
        buttonMenu.add(resetTurn);
        rigthMenu.add(buttonMenu, BorderLayout.SOUTH);
        add(rigthMenu, BorderLayout.EAST);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }

    public ArrayList<JButton> getButtons() {
        return al;
    }
}
