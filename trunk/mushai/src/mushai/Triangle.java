package mushai;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Triangle extends Piece {

    public Triangle(Color color, int dir) {
        super(color,dir);
        icon = new SquareIcon(color);
    }

    public Set<Point> getMoves() {
        Set<Point> pointSet= new HashSet<Point>();

        Point backward, forwardLeft, forwardRight;
        
        forwardLeft = new Point(-1*direction,-1*direction);
        forwardRight = new Point(1*direction,-1*direction);
        backward = new Point(0,  1*direction);
        pointSet.add(forwardLeft);
        pointSet.add(forwardRight);
        pointSet.add(backward);
        return pointSet;

    }
    private class SquareIcon extends PieceIcon {

        public SquareIcon(Color color) {
            super(color);
        }
 

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            super.paintIcon(c, g, x, y);
            int w = getIconWidth()/5;
            int h = getIconHeight()/5;
            int[] xs = {x*w,0,x*w/2};
            int[] ys = {0,0,y*h/2};
            g.fillPolygon(xs,ys,3);
        }
    }
}
