package mushai;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.HashSet;
import javax.swing.Icon;

/**
 *
 * @author bark
 */
public class Square extends Piece {

    public Square(int x, int y, Color color) {
        super(x, y, color);
        icon = new SquareIcon(color);
    }

    @Override
    protected void updateMoves() {
        Point forward = null, backward = null, left = null, right = null;
        moves = new HashSet<Point>();


        int x = getX();


        int y = getY();


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

    private class SquareIcon extends PieceIcon
    {
        public SquareIcon(Color color){
            this.color = color;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x, y, getIconWidth(), getIconHeight());
        }

        @Override
        public int getIconWidth() {
            return 75;
        }

        @Override
        public int getIconHeight() {
            return 75;
        }

    }
}
