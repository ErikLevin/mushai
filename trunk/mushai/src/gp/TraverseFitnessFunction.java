package gp;

import java.util.List;
import mushai.Controller;
import mushai.Model;
import mushai.Move;
import mushai.Playboard;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

class TraverseFitnessFunction extends GPFitnessFunction {

    private Playboard board;
    private Controller controller;

    public TraverseFitnessFunction(Playboard board) {
        this.board = board;
        this.controller = new Controller(board);
    }

    @Override
    protected double evaluate(IGPProgram individual) {
        float result = individual.execute_float(0, null);
        List<Move> moves = new Model().getAllPossibleMoves(board, 0);
        int iMove = Math.round(result);
        if (iMove >= 0 && iMove < moves.size()) {
            if(controller.move(moves.get(iMove))){
                System.out.println("Board after move: \n" + board);
            }
        }
        try {
            return board.getFitness();
        } catch (Exception ex) {
            throw new RuntimeException("Negative fitness");
        }
    }
}
