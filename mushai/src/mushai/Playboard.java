/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author bark
 */
public class Playboard extends JPanel {

    private Tile[][] tiles;

    public Playboard() {
        super();
        int playboardSize = Settings.getPlayboardSize();
        tiles = new Tile[playboardSize][playboardSize];
        setLayout(new GridLayout(playboardSize, playboardSize));

        this.setPreferredSize(new Dimension(200, 200));
        for (int i = 0; i < playboardSize; i++) {
            for (int j = 0; j < playboardSize; j++) {
                tiles[i][j] = new Tile();
                add(tiles[i][j]);
            }
        }
        tiles[1][1].setPiece(new Square(1, 1, Color.BLACK));
        update();
    }

    /**
     * Updates the Playboard so that pieces are shown in their correct places.
     * To be executed each time a move has been made.
     */
    private void update() {
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
}
