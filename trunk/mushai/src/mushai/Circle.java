package mushai;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Circle extends Piece {

    public Circle(Color color) {
        super(color);
        icon = new SquareIcon(color);
    }

    public Set<Point> getMoves() {
        Set<Point> pointSet= new HashSet<Point>();

        Point forward = null, backward = null, left = null, right = null, forwardLeft, forwardRight, backLeft, backRight;
        forward = new Point(0,  - 1);
        backward = new Point(0,  1);
        left = new Point(- 1, 0);
        right = new Point( 1, 0);
        forwardLeft = new Point(-1,-1);
        forwardRight = new Point(1,-1);
        backLeft = new Point(-1,1);
        backRight = new Point(1,1);
        pointSet.add(forward);
        pointSet.add(backward);
        pointSet.add(left);
        pointSet.add(right);
        pointSet.add(forwardLeft);
        pointSet.add(forwardRight);
        pointSet.add(backLeft);
        pointSet.add(backRight);
        return pointSet;

    }
    private class SquareIcon extends PieceIcon {

        public SquareIcon(Color color) {
            super(color);
        }
 

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            super.paintIcon(c, g, x, y);

            g.fillOval(x, y, getIconWidth(), getIconHeight());
        }
    }
}
