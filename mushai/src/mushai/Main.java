package mushai;

import java.awt.Color;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Player bark= new Player("bark", Color.cyan);
       bark.isMyTurn();
       Settings.addPlayer(bark);
       Settings.addPlayer(new Player("ai", Color.ORANGE));
       Window win = new Window();
       Controller controller = new Controller(win.getBoard());
    }

}
