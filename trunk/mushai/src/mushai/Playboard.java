/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import mushai.Piece.PieceColor;

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
        try {
            tiles[1][1].setPiece(new Square(1, 1, PieceColor.BLACK));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Playboard.class.getName()).log(Level.SEVERE, null, ex);
        }

        update();
    }

    private void update(){
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
