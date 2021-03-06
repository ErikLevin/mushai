package mushai;

import java.awt.Color;
import javax.swing.UIManager;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
	    // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } 
        catch (Exception e) {
        }
        
        System.out.println("starting program");
//        SettingsWindow setWindow = new SettingsWindow();

        Settings.nullPlayers();
        Player player1 = new Player("You", Color.yellow);
//        player1.setType(Player.PlayerType.MINIMAX);
        Settings.addPlayer(player1);

        Player player2 = new Player("Ai", Color.cyan);
        player2.setMyTurn(true);
        player2.setType(Player.PlayerType.MINIMAX);
        //player2.setType(Player.PlayerType.GENETIC);

        Settings.addPlayer(player2);
        System.out.println(Model.whoseTurnIsIt());

        Settings.setPlayboardSize(10);

        new Controller(new Window());
    }
}
