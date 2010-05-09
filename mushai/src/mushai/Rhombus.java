package mushai;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Rhombus extends Piece {

    public Rhombus(Color color, int direction) {
        super(color, direction);
        icon = new SquareIcon(color);
    }

    public Set<Point> getMoves() {
        Set<Point> pointSet = new HashSet<Point>();

        Point forwardLeft, forwardRight, backLeft, backRight;
        forwardLeft = new Point(-1 * direction, -1 * direction);
        forwardRight = new Point(1 * direction, -1 * direction);
        backLeft = new Point(-1 * direction, 1 * direction);
        backRight = new Point(1 * direction, 1 * direction);
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
            int w = getIconWidth() / 5;
            int h = getIconHeight() / 5;
            int[] xs = {x * w, 0, x * w / 2};
            int[] ys = {y*h/2, y*h/2, 0};
            int[] xsUp = {x*w/2,x*w,0};
            int[] ysUp = {y*h,y*h/2,y*h/2};
            g.fillPolygon(xsUp, ysUp, 3);
            g.fillPolygon(xs, ys, 3);

        }
    }
}
