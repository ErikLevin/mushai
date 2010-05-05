package gp;

import mushai.Controller;
import mushai.Model;
import mushai.Playboard;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

class TraverseFitnessFunction extends GPFitnessFunction {

//    private Controller controller;
    int noPlayer1Pieces, noPlayer2Pieces;

    public TraverseFitnessFunction(int noPlayer1Pieces, int noPlayer2Pieces){
//        this.controller = new Controller(AgentGP.board);
        this.noPlayer1Pieces = noPlayer1Pieces;
        this.noPlayer2Pieces = noPlayer2Pieces;
    }

    @Override
    protected double evaluate(IGPProgram individual) {
        AgentGP.board.resetBoard(noPlayer1Pieces, noPlayer2Pieces);
        individual.execute_void(0, null);

//        float result = individual.execute_float(0, null);
//        List<Move> moves = Model.getAllPossibleMoves(board, 0);
//        int iMove = Math.round(result);
//        if (iMove >= 0 && iMove < moves.size()) {
//            System.out.println("Board before move: \n" + board);
//            if (controller.move(moves.get(iMove))) {
//                System.out.println("Board after move: \n" + board);
//            }
//        }
        int hej = Model.getBoardFitness(AgentGP.board);
        return hej;
    }
}
