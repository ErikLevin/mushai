package gp;

import java.util.List;
import minmax.PlayboardModel;
import mushai.Move;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

class MakeMove extends CommandGene {

    public MakeMove(GPConfiguration conf) throws InvalidConfigurationException {
//        super(conf, 4, CommandGene.FloatClass);
        super(conf, 4, Move.class);
    }

    @Override
    public Class getChildType(IGPProgram a_ind, int a_chromNum) {
        return CommandGene.FloatClass;
    }

    @Override
    public void execute_void(ProgramChromosome c, int n, Object[] args) {
        execute_object(c, n, args);
    }

    @Override
    public Object execute_object(ProgramChromosome c, int n, Object[] args) {
        check(c);

        PlayboardModel board = (PlayboardModel) args[0];

        int fromX = Math.round(c.execute_float(n, 0, args));
        int fromY = Math.round(c.execute_float(n, 1, args));
        int toX = Math.round(c.execute_float(n, 2, args));
        int toY = Math.round(c.execute_float(n, 3, args));
        List<Move> moves = board.getAllPossibleMoves(board.getTurn());
        Move move = new Move(fromX, fromY, toX, toY);
        if (!moves.contains(move)) {
            move = moves.get(0);
        }
        return move;
    }

    @Override
    public String toString() {
        return "MakeMove (&1, &2, &3, &4)";
    }
}
