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

    public MiniMax(Controller c, Playboard pb){
        controller = c;
        playboard = pb;
    }
    public Move findBestMove(int depth){
        int turn = Model.whoseTurnIsIt();
        
        Move move = findBestMove(depth,null).move;

        ArrayList<Player> arL = Settings.getPlayers();
        if (turn == 0){
            arL.get(0).setMyTurn(false);
            arL.get(1).setMyTurn(true);
        }
        else if (turn == 1){
            arL.get(0).setMyTurn(true);
            arL.get(1).setMyTurn(false);
        }
        return move;
    }
    private MoveAndFitness findBestMove(int depth, Move lastMove){
        if (depth == 0){
            try {
                return new MoveAndFitness(lastMove,Model.getBoardFitness(playboard));
            } catch (Exception ex) {
                Logger.getLogger(MiniMax.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int bestValue, value, turn;
        Move bestMove;
        turn = Model.whoseTurnIsIt();
        List<Move> possibleMoves = Model.getAllPossibleMoves(playboard);
        if (possibleMoves.isEmpty())
            throw new RuntimeException("CRITICAL ERROR EXCEPTION! NO POSSIBLE MOVES :(((((");

        Move firstMove = possibleMoves.remove(0);
        controller.move(firstMove);
        MoveAndFitness maf = findBestMove(depth-1,firstMove);
        bestValue = maf.fitness;
        Model.movePiece(playboard, firstMove.getEnd(), firstMove.getStart());
        bestMove = firstMove;

        for (Move move : possibleMoves){
            //domove
            controller.move(move);
            maf = findBestMove(depth-1,move);
            value = maf.fitness;
            //undomove
            Model.movePiece(playboard, move.getEnd(), move.getStart());
            if (turn == 0){
                if (value > bestValue){
                    bestValue = value;
                    bestMove = move;
                }
            }
            else if (turn == 1){
                if (value < bestValue){
                    bestValue = value;
                    bestMove = move;
                }
            }
        }
        return new MoveAndFitness(bestMove,bestValue);
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
    
    private class MoveAndFitness{
        Move move;
        int fitness;
        public MoveAndFitness(Move m, int f){
            move = m;
            fitness = f;
        }
    }
}