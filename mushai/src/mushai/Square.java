/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mushai;
import java.util.ArrayList;
import java.awt.Point;

/**
 *
 * @author bark
 */
public class Square extends Piece {

    public Square() {
        super("hej");
    }
    public ArrayList<Point> getSquareMoves(){
        ArrayList<Point> moves = new ArrayList<Point>();
        Point forward,backward,left,right;
        int x = (int)position.getX();
        int y = (int)position.getY();
        if (Color.RED){
            forward = new Point(x,y-1);
            backward = new Point(x,y+1);
            left = new Point(x-1,y);
            right = new Point(x+1,y);
            
        }
        else if (Color.BLACK){
            forward = new Point(x,y+1);
            backward = new Point(x,y-1);
            left = new Point(x+1,y);
            right = new Point(x-1,y);
            moves.add(forward);
            moves.add(backward);
            moves.add(left);
            moves.add(right);
        }else{
            return null;
        }
        moves.add(forward);
        moves.add(backward);
        moves.add(left);
        moves.add(right);
        return moves;
    }
}
