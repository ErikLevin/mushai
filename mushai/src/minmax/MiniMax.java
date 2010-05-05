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
    private Playboard playboard;

    public MiniMax(Controller c, Playboard pb) {
        controller = c;
        playboard = pb;
    }

    public Move findBestMove(int depth) {
        int turn = Model.whoseTurnIsIt();
        MoveAndFitness maf = findBestMove(depth, null);
        Move move = maf.move;
        //System.out.println(maf.fitness);
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
        if (depth == 0 || Model.getBoardFitness(playboard) > 1000 || Model.getBoardFitness(playboard) == 0) {
            //System.out.println(Model.getBoardFitness(playboard));
            if (lastMove == null) System.out.println("HORRIBLE");
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

        Move firstMove = possibleMoves.remove(0);
        controller.move(firstMove);
        //if (lastMove!=null)System.out.println("LAST: "+lastMove.getStart() + " ---> " + lastMove.getEnd() + ":::" + Model.getBoardFitness(playboard));
        //System.out.println(firstMove.getStart() + " ---> " + firstMove.getEnd() + ":::" + Model.getBoardFitness(playboard));
        MoveAndFitness maf = findBestMove(depth - 1, firstMove);
        bestValue = maf.fitness;
        Model.movePiece(playboard, firstMove.getEnd(), firstMove.getStart());
        controller.changePlayer();
        if (maf.fitness > 1000 || maf.fitness == 0) {
            //return new MoveAndFitness(firstMove, maf.fitness);
        }
        bestMove = firstMove;
        for (Move move : possibleMoves) {
            if (depth == 4) System.out.println(move.getStart() + "--->"+ move.getEnd());
            //domove
            controller.move(move);
            //Model.movePiece(playboard, move.getStart(), move.getEnd()); controller.changePlayer();
            
            maf = findBestMove(depth - 1, move);
            value = maf.fitness;
            if (depth == 4) System.out.println(value);
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

    public int findBestOfMyMoves(Playboard board, int deap) {
        int bestMove = 0;
        if (deap == 0) {
            try {
                return Model.getBoardFitness(board);
            } catch (Exception ex) {
                Logger.getLogger(MiniMax.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Move move : Model.getAllPossibleMoves(board)) {
            int temp = findBestOfEnemysMoves(Model.movePiece(board, move), deap - 1);
            if (temp > bestMove) {
                bestMove = temp;
            }

        }
        return bestMove;
    }

    public int findBestOfEnemysMoves(Playboard board, int deap) {
        int lowestPoint = +1000;
        for (Move move : Model.getAllPossibleMoves(board)) {
            int temp;
            try {
                temp = Model.getBoardFitness(Model.movePiece(board, move));
                if (temp < lowestPoint) {
                    lowestPoint = temp;
                }
            } catch (Exception ex) {
                Logger.getLogger(MiniMax.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Move move : Model.getAllPossibleMoves(board)) {
            try {
                int temp = Model.getBoardFitness(Model.movePiece(board, move));
                if (temp == lowestPoint) {
                    return findBestOfMyMoves(Model.movePiece(board, move), deap);

                }
            } catch (Exception ex) {
                Logger.getLogger(MiniMax.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }

    private class MoveAndFitness {

        Move move;
        int fitness;

        public MoveAndFitness(Move m, int f) {
            move = m;
            fitness = f;
        }
    }
}
