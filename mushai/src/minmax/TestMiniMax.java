/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package minmax;

import java.awt.Color;
import java.util.List;
import mushai.*;

/**
 *
 * @author Hasse
 */
public class TestMiniMax {
    public static void main(String[] args){

        Player player = new Player("0", Color.yellow);
        player.setMyTurn(true);
        Settings.addPlayer(player);
        Settings.addPlayer(new Player("1", Color.blue));
        Playboard pb = new Playboard(4, 4, 6);
        Controller c = new Controller(pb);
        MiniMax m = new MiniMax(c,pb);
        List<Move> moves;
        for (int i = 0; i < 50; i++){
            if (i%2 == 0){
                System.out.println("-------------------");
                System.out.println("Round: " + (1+(i/2)));
                Move bestMove = m.findBestMove(1);
                c.move(bestMove);
            } else{
                Move bestMove = m.findBestMove(4);
                c.move(bestMove);
                
                /*moves = Model.getAllPossibleMoves(pb);
                int randomMove = (int)Math.round(Math.random()*(moves.size()-1));
                c.move(moves.get(randomMove));*/
            }
            System.out.println(pb);
            System.out.println("Fitness after move: " + Model.getBoardFitness(pb));
            System.out.println("\n\n");
            if(Model.getBoardFitness(pb) > 1000 || Model.getBoardFitness(pb) == 0)
                break;
        }
    }

}
