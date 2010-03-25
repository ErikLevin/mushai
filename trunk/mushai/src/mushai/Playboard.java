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
 *
 * @author bark
 */
public class Playboard extends JPanel implements ActionListener {

    private Tile[][] tiles;
    /**
     * The starting point for ongoing move
     */
    private Point moveStart;

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
                tiles[i][j].addActionListener(this);
            }
        }
        tiles[1][1].setPiece(new Square(1, 1, Color.BLACK));
        tiles[3][2].setPiece(new Square(3, 2, Color.RED));
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().getClass() == Tile.class) { //A Tile was clicked
            Tile t = (Tile) e.getSource();
            int xTile = 0, yTile = 0;
            outer:
            for (xTile = 0; xTile < Settings.getPlayboardSize(); xTile++) {
                for (yTile = 0; yTile < Settings.getPlayboardSize(); yTile++) {
//                    System.out.println("size "+Settings.getPlayboardSize());

                    if (tiles[xTile][yTile] == t) {
//                        System.out.println("Found tile!");
                        break outer;
                    }
                }
            }

            System.out.println("x: " + xTile + " y: " + yTile);
            if (t.getPiece() != null) { // is piece on tile
                if (moveStart == null) // no piece is picked up
                {
                    t.setBackground(Color.GREEN);
                    moveStart = new Point(xTile, yTile);
                } else { // a piece already picked up
                    t.setBackground(Color.RED);
                }
            } else // no piece on tile
            if (moveStart != null) {
                move(moveStart, new Point(xTile, yTile));
                moveStart = null;
            }
        }
    }

    private void move(Point start, Point end) {
        Tile origin = tiles[start.x][start.y];
        Piece p = origin.getPiece();
        origin.setPiece(null);
        tiles[end.x][end.y].setPiece(p);
        p.setPosition(end.x, end.y);
        moveStart = null;
        update();
    }
}
