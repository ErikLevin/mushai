package mushai;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Square extends Piece {

    public Square(Color color) {
        super(color);
        icon = new SquareIcon(color);
    }

    public Set<Point> getMoves() {
        Set<Point> pontSet= new HashSet<Point>();

        Point forward = null, backward = null, left = null, right = null;
        forward = new Point(0,  - 1);
        backward = new Point(0,  1);
        left = new Point(- 1, 0);
        right = new Point( 1, 0);
        pontSet.add(forward);
        pontSet.add(backward);
        pontSet.add(left);
        pontSet.add(right);
        return pontSet;

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
