package gp;

import java.io.Serializable;
import minmax.PlayboardModel;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.IMutateable;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 * When initialized, chooses a random position on the playboard. When executed,
 * returns 1 if there is a piece on that position, 0 otherwise.
 *
 * Only supported for return type FloatClass and execute_float!
 *
 * @author MushAI
 */
class IsPieceAt extends CommandGene implements IMutateable, Serializable {

    int x, y;

    public IsPieceAt(GPConfiguration a_conf, Class a_returnType) throws InvalidConfigurationException {
        super(a_conf, 0, a_returnType);
        randomize(a_conf);
    }

    private void randomize(GPConfiguration a_conf) {
        x = a_conf.getRandomGenerator().nextInt(4);
        y = a_conf.getRandomGenerator().nextInt(4);
    }

    /**
     * Returns 1 if there is a piece on this sensor's position.
     *
     * @param c - ignored
     * @param n - ignored
     * @param args - first argument is the playboard to inspect
     * @return - 1 if there is piece on (x,y) on this playboard, 0 otherwise
     */
    @Override
    public float execute_float(ProgramChromosome c, int n, Object[] args) {
        PlayboardModel board = (PlayboardModel) args[0];
        if (board.getTiles()[x][y] == PlayboardModel.EMPTY_TILE) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "IsPieceAt (" + x + ", " + y + ")";
    }

    public CommandGene applyMutation(int a_index, double a_percentage) throws InvalidConfigurationException {
        randomize(getGPConfiguration());
        return this;
    }
}
