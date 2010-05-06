/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class PlayboardModel {

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
    Set<Point> squareMoves;
    int playerTurn;

    public PlayboardModel(Playboard pb, int turn) {
        board = getBoard(pb);

        Square sq = new Square(null);
        squareMoves = sq.getMoves();
        playerTurn = turn;
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
        int piece = board[pos.x][pos.y];
        if (piece == PLAYER1_SQUARE || piece == PLAYER2_SQUARE) {
            for (Point diffPoint : squareMoves) {
            Point temp = new Point(pos.x + diffPoint.x, pos.y + diffPoint.y);
                if (pointOnBoard(temp)) {
                    if (board[temp.x][temp.y] == EMPTY_TILE) {
                        possibleMoves.add(temp);
                    }
                }
            }
            jumping(pos, squareMoves, possibleMoves);
        }
        possibleMoves.remove(pos);
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
            fitness = 0;
        }
        if (fitness < 0) {
            throw new RuntimeException("Fitness for board was negative\n" + board.toString());
        }
        return fitness;
    }

    public int checkWin() {
        int win = 0;
        for (int i = 0; i <= Settings.getPlayers().size(); i++) {
            if (i == 0) {
                win = 1;
                for (Point point : getYourPieces(i)) {
                    if (point.y != Settings.getPlayboardSize() - 1) {
                        win = 0;
                        break;
                    }
                }
                if (win == 1) {
                    //System.out.println("player 1 won");
                    return win;
                }

            }
            if (i == 1) {
                win = -1;
                for (Point point : getYourPieces(i)) {
                    if (point.y != 0) {
                        win = 0;
                        break;
                    }
                }
                if (win == -1) {
                    //System.out.println("player 2 won");
                    return win;
                }
            }
        }
        return win;
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
}
