
package gp;

import mushai.Playboard;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

class TraverseFitnessFunction extends GPFitnessFunction {

    private Playboard board;

    public TraverseFitnessFunction(Playboard board) {
this.board = board;
    }

    @Override
    protected double evaluate(IGPProgram individual) {
        individual.execute_void(0, new Object[] {board} );
        
        return board.getFitness();
    }

}
