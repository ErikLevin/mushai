/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import javax.swing.ImageIcon;

/**
 *
 * @author bark
 */
public class Square extends Piece {


    public Square(int x, int y, PieceColor color) throws FileNotFoundException {
        super(x, y, color);
        File picFile = new File("images/square.png");
        System.out.println(picFile.getAbsolutePath());
        if(!picFile.exists()){
            throw new FileNotFoundException("Image for Square not found");
        }
        icon = new ImageIcon(picFile.getAbsolutePath());
    }

    @Override
    protected void updateMoves() {
        Point forward = null, backward = null, left = null, right = null;
        moves = new HashSet<Point>();
        int x = getX();
        int y = getY();
        if (color == PieceColor.RED) {
            forward = new Point(x, y - 1);
            backward = new Point(x, y + 1);
            left = new Point(x - 1, y);
            right = new Point(x + 1, y);

        } else if (color == PieceColor.BLACK) {
            forward = new Point(x, y + 1);
            backward = new Point(x, y - 1);
            left = new Point(x + 1, y);
            right = new Point(x - 1, y);
            moves.add(forward);
            moves.add(backward);
            moves.add(left);
            moves.add(right);
        }
        moves.add(forward);
        moves.add(backward);
        moves.add(left);
        moves.add(right);
    }
}
