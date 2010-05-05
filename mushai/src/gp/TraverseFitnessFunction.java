package gp;

import java.util.List;
import mushai.Controller;
import mushai.Model;
import mushai.Move;
import mushai.Playboard;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

class TraverseFitnessFunction extends GPFitnessFunction {

    private Controller controller;
    private Playboard board;
    int noPlayer1Pieces, noPlayer2Pieces;

    public TraverseFitnessFunction(int noPlayer1Pieces, int noPlayer2Pieces) {
        this(new Playboard(noPlayer1Pieces, noPlayer2Pieces), noPlayer1Pieces, noPlayer2Pieces);
    }

    TraverseFitnessFunction(Playboard board, int player1Pieces, int player2Pieces) {
        this.board = board;
        this.controller = new Controller(board);
        this.noPlayer1Pieces = player1Pieces;
        this.noPlayer2Pieces = player2Pieces;
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
                List<Move> moves = Model.getAllPossibleMoves(board);
                Move move = moves.get((int) Math.random() * moves.size());
                controller.move(move);
            }

            fitness = Model.getBoardFitness(board);

//            System.out.println(board);
//            System.out.println("Fitness after move: " + fitness);
//            System.out.println("\n\n");

            if (fitness > 1000 || fitness == 0) {
                return fitness;
            }
        }
        return fitness;
    }
}
