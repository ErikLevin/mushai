/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import java.awt.Color;
import javax.swing.JButton;

public class Tile extends JButton {

    private Piece piece;

    public Tile() {
        super();
        piece = null;
        setBackground(Color.LIGHT_GRAY);
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;

    }

    /**
     * Updates Tile (x,y). Sets its icon according to the piece on it, and updates
     * the possible moves.
     * @param x
     * @param y
     */
    public void update(int x, int y) {
        if (piece != null) {
            setIcon(piece.getIcon());
        } else {
            setIcon(null);
        }
    }
}
