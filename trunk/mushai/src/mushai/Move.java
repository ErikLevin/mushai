
package mushai;

import java.awt.Point;

/**
 * Represents a move, including the start and end points.
 */
public class Move {
    private final Point start;
    private final Point end;

    public Move(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getEnd() {
        return end;
    }

    public Point getStart() {
        return start;
    }

    public int getStartX(){
        return start.x;
    }

    public int getStartY(){
        return start.y;
    }

    public int getEndX(){
        return end.x;
    }

    public int getEndY(){
        return end.y;
    }
}
