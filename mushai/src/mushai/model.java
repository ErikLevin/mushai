package mushai;


import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bark
 */
public class model {

    private static HashSet<Point> possibleMoves = new HashSet<Point>();
/*
    public static boolean canIGo(Point to,Point from, ArrayList[][] bord){
        
        
    }

    public static ArrayList<ArrayList<Point>> getAllMoves(){


    }

*/


    public static HashSet<Point> whereCanIMove2(Point from,Playboard board) {
        possibleMoves = new HashSet<Point>();//nollar listan
        Piece pi = getPiece(from,board);
        for (Point diffPoint : pi.getMoves()) {
            Point temp = new Point(from.x + diffPoint.x, from.y + diffPoint.y);
            if (pointOnBorde(temp)) {


                if (getPiece(temp,board) == null) {
                    possibleMoves.add(temp);
                }

            }
        }

        possibleMoves.addAll(jumping(from, pi,board));
        possibleMoves.remove(from);
        return possibleMoves;
    }

    private static boolean pointOnBorde(Point point) {
        if (point.x >= 0 && point.x < Settings.getPlayboardSize()) {
            if (point.y >= 0 && point.y < Settings.getPlayboardSize()) {
                return true;


            }
        }
        return false;


    }

    private static HashSet<Point> jumping(Point start, Piece pi,Playboard board) {



        if (pointOnBorde(start)) {
            possibleMoves.add(start);

            for (Point diffPoint : pi.getMoves()) {
                Point posOfPice = new Point(start.x + diffPoint.x, start.y + diffPoint.y);
                if (pointOnBorde(posOfPice)) {

                    if (getPiece(posOfPice,board) != null) {

                        Point posAfterJump = new Point(start.x + diffPoint.x + diffPoint.x, start.y + diffPoint.y + diffPoint.y);

                        if (pointOnBorde(posAfterJump)) {

                            if (getPiece(posAfterJump,board) == null) {
                                if (!possibleMoves.contains(posAfterJump)) {
                                    possibleMoves.addAll(jumping(posAfterJump, pi,board));
                                }

                            }
                        }
                    }
                }
            }
        }
        return possibleMoves;


    }

    public ArrayList<ArrayList<Point>> getallPosebleMoves(Playboard board) {
        ArrayList<Point> pices = getYoursPieces(board);
        ArrayList<ArrayList<Point>> allTheMoves = new ArrayList<ArrayList<Point>>();



        for (Point p : pices) {
            ArrayList<Point> temp = new ArrayList<Point>();
            temp.add(p);


            for (Point i : whereCanIMove2(p,board)) {
                temp.add(i);


            }
            allTheMoves.add(temp);


        }
        return allTheMoves;


    }

    public static ArrayList<Point> getYoursPieces(Playboard board) {
        Color whichPlayerTurn = null;

        ArrayList<Player> arL = Settings.getPlayers();


        for (int i = 0; i
                < arL.size(); i++) {
            if (arL.get(i).isItMyturn()) {

                whichPlayerTurn = arL.get(i).getColor();


                break;


            }
        }

        ArrayList<Point> pieces = new ArrayList<Point>();


        int xTile = 0, yTile = 0;


        for (xTile = 0; xTile
                < Settings.getPlayboardSize(); xTile++) {
            for (yTile = 0; yTile
                    < Settings.getPlayboardSize(); yTile++) {
                Piece tPiece = getPiece(new Point(xTile, yTile),board);


                if (tPiece != null) {
                    if (tPiece.color == whichPlayerTurn) {
                        pieces.add(new Point(xTile, yTile));


                    }
                }
            }
        }
        return pieces;


    }

    public static Piece getPiece(Point point,Playboard board) {
        return board.getTiles()[point.x][point.y].getPiece();

    }


}
