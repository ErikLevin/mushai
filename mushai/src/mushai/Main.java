package mushai;

import java.awt.Color;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

       Settings.addPlayer(new Player("bark", Color.PINK));
       Settings.addPlayer(new Player("ai", Color.ORANGE));
       Window win = new Window();
       Controller controller = new Controller(win.getBoard());
//       win.add(new Square());
    }

}
