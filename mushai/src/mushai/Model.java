package mushai;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Model {

    private static HashSet<Point> possibleMoves = new HashSet<Point>();

    public static HashSet<Point> whereCanIMove2(Point from, Playboard board) {

        possibleMoves = new HashSet<Point>();//nollar listan
        Piece pi = getPiece(from, board);
        for (Point diffPoint : pi.getMoves()) {
            Point temp = new Point(from.x + diffPoint.x, from.y + diffPoint.y);
            if (pointOnBoard(temp)) {


                if (getPiece(temp, board) == null) {
                    possibleMoves.add(temp);
                }

            }
        }

        possibleMoves.addAll(jumping(from, pi, board));
        possibleMoves.remove(from);
        return possibleMoves;
    }

    public static boolean pointOnBoard(Point point) {
        if (point.x >= 0 && point.x < Settings.getPlayboardSize()) {
            if (point.y >= 0 && point.y < Settings.getPlayboardSize()) {
                return true;
            }
        }
        return false;
    }

    private static HashSet<Point> jumping(Point start, Piece pi, Playboard board) {
        if (pointOnBoard(start)) {
            possibleMoves.add(start);

            for (Point diffPoint : pi.getMoves()) {
                Point posOfPice = new Point(start.x + diffPoint.x, start.y + diffPoint.y);
                if (pointOnBoard(posOfPice)) {

                    if (getPiece(posOfPice, board) != null) {

                        Point posAfterJump = new Point(start.x + diffPoint.x + diffPoint.x, start.y + diffPoint.y + diffPoint.y);

                        if (pointOnBoard(posAfterJump)) {

                            if (getPiece(posAfterJump, board) == null) {
                                if (!possibleMoves.contains(posAfterJump)) {
                                    possibleMoves.addAll(jumping(posAfterJump, pi, board));
                                }

                            }
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }

    public static int whoseTurnIsIt() {
        ArrayList<Player> arL = Settings.getPlayers();

        for (int i = 0; i < arL.size(); i++) {
            if (arL.get(i).isItMyTurn()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * The amount of steps forwards that player 0 has taken, minus the number of
     * steps player 1 has taken forwards.
     * @return - The fitness for player 0 for the current board.
     */
    public static int getBoardFitness(Playboard board) throws RuntimeException {
        int fitness = boardBaseFitness(board);
        for (int j = 0; j < board.getTiles().length; j++) {
            for (int i = 0; i < board.getTiles().length; i++) {
                Piece p = board.getTiles()[i][j].getPiece();
                if (p != null) {
                    if (p.color == Settings.getPlayers().get(0).getColor()) {
                        fitness += j;
                    } else {
                        fitness -= (board.getTiles().length - 1 - j);
                    }
                }
            }
        }
        if (fitness < 0) {
            throw new RuntimeException("Fitness for board was negative\n" + board.toString());
        }
        return fitness;
    }

    private static int boardBaseFitness(Playboard board) {
        System.out.println("Base fitness: " + board.getTiles().length * getYourPieces(board).size());
        return board.getTiles().length * getYourPieces(board).size();
    }

    public static List<Move> getAllPossibleMoves(Playboard board) {
        return getAllPossibleMoves(board, whoseTurnIsIt());
    }

    public static List<Move> getAllPossibleMoves(Playboard board, int player) {
        ArrayList<Point> pices = getYourPieces(board, player);
        ArrayList<Move> allTheMoves = new ArrayList<Move>();

        for (Point p : pices) {
            for (Point i : whereCanIMove2(p, board)) {
                allTheMoves.add(new Move(p, i));
            }
        }
        return allTheMoves;
    }

    public static ArrayList<Point> getYourPieces(Playboard board) {
        return getYourPieces(board, whoseTurnIsIt());
    }

    public static ArrayList<Point> getYourPieces(Playboard board, int player) {
        Color whichPlayerTurn = Settings.getPlayer(player).getColor();

        ArrayList<Point> pieces = new ArrayList<Point>();
        int xTile = 0, yTile = 0;

        for (xTile = 0; xTile
                < Settings.getPlayboardSize(); xTile++) {
            for (yTile = 0; yTile
                    < Settings.getPlayboardSize(); yTile++) {
                Piece tPiece = getPiece(new Point(xTile, yTile), board);

                if (tPiece != null) {
                    if (tPiece.color == whichPlayerTurn) {
                        pieces.add(new Point(xTile, yTile));
                    }
                }
            }
        }
        return pieces;
    }

    public static Piece getPiece(Point point, Playboard board) {
        return board.getTiles()[point.x][point.y].getPiece();
    }

    public static Playboard movePiece(Playboard board, Move move) {
        return movePiece(board, move.getStart(), move.getEnd());
    }


    public static Playboard movePiece(Playboard board, Point from, Point to) {
        Tile origin = board.getTiles()[from.x][from.y];

        Piece p = Model.getPiece(from, board);
        origin.setPiece(null);
        board.getTiles()[to.x][to.y].setPiece(p);

        return board;

    }
}
