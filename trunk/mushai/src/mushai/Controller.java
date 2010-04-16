package mushai;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;

/**
 * Controller class for game logic.
 */
public class Controller implements ActionListener {

    private Playboard board;
    private Window win;
    HashSet<Point> posebleMoves = new HashSet<Point>();
    /**
     * The starting point for ongoing move
     */
    private Point moveStart;
    private ArrayList<Point> moves = new ArrayList<Point>();

    public Controller(Window tWin) {
        win = tWin;
        this.board = win.getBoard();

        for (JButton temp : win.getButtons()) {
            temp.addActionListener(this);
        }
        for (int j = 0; j < Settings.getPlayboardSize(); j++) {
            for (int i = 0; i < Settings.getPlayboardSize(); i++) {
                board.getTiles()[i][j].addActionListener(this);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {


        System.out.println(((JButton) e.getSource()).getText());
        if (((JButton) e.getSource()).getText().equals("end turn")) {
            // när du trycker på end turn kommer du från första i listan till sista
            move(moves.get(0), moves.get(moves.size() - 1));
        }
        if (e.getSource().getClass() == Tile.class) { //A Tile was clicked
            Tile t = (Tile) e.getSource();
            int xTile = 0, yTile = 0;
            outer:
            for (xTile = 0; xTile < Settings.getPlayboardSize(); xTile++) {
                for (yTile = 0; yTile < Settings.getPlayboardSize(); yTile++) {

                    if (board.getTiles()[xTile][yTile] == t) {
                        break outer;
                    }
                }
            }

            Point presedPoint = new Point(xTile, yTile);
            if (moveStart != null) {
                if (whereCanIMove2(moveStart).contains(presedPoint)) {
                    move(moveStart, presedPoint);
                }
            }
            if (getPiece(presedPoint) != null) {
                if (moveStart == null) {
                    moveStart = presedPoint;
                }
            }


            /*
            System.out.println("x: " + xTile + " y: " + yTile);
            if (t.getPiece() != null) { // is piece on tile
            if (moves.isEmpty()) {
            moves.add(new Point(xTile, yTile));
            } else {
            t.setBackground(Color.RED);

            }
            } else {
            if (!moves.isEmpty()) {
            canIMoveThere(moves.get(moves.size() - 1), new Point(xTile, yTile));
            }
            }

            }

             */        }
    }

    private void move(Point start, Point end) {
        Tile origin = board.getTiles()[start.x][start.y];
        Piece p = origin.getPiece();
        origin.setPiece(null);
        board.getTiles()[end.x][end.y].setPiece(p);
        moveStart = null;
        changePlayer();

        board.update();
    }

    public void changePlayer() {


        /**nollställer brädet **/
        ArrayList<Player> arL = Settings.getPlayers();
        moves = new ArrayList<Point>();
        posebleMoves=new HashSet<Point>();

        int xTile = 0, yTile = 0;
        for (xTile = 0; xTile < Settings.getPlayboardSize(); xTile++) {//målar om hela brädet
            for (yTile = 0; yTile < Settings.getPlayboardSize(); yTile++) {
                board.getTiles()[xTile][yTile].setBackground(Color.lightGray);
                board.getTiles()[xTile][yTile].repaint();
            }
        }
        /**räknar ut vems person det är **/
        for (int i = 0; i < Settings.getNrOfPlayers(); i++) {
            if (arL.get(i).isItMyturn()) {
                arL.get(i).isNotMyTurn();
                if (Settings.getNrOfPlayers() - 1 != i) {
                    arL.get(i + 1).isMyTurn();
                    break;
                } else {
                    arL.get(0).isMyTurn();
                    break;
                }

            }
        }
    }

    private HashSet<Point> whereCanIMove2(Point from) {

        Piece pi = getPiece(from);
        for (Point diffPoint : pi.getMoves()) {
            Point temp = new Point(from.x + diffPoint.x, from.y + diffPoint.y);
            if (pointOnBorde(temp)) {


                if (getPiece(temp) == null) {
                    posebleMoves.add(temp);
                }

            }
        }

        posebleMoves.addAll(jumping(from, pi));
        return posebleMoves;
    }

    private boolean pointOnBorde(Point point) {
        if (point.x >= 0 && point.x < Settings.getPlayboardSize()) {
            if (point.y >= 0 && point.y < Settings.getPlayboardSize()) {
                return true;


            }
        }
        return false;


    }

    private HashSet<Point> jumping(Point start, Piece pi) {



        if (pointOnBorde(start)) {
            posebleMoves.add(start);

            for (Point diffPoint : pi.getMoves()) {
                Point posOfPice = new Point(start.x + diffPoint.x, start.y + diffPoint.y);
                if (pointOnBorde(posOfPice)) {

                    if (getPiece(posOfPice) != null) {

                        Point posAfterJump = new Point(start.x + diffPoint.x + diffPoint.x, start.y + diffPoint.y + diffPoint.y);

                        if (pointOnBorde(posAfterJump)) {

                            if (getPiece(posAfterJump) == null) {
                               if(!posebleMoves.contains(posAfterJump)){
                                posebleMoves.addAll(jumping(posAfterJump, pi));
                               }

                            }
                        }
                    }
                }
            }
        }
        return posebleMoves;


    }

    /* boolean canIMoveThere(Point from, Point to) {//ska bli ersatt med en bättre
    System.out.println("from:" + from + "  to:" + to + "   size of moves:" + moves.size() + "bord size:" + board.getTiles().length);
    Square pi = (Square) board.getTiles()[moves.get(0).x][moves.get(0).y].getPiece();
    pi.updateMoves();

    for (Point tempPoint : pi.getMoves()) {
    boolean endTurn = true;

    Point newPoint = new Point(from.x + tempPoint.x, from.y + tempPoint.y);
    if (newPoint.x + tempPoint.x >= 0 && newPoint.x + tempPoint.x < Settings.getPlayboardSize()) {
    if (newPoint.y + tempPoint.y >= 0 && newPoint.y + tempPoint.y < Settings.getPlayboardSize()) {

    if (board.getTiles()[newPoint.x][newPoint.y].getPiece() != null) {
    newPoint = new Point(newPoint.x + tempPoint.x, newPoint.y + tempPoint.y);
    endTurn = false;
    }
    }
    }
    if (newPoint.equals(to)) {
    if (moves.size() < 2 || !endTurn) {
    moves.add(to);
    return true;
    }
    if (endTurn) {
    move(moves.get(0), moves.get(moves.size() - 1));
    return true;
    }
    // drawLine mellan dessa rutor
    }
    }
    return false;
    }*//*
    public ArrayList<Point> whereCanIMove(Point point) {
    ArrayList<Point> legalMoves = new ArrayList<Point>();
    legalMoves.add(point);
    int xTile = 0, yTile = 0;
    for (xTile = 0; xTile < Settings.getPlayboardSize(); xTile++) {//målar om hela brädet
    for (yTile = 0; yTile < Settings.getPlayboardSize(); yTile++) {
    if(whereCanIMove2(point))
    if (canIMoveThere(point, new Point(xTile, yTile))) {
    legalMoves.add(new Point(xTile, yTile));
    }
    }

    }
    return legalMoves;
    }
     */

    public ArrayList<ArrayList<Point>> getallPosebleMoves() {
        ArrayList<Point> pices = getYoursPieces();
        ArrayList<ArrayList<Point>> allTheMoves = new ArrayList<ArrayList<Point>>();



        for (Point p : pices) {
            ArrayList<Point> temp = new ArrayList<Point>();
            temp.add(p);


            for (Point i : whereCanIMove2(p)) {
                temp.add(i);


            }
            allTheMoves.add(temp);


        }
        return allTheMoves;


    }

    public ArrayList<Point> getYoursPieces() {
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
                Piece tPiece = getPiece(new Point(xTile, yTile));


                if (tPiece != null) {
                    if (tPiece.color == whichPlayerTurn) {
                        pieces.add(new Point(xTile, yTile));


                    }
                }
            }
        }
        return pieces;


    }

    private Piece getPiece(Point point) {
        return board.getTiles()[point.x][point.y].getPiece();

    }
}
