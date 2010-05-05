package mushai;

import java.awt.Color;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Icon;

public abstract class Piece {

    protected Icon icon;
    protected Set<Point> moves;
    private boolean outOfStartArea;

    public Icon getIcon() {
        return icon;
    }

    /**
     *
     * @param x
     * @param y
     */
    protected abstract void updateMoves();
    protected Color color;

    public Piece() {
        this(Color.RED);
    }

    public Piece(Color color) {
        icon = null;
        moves = new HashSet<Point>();
        this.color = color;
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

    /**
     * Primitive toString. Only makes much sense if there are two players and one kind of piece.
     * @return
     */
    @Override
    public String toString() {
        if(color.equals(Settings.getPlayer(0).getColor()))
        {
            return "1";
        }
        return "2";
    }
}
