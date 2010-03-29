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
    }

    protected void updateMoves(int x, int y) {
        Point forward = null, backward = null, left = null, right = null;
        moves = new HashSet<Point>();

        if (color == Color.RED) {
            forward = new Point(x, y - 1);
            backward = new Point(x, y + 1);
            left = new Point(x - 1, y);
            right = new Point(x + 1, y);



        } else if (color == Color.BLACK) {
            forward = new Point(x, y + 1);
            backward = new Point(x, y - 1);
            left = new Point(x + 1, y);
            right = new Point(x - 1, y);


        }
        moves.add(forward);
        moves.add(backward);
        moves.add(left);
        moves.add(right);
    }

    private class SquareIcon extends PieceIcon {

        public SquareIcon(Color color) {
            super(color);
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            super.paintIcon(c, g, x, y);

            g.fillRect(x, y, getIconWidth(), getIconHeight());
        }
    }
}