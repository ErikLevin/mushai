package mushai;

import java.awt.Color;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("starting program");
//        SettingsWindow setWindow = new SettingsWindow();


        Settings.nullPlayers();
        Player player1 = new Player("You", Color.yellow);
        Settings.addPlayer(player1);

        Player player2 = new Player("Ai", Color.cyan);
        player2.setMyTurn(true);
        player2.setType(Player.PlayerType.MINIMAX);

        Settings.addPlayer(player2);



        Settings.setPlayboardSize(8);

        Window win = new Window();
        Controller controller = new Controller(win);

    }
}
