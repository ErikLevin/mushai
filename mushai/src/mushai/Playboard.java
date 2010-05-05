/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

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
        this(Settings.getPlayboardSize(), Settings.getPlayboardSize());
    }
    public Playboard(int noPlayer1Pieces, int noPlayer2Pieces, int size) {
        super();

        int ps = size;
        Settings.setPlayboardSize(size);
        tiles = new Tile[ps][ps];
        setLayout(new GridLayout(ps, ps));
        this.setPreferredSize(new Dimension(200, 200));
        for (int j = 0; j < ps; j++) {
            for (int i = 0; i < ps; i++) {
                tiles[i][j] = new Tile();
                add(tiles[i][j]);
            }
        }

        resetBoard(noPlayer1Pieces, noPlayer2Pieces);
    }
    public Playboard(int noPlayer1Pieces, int noPlayer2Pieces) {
        super();

        int ps = Settings.getPlayboardSize();
        tiles = new Tile[ps][ps];
        setLayout(new GridLayout(ps, ps));
        this.setPreferredSize(new Dimension(200, 200));
        for (int j = 0; j < ps; j++) {
            for (int i = 0; i < ps; i++) {
                tiles[i][j] = new Tile();
                add(tiles[i][j]);
            }
        }

        resetBoard(noPlayer1Pieces, noPlayer2Pieces);
    }

    /**
     * Initializes the Playboard, only with Square pieces for now.
     *
     * @param noPlayer1Pieces - Number of pieces that player 1 controls
     * @param noPlayer2Pieces - Number of pieces that player 2 controls
     */
    public void resetBoard(int noPlayer1Pieces, int noPlayer2Pieces) {
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

    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[j][i].getPiece() == null) {
                    s += "#";
                } else {
                    s += tiles[j][i].getPiece();
                }
                s += "\t";
            }
            s += "\n";
        }

        return s;
    }

    private void clearBoard() {
        for (Tile[] ts : tiles) {
            for (Tile t : ts) {
                t.setPiece(null);
            }
        }
        update();
    }
}
