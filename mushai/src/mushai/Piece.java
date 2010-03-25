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

    public int getX() {
        return (int)position.getX();
    }

    public int getY() {
        return (int)position.getY();
    }

    public void setPosition(Point position) {
        this.position = position;
        updateMoves();
    }

    public void setPosition(int x, int y) {
        this.position.setLocation(x, y);
        updateMoves();
    }

    protected abstract void updateMoves();

    protected Color color;

    public Piece() {
        color = Color.RED;
        position = new Point(0, 0);
        icon = null;
        moves = new HashSet<Point>();
    }

    public Piece(int x, int y, Color color) {
        this();
        setPosition(x, y);
        this.color = color;
    }

    public Set<Point> getMoves() {
        return moves;
    }
}
