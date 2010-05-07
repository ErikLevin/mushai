package gp;

import java.io.Serializable;
import minmax.MiniMax;
import minmax.PlayboardModel;
import mushai.Controller;
import mushai.Model;
import mushai.Playboard;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

class TraverseFitnessFunction extends GPFitnessFunction implements Serializable {

    private Controller controller;
    private Playboard board;
    private MiniMax minimax;
    private int noPlayer1Pieces, noPlayer2Pieces;

    public TraverseFitnessFunction(int noPlayer1Pieces, int noPlayer2Pieces) {
        this(new Playboard(noPlayer1Pieces, noPlayer2Pieces), noPlayer1Pieces, noPlayer2Pieces);
    }

    TraverseFitnessFunction(Playboard board, int player1Pieces, int player2Pieces) {
        this.board = board;
        this.controller = new Controller(board);
        this.noPlayer1Pieces = player1Pieces;
        this.noPlayer2Pieces = player2Pieces;
        this.minimax = new MiniMax(controller, board);
    }

    TraverseFitnessFunction(PlayboardModel board, int player1Pieces, int player2Pieces) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected double evaluate(IGPProgram individual) {
        board.resetBoard(noPlayer1Pieces, noPlayer2Pieces);
        int fitness = 0;

        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {
//                System.out.println("-------------------");
//                System.out.println("Round: " + (1 + (i / 2)));
                individual.execute_void(0, new Object[]{board});
            } else { //Random player
                controller.move(minimax.findBestMove(1));
            }

            fitness = Model.getBoardFitness(board);

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
