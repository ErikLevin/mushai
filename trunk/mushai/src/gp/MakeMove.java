package gp;

import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

class MakeMove extends CommandGene{

    public MakeMove(GPConfiguration conf, Class VoidClass) throws Exception{
        super(conf, 4, VoidClass);
    }

    /**
     * Executes move, based on this node's parameters.
     *
     * @param c - ignored
     * @param n - ignored
     * @param args - ignored
     */
    @Override
    public void execute_void(ProgramChromosome c, int n, Object[] args) {
        
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
