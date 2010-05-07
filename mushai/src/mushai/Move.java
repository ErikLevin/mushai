package mushai;

import java.awt.Point;

/**
 * Represents a move, including the start and end points.
 */
public class Move  {

    private final Point start;
    private final Point end;

    public Move(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    Move(int fromX, int fromY, int toX, int toY) {
        this(new Point(fromX, fromY), new Point(toX, toY));
    }

    public Point getEnd() {
        return end;
    }

    public Point getStart() {
        return start;
    }

    public int getStartX() {
        return start.x;
    }

    public int getStartY() {
        return start.y;
    }

    public int getEndX() {
        return end.x;
    }

    public int getEndY() {
        return end.y;
    }
    public String toString(){
        String from = "[x: " + start.x + ", y: " + start.y + "]";
        String to = "[x: " + end.x + ", y: " + end.y + "]";
        return from + "--->" + to;
    }


    @Override
    public boolean equals(Object o) {
       if(o instanceof Move){
           Move other =((Move)o);
           if(other.getEnd()==getEnd()&&other.getStart()==getStart()){
            return true;
           }
       }
        return false;
    }
}
