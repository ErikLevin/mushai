package minmax;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mushai.*;

/**
 *
 * @author Hasse
 */
public class PlayboardModel implements Cloneable{

    static final int EMPTY_TILE = 0;
    static final int PLAYER1_SQUARE = 1;
    static final int PLAYER1_CIRCLE = 2;
    static final int PLAYER1_TRIANGLE = 3;
    static final int PLAYER1_RHOMBUS = 4;
    static final int PLAYER2_SQUARE = 5;
    static final int PLAYER2_CIRCLE = 6;
    static final int PLAYER2_TRIANGLE = 7;
    static final int PLAYER2_RHOMBUS = 8;
    int[][] board;
    Set<Point> squareMoves,triangleMoves,circleMoves;
    int playerTurn;
    int DOWNWARDS = -1;
    int UPWARDS = 1;
    public PlayboardModel(Playboard pb, int turn) {
        board = getBoard(pb);
        int direction;
        if (turn == 0)
            direction = DOWNWARDS;
        else
            direction = UPWARDS;
        Piece sq = new Square(null,direction);
        squareMoves = sq.getMoves();
        sq = new Circle(null,direction);
        circleMoves = sq.getMoves();
        sq = new Triangle(null,direction);
        triangleMoves = sq.getMoves();
        playerTurn = turn;
    }

    private PlayboardModel(int[][] pb, int turn) {
        board = pb.clone();
        int direction;
        if (turn == 0)
            direction = DOWNWARDS;
        else
            direction = UPWARDS;
        Piece sq = new Square(null,direction);
        squareMoves = sq.getMoves();
        sq = new Circle(null,direction);
        circleMoves = sq.getMoves();
        sq = new Triangle(null,direction);
        triangleMoves = sq.getMoves();
        playerTurn = turn;
    }
    @Override
    public PlayboardModel clone() {
        return new PlayboardModel(board, playerTurn);
    }

    public int[][] getBoard(Playboard pb) {
        Color player1Color = Settings.getPlayers().get(0).getColor();
        Tile[][] tiles = pb.getTiles();
        int[][] intPlayboard = new int[Settings.getPlayboardSize()][Settings.getPlayboardSize()];
        for (int i = 0; i < Settings.getPlayboardSize(); i++) {
            for (int j = 0; j < Settings.getPlayboardSize(); j++) {
                Piece p = tiles[i][j].getPiece();
                if (p == null) {
                    intPlayboard[i][j] = EMPTY_TILE;
                } else if (p instanceof Square) {
                    if (p.getColor().equals(player1Color)) {
                        intPlayboard[i][j] = PLAYER1_SQUARE;
                    } else {
                        intPlayboard[i][j] = PLAYER2_SQUARE;
                    }
                } else if (p instanceof Circle) {
                    if (p.getColor().equals(player1Color)) {
                        intPlayboard[i][j] = PLAYER1_CIRCLE;
                    } else {
                        intPlayboard[i][j] = PLAYER2_CIRCLE;
                    }
                } else if (p instanceof Triangle) {
                    if (p.getColor().equals(player1Color)) {
                        intPlayboard[i][j] = PLAYER1_TRIANGLE;
                    } else {
                        intPlayboard[i][j] = PLAYER2_TRIANGLE;
                    }
                }
            }
        }

        return intPlayboard;
    }
    //ROUGHLY TESTED!

    public List<Move> getAllPossibleMoves(int player) {
        ArrayList<Point> pieces = getYourPieces(player);
        ArrayList<Move> allTheMoves = new ArrayList<Move>();
        for (Point p : pieces) {
            for (Point i : possibleMovesFromPosition(p)) {
                allTheMoves.add(new Move(p, i));
            }
        }

        return allTheMoves;
    }
    //DONE!

    private ArrayList<Point> getYourPieces(int player) {
        int piece;
        ArrayList<Point> yourPieces = new ArrayList<Point>();
        for (int x = 0; x < Settings.getPlayboardSize(); x++) {
            for (int y = 0; y < Settings.getPlayboardSize(); y++) {
                piece = board[x][y];
//                if (piece == 6 && player == 1)
//                    System.out.println((player == 1 && piece >= 5));
                if ((player == 0 && piece > 0 && piece < 5) || (player == 1 && piece >= 5)) {
                    yourPieces.add(new Point(x, y));
                }
            }
        }
        return yourPieces;
    }
    //ROUGHLY TESTED!

    private Set<Point> possibleMovesFromPosition(Point pos) {
        HashSet<Point> possibleMoves = new HashSet<Point>();
        Set<Point> pieceMoves;
        int piece = board[pos.x][pos.y];
        if (piece == PLAYER1_SQUARE || piece == PLAYER2_SQUARE) {
            pieceMoves = squareMoves;
        }
        else if (piece == PLAYER1_CIRCLE || piece == PLAYER2_CIRCLE) {
            pieceMoves = circleMoves;
        }
        else if (piece == PLAYER1_TRIANGLE || piece == PLAYER2_TRIANGLE) {
            pieceMoves = triangleMoves;
        }
        else{
            pieceMoves = new HashSet<Point>();
        }
        for (Point diffPoint : pieceMoves) {
                Point temp = new Point(pos.x + diffPoint.x, pos.y + diffPoint.y);
            if (pointOnBoard(temp)) {
                if (board[temp.x][temp.y] == EMPTY_TILE) {
                    possibleMoves.add(temp);
                }
            }
        }
        //if(piece != EMPTY_TILE){
            jumping(pos, pieceMoves, possibleMoves);
            possibleMoves.remove(pos);
        //}

             HashSet<Point> removePoints = new HashSet<Point>();
        for(int j=0;j<Settings.getPlayboardSize();j++){
            removePoints.add(new Point(0, j));
            removePoints.add(new Point(Settings.getPlayboardSize()-1, j));

        }
        possibleMoves.removeAll(removePoints);
        return possibleMoves;
    }
    //DONE!

    private boolean pointOnBoard(Point point) {
        if (point.x >= 0 && point.x < Settings.getPlayboardSize()) {
            if (point.y >= 0 && point.y < Settings.getPlayboardSize()) {
                return true;
            }
        }
        return false;
    }
    //ROUGHLY TESTED!

    private void jumping(Point start, Set<Point> pieceMoves, HashSet<Point> possibleMoves) {
        if (pointOnBoard(start)) {
            possibleMoves.add(start);
            for (Point diffPoint : pieceMoves) {
                Point posOfPiece = new Point(start.x + diffPoint.x, start.y + diffPoint.y);
                if (pointOnBoard(posOfPiece)) {
                    if (board[posOfPiece.x][posOfPiece.y] != EMPTY_TILE) {
                        Point posAfterJump = new Point(start.x + diffPoint.x + diffPoint.x, start.y + diffPoint.y + diffPoint.y);
                        if (pointOnBoard(posAfterJump)) {
                            if (board[posAfterJump.x][posAfterJump.y] == EMPTY_TILE) {
                                if (!possibleMoves.contains(posAfterJump)) {
                                    jumping(posAfterJump, pieceMoves, possibleMoves);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int getBoardFitness() throws RuntimeException {
        //int fitness = boardBaseFitness(board);
        int fitness = 50;
        for (int x = 0; x < Settings.getPlayboardSize(); x++) {
            for (int y = 0; y < Settings.getPlayboardSize(); y++) {
                int p = board[x][y];
                if (p != EMPTY_TILE) {
                    if (p > 0 && p < 5) {
                        fitness += y;
                        if (y == Settings.getPlayboardSize() - 1) {
                            //fitness += 100;
                            //System.out.println(fitness);
                        }
                    } else {
                        fitness -= (Settings.getPlayboardSize() - 1 - y);
                        if (y == 0) {
                            //fitness -= 100;
                        }
                    }
                }
            }
        }
        int winner = checkWin();
        if (winner > 0) {
            fitness = 10000;
        } else if (winner < 0) {
            fitness = -10000;
        }
        return fitness;
    }

    public int checkWin() {
        int win = 0;
        for (int i = 0; i < Settings.getPlayers().size(); i++) {
            if (i == 0) {

                boolean goalIsFull = true;
                for (int j = 0; Settings.getPlayboardSize() > j; j++) {//kollar om målet är fullt med saker

                    if (board[j][Settings.getPlayboardSize() - 1] == EMPTY_TILE) {
                        goalIsFull = false;
                    }
                }

                if (goalIsFull) {
                    win = 1;
                    for (Point point : getYourPieces(i)) {
                        if (point.y != Settings.getPlayboardSize() - 1 && point.y != Settings.getPlayboardSize() - 2) {
                            win = 0;
                            break;
                        }
                    }
                    if (win == 1) {
                        //System.out.println("player 1 won");
                        return win;
                    }

                }
            } else if (i == 1) {

                boolean goalIsFull = true;
                for (int j = 0; Settings.getPlayboardSize() > j; j++) {//kollar om målet är fullt med saker
                    if (board[j][0] == EMPTY_TILE) {
                        goalIsFull = false;
                    }
                }

                if (goalIsFull) {

                    win = -1;
                    for (Point point : getYourPieces(i)) {
                        if (point.y != 0 && point.y != 1) {
                            win = 0;
                            break;
                        }
                    }
                    if (win == -1) {
                        return win;
                    }

                }
            }
        }
        return 0;
    }


    public void movePiece(Point from, Point to) {
        int origin = board[from.x][from.y];
        board[from.x][from.y] = EMPTY_TILE;
        board[to.x][to.y] = origin;
        changeTurn();
    }

    private void changeTurn() {
        if (playerTurn == 1) {
            playerTurn = 0;
        } else {
            playerTurn = 1;
        }
    }

    public int getTurn() {
        return playerTurn;
    }

    void movePiece(Move m) {
        movePiece(m.getStart(), m.getEnd());
    }
}
