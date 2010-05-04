package gp;

import mushai.Controller;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.GreaterThan;
import org.jgap.gp.function.LesserThan;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.SubProgram;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;
import org.jgap.gp.terminal.Terminal;

class MakeMove extends SubProgram {

    Controller controller;

    public MakeMove(GPConfiguration conf, Controller controller) throws InvalidConfigurationException {
//        super(conf, 4, CommandGene.FloatClass);
        super(conf, 4, CommandGene.FloatClass);
        this.controller = controller;

         Class[] types = {
            CommandGene.VoidClass
        };

        Class[][] argTypes = {{}};

        CommandGene[][] nodes = {{
                // ----- Base functions ------
                new Add(conf, CommandGene.FloatClass),
                new Subtract(conf, CommandGene.FloatClass),
                new Multiply(conf, CommandGene.FloatClass),
                //new IfElse(conf, CommandGene.FloatClass), // Can't return float... Make our own?
                new GreaterThan(conf, CommandGene.FloatClass),
                new LesserThan(conf, CommandGene.FloatClass),
                //new And(conf, CommandGene.FloatClass), //make own logical operators, that work on numbers?
                //new Or(conf), //Returns boolean
                //new Not(conf), //Returns boolean
                // ------- Game specific functions -------
                new MakeMove(conf, controller), //Is VoidClass
                // ------- Base terminals ---------
                new Terminal(conf, CommandGene.FloatClass, 0, 5, false, 0, true),
                // ------- Game specific terminals --------

                // ------- Sensors --------
                //new IsPieceAt(conf, CommandGene.FloatClass),
            }
        };
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
        return "MakeMove";
    }
}
