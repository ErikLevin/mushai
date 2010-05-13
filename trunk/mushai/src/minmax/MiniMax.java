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

    private Playboard originalPlayboard;
    private PlayboardModel playboard;

    public MiniMax(Playboard pb) {
        originalPlayboard = pb;
    }

    public MiniMax(PlayboardModel board) {
        playboard = board;
    }

    public Move findBestMove(int depth) {
        int turn = Model.whoseTurnIsIt();
        playboard = new PlayboardModel(originalPlayboard, turn);
        MoveAndFitness maf;
        Move move = null;
        if (playboard.endGameSituation(turn)) {
            move = findEndGameMove(depth - 1);
        } else {
            maf = findBestMove(depth, null);
            move = maf.move;
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
                    if (!(value == bestValue && randomValue > (1 / noOfPossibleMoves))) {
                        bestValue = value;
                        bestMove = move;
                    }
                }
            } else if (turn == 1) {
                if (value <= bestValue) {
                    if (!(value == bestValue && randomValue > (1 / noOfPossibleMoves))) {
                        bestValue = value;
                        bestMove = move;
                    }
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

    private Move findEndGameMove(int depth) {
        int currDepth = 0;
        int fitness = playboard.getUtility();
        int bestFitness = 0;
        int requestTurn = Model.whoseTurnIsIt();
        if (requestTurn == 1) {
            bestFitness = 0;
        }
        LinkedList<MoveAndPrevMoves> queue = new LinkedList<MoveAndPrevMoves>();
        for (Move m : playboard.getAllPossibleMoves(requestTurn)) {
            queue.add(new MoveAndPrevMoves(m, new LinkedList<Move>()));
        }
        Move move, bestMove, temp;
        bestMove = null;
        LinkedList<Move> prevMoves = new LinkedList<Move>();
        LinkedList<Move> prevClone = new LinkedList<Move>();
        while (currDepth <= depth) {
            if (queue.isEmpty()) {
                System.out.println("reached empty, maybe correct");
                System.out.println(currDepth);
                break;
            }
            MoveAndPrevMoves mapm = queue.pop();
            move = mapm.move;

            prevMoves = mapm.prevMoves;
            currDepth = prevMoves.size();
            //System.out.println(currDepth + " " + move);
            //doallmoves
            for (Move m : prevMoves) {
                playboard.movePiece(m.getStart(), m.getEnd());
            }
            playboard.movePiece(move.getStart(), move.getEnd());

            fitness = playboard.getEndGameFitness(requestTurn);

            if (fitness > bestFitness) {
                bestFitness = fitness;
                if (prevMoves.isEmpty()) {
                    bestMove = move;
                } else {
                    bestMove = prevMoves.getFirst();
                }
            }
            if (playboard.checkWin() != 0) {
                System.out.println("possible win! should break! " + currDepth + " " + bestMove);
                for (Move m : prevMoves) {
                    System.out.println(m);
                }
                break;
            }

            prevClone = new LinkedList<Move>();
            for (Move m : prevMoves) {
                prevClone.add(m);
            }
            prevClone.add(move);
            if (currDepth < depth) {
                for (Move m : playboard.getAllPossibleMoves(requestTurn)) {
                    queue.add(new MoveAndPrevMoves(m, prevClone));
                }
            }
            playboard.movePiece(move.getEnd(), move.getStart());
            for (int i = prevMoves.size() - 1; i >= 0; i--) {
                temp = prevMoves.get(i);
                playboard.movePiece(temp.getEnd(), temp.getStart());
            }
        }
        if (bestMove == null) {
            System.out.println("RETURNING NULL AS BEST MOVE!");
        }
        return bestMove;
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
