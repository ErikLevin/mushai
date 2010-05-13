package minmax;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import mushai.Controller;
import mushai.Playboard;
import mushai.Model;
import mushai.Move;
import mushai.Player;
import mushai.Settings;

/**
 *
 * @author MushAI
 */
public class MiniMax {

    private Controller controller; //Not used??
    private Playboard originalPlayboard;
    private PlayboardModel playboard;

    public MiniMax(Controller c, Playboard pb) {
        controller = c;
        originalPlayboard = pb;
    }

    public MiniMax(PlayboardModel board) {
        playboard = board;
    }
    public Move findEndGameMove(int depth) {
        int turn = Model.whoseTurnIsIt();
        playboard = new PlayboardModel(originalPlayboard, turn);
        MoveAndFitness maf;
        if (playboard.endGameSituation(turn))
            maf = findEndGameMove(depth+1, null);
        else
            maf = findBestMove(depth, null);
        Move move = maf.move;
        //Move move = breadthFirst(depth);
//        Move move = minimaxDecision(depth, playboard);

        ArrayList<Player> arL = Settings.getPlayers();
        if (turn == 0) {
            arL.get(0).setMyTurn(true);
            arL.get(1).setMyTurn(false);
        } else if (turn == 1) {
            arL.get(0).setMyTurn(false);
            arL.get(1).setMyTurn(true);
        }
        return move;
    }
    public Move findBestMove(int depth) {
        int turn = Model.whoseTurnIsIt();
        playboard = new PlayboardModel(originalPlayboard, turn);
        
        MoveAndFitness maf = findBestMove(depth, null);
        Move move = maf.move;
        if (maf.fitness > 1000) {
            System.out.println("ZOMG 100000 fitness!!!!");
        }
        //Move move = breadthFirst(depth);
//        Move move = minimaxDecision(depth, playboard);

        ArrayList<Player> arL = Settings.getPlayers();
        if (turn == 0) {
            arL.get(0).setMyTurn(true);
            arL.get(1).setMyTurn(false);
        } else if (turn == 1) {
            arL.get(0).setMyTurn(false);
            arL.get(1).setMyTurn(true);
        }

        return move;
    }

    private MoveAndFitness findBestMove(int depth, Move lastMove) {
        if (lastMove != null) //System.out.println("after move: " + lastMove + " fitness is: " + playboard.getUtility());
        {
            if (depth == 0 || playboard.getUtility() > 1000 || playboard.getUtility() < -1000) {
                try {
                    return new MoveAndFitness(lastMove, playboard.getUtility());
                } catch (Exception ex) {
                    Logger.getLogger(MiniMax.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        int bestValue, value, turn;
        Move bestMove;
        turn = Model.whoseTurnIsIt();
        List<Move> possibleMoves = playboard.getAllPossibleMoves(turn);
        int noOfPossibleMoves = possibleMoves.size();
        if (possibleMoves.isEmpty()) {
            throw new RuntimeException("CRITICAL ERROR EXCEPTION! NO POSSIBLE MOVES :(((((");
        }

        Move firstMove = possibleMoves.remove(0);
        playboard.movePiece(firstMove.getStart(), firstMove.getEnd());
        MoveAndFitness maf = findBestMove(depth - 1, firstMove);
        bestValue = maf.fitness;
        playboard.movePiece(firstMove.getEnd(), firstMove.getStart());
        bestMove = firstMove;
        double randomValue;
        Random randomizer = new Random();
        for (Move move : possibleMoves) {
            //if (depth == 4) System.out.println(move.getStart() + "--->"+ move.getEnd());
            //domove
            playboard.movePiece(move.getStart(), move.getEnd());
            maf = findBestMove(depth - 1, move);
            value = maf.fitness;
            //undomove
            playboard.movePiece(move.getEnd(), move.getStart());
            if (value == -10000 || value == 10000) {
                if (turn == 0) {
                    value += depth;
                    maf.fitness += depth;
                } else {
                    value -= depth;
                    maf.fitness -= depth;
                }
            }
            randomValue = randomizer.nextDouble();
            if (turn == 0) {
                if (value >= bestValue) {
                    if (!(value == bestValue && randomValue > (1/noOfPossibleMoves))) {
                        bestValue = value;
                        bestMove = move;
                    }
                }
            } else if (turn == 1) {
                if (value <= bestValue) {
                    if (!(value == bestValue && randomValue > (1/noOfPossibleMoves))) {
                        bestValue = value;
                        bestMove = move;
                    }
                }
            }
        }
        return new MoveAndFitness(bestMove, bestValue);
    }

    private MoveAndFitness findEndGameMove(int depth, Move lastMove) {
        int turn = Model.whoseTurnIsIt();
        if (depth == 0 || playboard.getEndGameFitness(turn) > 1000) {
            try {
                return new MoveAndFitness(lastMove, playboard.getEndGameFitness(turn));
            } catch (Exception ex) {
                Logger.getLogger(MiniMax.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int bestValue, value;
        Move bestMove;
        List<Move> possibleMoves = playboard.getAllPossibleMoves(turn);
        if (possibleMoves.isEmpty()) {
            throw new RuntimeException("CRITICAL ERROR EXCEPTION! NO POSSIBLE MOVES :(((((");
        }

        Move firstMove = possibleMoves.remove(0);
        playboard.movePiece(firstMove.getStart(), firstMove.getEnd());
        playboard.movePiece(firstMove.getStart(), firstMove.getStart());
        MoveAndFitness maf = findEndGameMove(depth - 1, firstMove);
        bestValue = maf.fitness;
        playboard.movePiece(firstMove.getStart(), firstMove.getStart());
        playboard.movePiece(firstMove.getEnd(), firstMove.getStart());
        bestMove = firstMove;
        double randomValue;
        double bestRandomValue = 0;
        Random randomizer = new Random();
        for (Move move : possibleMoves) {
            //domove
            playboard.movePiece(move.getStart(), move.getEnd());
            //random move from opponent
            playboard.movePiece(move.getStart(), move.getStart());

            maf = findEndGameMove(depth - 1, move);
            value = maf.fitness;
            //undomove
            playboard.movePiece(move.getStart(), move.getStart());
            playboard.movePiece(move.getEnd(), move.getStart());
            if (value == 10000) {
                value += depth;
                maf.fitness += depth;
            }
            randomValue = randomizer.nextDouble();
            if (value >= bestValue) {
                if (!(value == bestValue && randomValue > bestRandomValue)) {
                    bestValue = value;
                    bestMove = move;
                    bestRandomValue = randomValue;
                }
            }
        }
        return new MoveAndFitness(bestMove, bestValue);
    }

    

    private class MoveAndFitness {

        Move move;
        int fitness;
        int goalDepth;

        public MoveAndFitness(Move m, int f) {
            move = m;
            fitness = f;
        }

        public MoveAndFitness(Move m, int f, int gd) {
            move = m;
            fitness = f;
            goalDepth = gd;
        }
    }
    private class MoveAndPrevMoves {

        Move move;
        LinkedList<Move> prevMoves;

        public MoveAndPrevMoves(Move m, LinkedList<Move> pm) {
            prevMoves = pm;
            move = m;
        }
    }
}
