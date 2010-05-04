/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package minmax;

import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mushai.Playboard;
import mushai.Model;
import mushai.Move;
/**
 *
 * @author bark
 */
public class minmaxtree {


public int findBestOfMyMoves(Playboard board,int deap){
   int bestMove=-1000;
    if(deap==0){
            try {
                return Model.getBoardFitness(board);
            } catch (Exception ex) {
                Logger.getLogger(minmaxtree.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    for(Move move:Model.getAllPossibleMoves(board)){
        int temp= findBestOfEnemysMoves(Model.movePiece(board, move),deap-1);
       if( temp>bestMove){
           bestMove=temp;
       }

    }
    return bestMove;
}
public int findBestOfEnemysMoves(Playboard board,int deap){
    return 0;

}



}
