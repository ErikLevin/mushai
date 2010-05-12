package gp;

import java.util.List;
import minmax.PlayboardModel;
import mushai.Move;
import mushai.Playboard;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

class TraverseFitnessFunction extends GPFitnessFunction{// implements Serializable {

    private PlayboardModel board;
    int[][] boardSetup;
    private int noPlayer1Pieces, noPlayer2Pieces;

    public TraverseFitnessFunction(int noPlayer1Pieces, int noPlayer2Pieces) {
        this(new Playboard(noPlayer1Pieces, noPlayer2Pieces), noPlayer1Pieces, noPlayer2Pieces);
    }

    TraverseFitnessFunction(Playboard board, int player1Pieces, int player2Pieces) {
        this(new PlayboardModel(board, 0), player1Pieces, player2Pieces);
    }

    TraverseFitnessFunction(PlayboardModel board, int player1Pieces, int player2Pieces) {
        this.board = board;
        boardSetup = board.getTiles().clone();
        this.noPlayer1Pieces = player1Pieces;
        this.noPlayer2Pieces = player2Pieces;
    }

    @Override
    protected double evaluate(IGPProgram individual) {
        board = new PlayboardModel(boardSetup, 0); // Recreate original board.

        for (int i = 0; i < 50; i++) { // Play a game of at most 50 moves in total
            if (i % 2 == 0) {
//                System.out.println("-------------------");
//                System.out.println("Round: " + (1 + (i / 2)));
                Move m = (Move) individual.execute_object(0, new Object[]{board});
                board.movePiece(m);
            } else { //Random player
                List<Move> moves = board.getAllPossibleMoves(board.getTurn());
                Move move = moves.get((int) (Math.random() * moves.size()));
                board.movePiece(move);
//                board.movePiece(minimax.findBestMove(1));
            }

//            fitness = board.getFitness();

//            System.out.println(board);
//            System.out.println("Fitness after move: " + fitness);
//            System.out.println("\n\n");

            if (board.checkWin() != 0) {
                return board.getFitness();
            }
        }
        return board.getFitness();
    }
}
