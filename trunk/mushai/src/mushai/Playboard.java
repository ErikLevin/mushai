/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
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
    }

    public Playboard(int noPlayer1Pieces, int noPlayer2Pieces) {
        this();
        initializeBoard(noPlayer1Pieces, noPlayer2Pieces);
    }

    /**
     * Initializes the Playboard, only with Square pieces for now.
     *
     * @param noPlayer1Pieces - Number of pieces that player 1 controls
     * @param noPlayer2Pieces - Number of pieces that player 2 controls
     */
    public void initializeBoard(int noPlayer1Pieces, int noPlayer2Pieces) {
        clearBoard();

        for (int i = 0; i < noPlayer1Pieces; i++) {
            tiles[i][0].setPiece(new Square(Settings.getPlayers().get(0).getColor()));
        }

        for (int i = 0; i < noPlayer2Pieces; i++) {
            tiles[i][Settings.getPlayboardSize() - 1].setPiece(
                    new Square(Settings.getPlayers().get(1).getColor()));
        }
        update();
    }

    /**
     * Updates the Playboard so that pieces are shown in their correct places.
     * To be executed each time a move has been made.
     */
    public void update() {
        for (int j = 0; j < Settings.getPlayboardSize(); j++) {
            for (int i = 0; i < Settings.getPlayboardSize(); i++) {
                tiles[i][j].update(i, j);
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

    public int getFitness() {
        int fitness = 0;
        for (int j = 0; j < Settings.getPlayboardSize(); j++) {
            for (int i = 0; i < Settings.getPlayboardSize(); i++) {
                Piece p = tiles[i][j].getPiece();
                if (p != null) {
                    if (p.color == Settings.getPlayers().get(0).getColor()) {
                        fitness += i;
                    } else {
                        fitness -= i;
                    }
                }
            }
        }
        if (fitness < 0) {
            return 0;
        }
        return fitness;
    }

    private void clearBoard() {
        for (Tile[] ts : tiles) {
            for (Tile t : ts) {
                t = null;
            }
        }
        update();
    }
}
