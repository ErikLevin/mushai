/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minmax;

import java.util.logging.Level;
import java.util.logging.Logger;
import mushai.Playboard;
import mushai.Model;
import mushai.Move;

/**
 *
 * @author bark
 */
public class Minmaxtree {

    


    public int findBestOfMyMoves(Playboard board, int deap) {
        int bestMove = 0;
        if (deap == 0) {
            try {
                return Model.getBoardFitness(board);
            } catch (Exception ex) {
                Logger.getLogger(Minmaxtree.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(Minmaxtree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Move move : Model.getAllPossibleMoves(board)) {
            try {
                int temp = Model.getBoardFitness(Model.movePiece(board, move));
                if (temp == lowestPoint) {
                    return findBestOfMyMoves(Model.movePiece(board, move), deap);

                }
            } catch (Exception ex) {
                Logger.getLogger(Minmaxtree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }
}
