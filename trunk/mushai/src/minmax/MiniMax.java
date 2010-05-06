/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minmax;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mushai.Controller;
import mushai.Playboard;
import mushai.Model;
import mushai.Move;
import mushai.Player;
import mushai.Settings;

/**
 *
 * @author bark
 */
public class MiniMax {

    private Controller controller;
    private Playboard originalPlayboard;
    private PlayboardModel playboard;

    public MiniMax(Controller c, Playboard pb) {
        controller = c;
        originalPlayboard = pb;
    }

    public Move findBestMove(int depth) {
        int turn = Model.whoseTurnIsIt();
        playboard = new PlayboardModel(originalPlayboard, turn);
        MoveAndFitness maf = findBestMove(depth, null);
        Move move = maf.move;
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
        if (lastMove != null)
            //System.out.println("after move: " + lastMove + " fitness is: " + playboard.getBoardFitness());
        if (depth == 0 || playboard.getBoardFitness() > 1000 || playboard.getBoardFitness() == 0) {
            try {
                return new MoveAndFitness(lastMove, playboard.getBoardFitness());
            } catch (Exception ex) {
                Logger.getLogger(MiniMax.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int bestValue, value, turn;
        Move bestMove;
        turn = Model.whoseTurnIsIt();
        List<Move> possibleMoves = playboard.getAllPossibleMoves(turn);
        if (possibleMoves.isEmpty()) {
            throw new RuntimeException("CRITICAL ERROR EXCEPTION! NO POSSIBLE MOVES :(((((");
        }

        Move firstMove = possibleMoves.remove(0);
        playboard.movePiece(firstMove.getStart(), firstMove.getEnd());
        //if (lastMove!=null)System.out.println("LAST: "+lastMove.getStart() + " ---> " + lastMove.getEnd() + ":::" + Model.getBoardFitness(playboard));
        //System.out.println(firstMove.getStart() + " ---> " + firstMove.getEnd() + ":::" + Model.getBoardFitness(playboard));
        MoveAndFitness maf = findBestMove(depth - 1, firstMove);
        bestValue = maf.fitness;
        playboard.movePiece(firstMove.getEnd(), firstMove.getStart());
        if (maf.fitness > 1000 || maf.fitness == 0) {
            //return new MoveAndFitness(firstMove, maf.fitness);
        }
        bestMove = firstMove;
        for (Move move : possibleMoves) {
            //if (depth == 4) System.out.println(move.getStart() + "--->"+ move.getEnd());
            //domove
            playboard.movePiece(move.getStart(), move.getEnd());
            maf = findBestMove(depth - 1, move);
            value = maf.fitness;
            //undomove
            playboard.movePiece(move.getEnd(), move.getStart());
            if (maf.fitness > 1000 || maf.fitness == 0) {
                //return new MoveAndFitness(move, maf.fitness);
            }
            if (turn == 0) {
                if (value > bestValue) {
                    //if (lastMove!=null)System.out.println("LAST: "+lastMove.getStart() + " ---> " + lastMove.getEnd() + ":::" + Model.getBoardFitness(playboard));
                    //System.out.println(move.getStart() + " ---> " + move.getEnd() + ":::" + Model.getBoardFitness(playboard));
                    bestValue = value;
                    bestMove = move;
                    //System.out.println("depth: " + depth + " value: " + bestValue);
                }
            } else if (turn == 1) {
                if (value < bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            }
        }
        return new MoveAndFitness(bestMove, bestValue);
    }

    /*private MoveAndFitness findBestMovebyBreadth(int depth, Move lastMove) {
        if (depth == 0 || Model.getBoardFitness(playboard) > 1000 || Model.getBoardFitness(playboard) == 0) {
            try {
                return new MoveAndFitness(lastMove, Model.getBoardFitness(playboard));
            } catch (Exception ex) {
                Logger.getLogger(MiniMax.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int bestValue, value, turn;
        Move bestMove;
        turn = Model.whoseTurnIsIt();
        List<Move> possibleMoves = Model.getAllPossibleMoves(playboard);
        if (possibleMoves.isEmpty()) {
            throw new RuntimeException("CRITICAL ERROR EXCEPTION! NO POSSIBLE MOVES :(((((");
        }
        
        LinkedList<Move> queue = new LinkedList<Move>();
        for (Move move : possibleMoves){
            queue.add(move);
        }
        Move nextMove = queue.removeFirst();
        controller.move(nextMove);



        Move firstMove = possibleMoves.remove(0);
        controller.move(firstMove);
        //if (lastMove!=null)System.out.println("LAST: "+lastMove.getStart() + " ---> " + lastMove.getEnd() + ":::" + Model.getBoardFitness(playboard));
        //System.out.println(firstMove.getStart() + " ---> " + firstMove.getEnd() + ":::" + Model.getBoardFitness(playboard));
        MoveAndFitness maf = findBestMovebyBreadth(depth - 1, firstMove);
        bestValue = maf.fitness;
        Model.movePiece(playboard, firstMove.getEnd(), firstMove.getStart());
        controller.changePlayer();
        if (maf.fitness > 1000 || maf.fitness == 0) {
            //return new MoveAndFitness(firstMove, maf.fitness);
        }
        bestMove = firstMove;
        for (Move move : possibleMoves) {
            //if (depth == 4) System.out.println(move.getStart() + "--->"+ move.getEnd());
            //domove
            controller.move(move);
            maf = findBestMovebyBreadth(depth - 1, move);
            value = maf.fitness;
            //undomove
            Model.movePiece(playboard, move.getEnd(), move.getStart());
            controller.changePlayer();
            if (maf.fitness > 1000 || maf.fitness == 0) {
                //return new MoveAndFitness(move, maf.fitness);
            }
            if (turn == 0) {
                if (value > bestValue) {
                    //if (lastMove!=null)System.out.println("LAST: "+lastMove.getStart() + " ---> " + lastMove.getEnd() + ":::" + Model.getBoardFitness(playboard));
                    //System.out.println(move.getStart() + " ---> " + move.getEnd() + ":::" + Model.getBoardFitness(playboard));
                    bestValue = value;
                    bestMove = move;
                    //System.out.println("depth: " + depth + " value: " + bestValue);
                }
            } else if (turn == 1) {
                if (value < bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            }
        }
        return new MoveAndFitness(bestMove, bestValue);
    }
    private MoveAndFitness negaMax(int depth, Move lastMove) {
        if (depth == 0 || Model.getBoardFitness(playboard) > 1000 || Model.getBoardFitness(playboard) == 0) {
            //System.out.println(Model.getBoardFitness(playboard));
            try {
                return new MoveAndFitness(lastMove, Model.getBoardFitness(playboard));
            } catch (Exception ex) {
                Logger.getLogger(MiniMax.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int bestValue, value, turn;
        Move bestMove;
        turn = Model.whoseTurnIsIt();
        List<Move> possibleMoves = Model.getAllPossibleMoves(playboard);
        if (possibleMoves.isEmpty()) {
            throw new RuntimeException("CRITICAL ERROR EXCEPTION! NO POSSIBLE MOVES :(((((");
        }
        bestValue = -1337;
        bestMove = null;
        MoveAndFitness maf;

        for (Move move : possibleMoves) {
            //domove
            controller.move(move);

            maf = negaMax(depth - 1, move);
            value = maf.fitness;
            if (turn == 1) {
                value = -value;
            }
            //undomove
            Model.movePiece(playboard, move.getEnd(), move.getStart());
            controller.changePlayer();
            System.out.println("value: " + value + " bestValue: " + bestValue);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        if (bestMove == null) {
            System.out.println("OOOOOOOH NOOOOOOOOOOOOO!!!!!!!");
        }
        System.out.println(possibleMoves.get(0));
        System.out.println(bestValue);
        return new MoveAndFitness(lastMove, bestValue);
    }*/

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
}
