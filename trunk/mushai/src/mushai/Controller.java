package mushai;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for game logic.
 */
public class Controller implements ActionListener {

    private Playboard board;
    /**
     * The starting point for ongoing move
     */
    private Point moveStart;

    public Controller(Playboard board) {
        this.board = board;

        for (int j = 0; j < Settings.getPlayboardSize(); j++) {
            for (int i = 0; i < Settings.getPlayboardSize(); i++) {
                board.getTiles()[i][j].addActionListener(this);
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

                    if (board.getTiles()[xTile][yTile] == t) {
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
        Tile origin = board.getTiles()[start.x][start.y];
        Piece p = origin.getPiece();
        origin.setPiece(null);
        board.getTiles()[end.x][end.y].setPiece(p);
        moveStart = null;
        board.update();
    }
}
