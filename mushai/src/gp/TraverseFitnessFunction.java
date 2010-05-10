package gp;

import java.io.Serializable;
import minmax.MiniMax;
import minmax.PlayboardModel;
import mushai.Controller;
import mushai.Playboard;
import mushai.Settings;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

class TraverseFitnessFunction extends GPFitnessFunction implements Serializable {

    private Controller controller;
    private PlayboardModel board;
    int[][] boardSetup;
    private MiniMax minimax;
    private int noPlayer1Pieces, noPlayer2Pieces;

    public TraverseFitnessFunction(int noPlayer1Pieces, int noPlayer2Pieces) {
        this(new Playboard(noPlayer1Pieces, noPlayer2Pieces), noPlayer1Pieces, noPlayer2Pieces);
    }

    TraverseFitnessFunction(Playboard board, int player1Pieces, int player2Pieces) {
        this(new PlayboardModel(board, 0), player1Pieces, player2Pieces);
    }

    TraverseFitnessFunction(PlayboardModel board, int player1Pieces, int player2Pieces) {
        this.board = board;
        boardSetup = board.getBoard().clone();
        this.noPlayer1Pieces = player1Pieces;
        this.noPlayer2Pieces = player2Pieces;
        this.minimax = new MiniMax(board);
    }

    @Override
    protected double evaluate(IGPProgram individual) {
        board = new PlayboardModel(boardSetup, noPlayer1Pieces); // Recreate original board.
        int fitness = 0;

        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {
//                System.out.println("-------------------");
//                System.out.println("Round: " + (1 + (i / 2)));
                individual.execute_void(0, new Object[]{board});
            } else { //Random player
                controller.move(minimax.findBestMove(1));
            }

            fitness = board.getFitness();

//            System.out.println(board);
//            System.out.println("Fitness after move: " + fitness);
//            System.out.println("\n\n");

            if (fitness > 1000 || fitness < -1000) {
                return fitness;
            }
        }
        return fitness;
    }

   
}
