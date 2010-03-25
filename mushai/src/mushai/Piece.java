/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import java.awt.Color;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Icon;

/**
 *
 * @author bark
 */
public abstract class Piece {

    protected Point position;
    protected Icon icon;
    protected Set<Point> moves;
    private boolean outOfStartArea;

    public Point getPosition() {
        return position;
    }

    public Icon getIcon() {
        return icon;
    }

    /**
     *
     * @param x
     * @param y
     */
    protected abstract void updateMoves(int x, int y);
    protected Color color;

    public Piece() {
        color = Color.RED;
        position = new Point(0, 0);
        icon = null;
        moves = new HashSet<Point>();
    }

    public Piece(Color color) {
        this();
        this.color = color;
    }

    public Set<Point> getMoves() {
        return moves;
    }
}
