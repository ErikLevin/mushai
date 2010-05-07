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
    private boolean marked=false;

    public Icon getIcon() {
        return icon;
    }

    /**
     *
     * @param x
     * @param y
     */
    protected Color color;

    public Piece() {
        this(Color.RED);
    }

    public Piece(Color color) {
        icon = null;
        moves = new HashSet<Point>();
        this.color = color;
    }


    public abstract Set<Point> getMoves();

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
    public Color getColor(){
        return color;
    }
}
