/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import java.awt.Color;
import javax.swing.JButton;

/**
 *
 * @author bark
 */
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

    public void update() {
        if (piece != null) {
            setIcon(piece.getIcon());
        }
    }
}
