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
import minmax.PlayboardModel;

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
        int[][] board = new int[Settings.getPlayboardSize()][Settings.getPlayboardSize()];
        board[3][0] = PlayboardModel.PLAYER2_SQUARE;
        board[4][0] = PlayboardModel.PLAYER2_TRIANGLE;
        board[5][0] = PlayboardModel.PLAYER2_CIRCLE;
        board[6][0] = PlayboardModel.PLAYER2_RHOMBUS;
        board[3][1] = PlayboardModel.PLAYER2_TRIANGLE;
        board[3][2] = PlayboardModel.PLAYER2_SQUARE;
        board[4][1] = PlayboardModel.PLAYER2_CIRCLE;
        board[4][3] = PlayboardModel.PLAYER2_RHOMBUS;

        board[4][7] = PlayboardModel.PLAYER1_SQUARE;
        board[5][8] = PlayboardModel.PLAYER1_TRIANGLE;
        board[1][9] = PlayboardModel.PLAYER1_CIRCLE;
        board[6][8] = PlayboardModel.PLAYER1_RHOMBUS;
        board[2][9] = PlayboardModel.PLAYER1_TRIANGLE;
        board[8][8] = PlayboardModel.PLAYER1_SQUARE;
        board[8][9] = PlayboardModel.PLAYER1_CIRCLE;
        board[3][9] = PlayboardModel.PLAYER1_RHOMBUS;

        pb = new Playboard(board);
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
