package mushai;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Circle extends Piece {

    public Circle(Color color, int direction) {
        super(color,direction);
        icon = new SquareIcon(color);
    }

    public Set<Point> getMoves() {
        Set<Point> pointSet= new HashSet<Point>();

        Point forward = null, backward = null, left = null, right = null, forwardLeft, forwardRight, backLeft, backRight;
        forward = new Point(0,  - 1*direction);
        backward = new Point(0,  1*direction);
        left = new Point(- 1*direction, 0);
        right = new Point( 1*direction, 0);
        forwardLeft = new Point(-1*direction,-1*direction);
        forwardRight = new Point(1*direction,-1*direction);
        backLeft = new Point(-1*direction,1*direction);
        backRight = new Point(1*direction,1*direction);
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
