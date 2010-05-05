package gp;

import mushai.Controller;
import mushai.Model;
import mushai.Playboard;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

class MakeMove extends CommandGene {

    public MakeMove(GPConfiguration conf) throws InvalidConfigurationException {
//        super(conf, 4, CommandGene.FloatClass);
        super(conf, 4, CommandGene.VoidClass);
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

        Controller controller = new Controller((Playboard) args[0]);

        int fromX = Math.round(c.execute_float(n, 0, args));
        int fromY = Math.round(c.execute_float(n, 1, args));
        int toX = Math.round(c.execute_float(n, 2, args));
        int toY = Math.round(c.execute_float(n, 3, args));
        if (!controller.move(fromX, fromY, toX, toY)) {
            //System.out.println("Couldn't move! Will choose first possible move...");
            controller.move(Model.getAllPossibleMoves(controller.getBoard()).get(0));
        } else {
            System.out.println("Could MOVE HORRAYYY!");
        }

        return controller.getBoard();
    }

    /**
     * Useless float representation that JGap needs to not crash...
     *
     * @param c
     * @param n
     * @param args
     * @return - Random float in [0,5]
     */
    @Override
    public float execute_float(ProgramChromosome c, int n, Object[] args) {
        return (float) (Math.random() * 5);
    }

    @Override
    public String toString() {
        return "MakeMove (&1, &2, &3, &4)";
    }
}
