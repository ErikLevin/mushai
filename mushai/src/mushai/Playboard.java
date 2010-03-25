/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 * Graphical representation of the game board.
 */
public class Playboard extends JPanel {

    private Tile[][] tiles;

    public Playboard() {
        super();
        int playboardSize = Settings.getPlayboardSize();
        tiles = new Tile[playboardSize][playboardSize];
        setLayout(new GridLayout(playboardSize, playboardSize));

        this.setPreferredSize(new Dimension(200, 200));
        for (int j = 0; j < playboardSize; j++) {
            for (int i = 0; i < playboardSize; i++) {
                tiles[i][j] = new Tile();
                add(tiles[i][j]);
            }
        }
        tiles[1][1].setPiece(new Square(1, 1, Settings.getPlayers().get(0).getColor()));
        tiles[3][2].setPiece(new Square(3, 2, Settings.getPlayers().get(1).getColor()));
        update();
    }

    /**
     * Updates the Playboard so that pieces are shown in their correct places.
     * To be executed each time a move has been made.
     */
    public void update() {
        for (Tile[] ts : tiles) {
            for (Tile t : ts) {
                t.update();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Tile[] ts : tiles) {
            for (Tile t : ts) {
                t.paintComponents(g);
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
