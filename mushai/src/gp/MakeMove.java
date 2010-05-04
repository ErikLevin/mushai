package gp;

import mushai.Controller;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.function.SubProgram;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

class MakeMove extends SubProgram {

    Controller controller;

    public MakeMove(GPConfiguration conf, Controller controller) throws InvalidConfigurationException {
//        super(conf, 4, CommandGene.FloatClass);
        super(conf, new Class[]{
                    CommandGene.FloatClass,
                    CommandGene.FloatClass,
                    CommandGene.FloatClass,
                    CommandGene.FloatClass});
        this.controller = controller;
    }

    @Override
    public void execute_void(ProgramChromosome c, int n, Object[] args) {
        check(c);
        int fromX = Math.round(c.execute_float(n, 0, args));
        int fromY = Math.round(c.execute_float(n, 1, args));
        int toX = Math.round(c.execute_float(n, 2, args));
        int toY = Math.round(c.execute_float(n, 3, args));

        controller.move(fromX, fromY, toX, toY);
    }

    @Override
    public float execute_float(ProgramChromosome c, int n, Object[] args) {
        return (float) (Math.random() * 5);
    }

    @Override
    public String toString() {
        return "MakeMove";
    }
}
