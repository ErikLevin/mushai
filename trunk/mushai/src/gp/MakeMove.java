package gp;

import mushai.Controller;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

class MakeMove extends CommandGene{

    Controller controller;

    public MakeMove(GPConfiguration conf, Controller controller) throws InvalidConfigurationException{
        super(conf, 4, CommandGene.VoidClass);
        this.controller  = controller;
    }

    @Override
    public void execute_void(ProgramChromosome c, int n, Object[] args) {
        check(c);
        int fromX = c.execute_int(n, 0, args);
        int fromY = c.execute_int(n, 1, args);
        int toX = c.execute_int(n, 2, args);
        int toY = c.execute_int(n, 3, args);

        controller.move(fromX, fromY, toX, toY);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
