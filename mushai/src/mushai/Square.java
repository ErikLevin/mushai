package mushai;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;

public class Square extends Piece {

    public Square(Color color) {
        super(color);
        icon = new SquareIcon(color);
        updateMoves();
    }

    protected void updateMoves() {
        /*Point forward = null, backward = null, left = null, right = null;
        moves = new HashSet<Point>();

        if (color == Color.RED) {
            forward = new Point(0,  - 1);
            backward = new Point(0,  1);
            left = new Point(- 1, 0);
            right = new Point( 1, 0);



        } else if (color == Color.BLACK) {
            forward = new Point(0,  1);
            backward = new Point(0, - 1);
            left = new Point(1, 0);
            right = new Point( - 1, 0);


        }
        moves.add(forward);
        moves.add(backward);
        moves.add(left);
        moves.add(right);*/
    }

    private class SquareIcon extends PieceIcon {

        public SquareIcon(Color color) {
            super(color);
        }
 

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            super.paintIcon(c, g, x, y);

            g.fillRect(x, y, getIconWidth(), getIconHeight());
        }
    }
}
