/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package minmax;

import java.awt.Color;
import mushai.*;

/**
 *
 * @author Hasse
 */
public class TestMiniMax {
    public static void main(String[] args){
        Playboard pb = new Playboard(1, 1);
        Controller c = new Controller(pb);
        Player player = new Player("0", Color.yellow);
        player.setMyTurn(true);
        Settings.addPlayer(player);
        Settings.addPlayer(new Player("1", Color.blue));
        
        MiniMax m = new MiniMax(c,pb);
        Move bestMove = m.findBestMove(3);
        System.out.println(bestMove.getStart());
        System.out.println(bestMove.getEnd());
        try{
            System.out.println(pb);
        } catch(Exception ex){

        }
        Model.movePiece(pb, bestMove);
        try{
            System.out.println(pb);
        } catch(Exception ex){

        }
        bestMove = m.findBestMove(3);
        Model.movePiece(pb, bestMove);
        System.out.println(pb);
    }

}
