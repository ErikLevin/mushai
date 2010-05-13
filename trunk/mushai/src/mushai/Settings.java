/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mushai;

import java.util.ArrayList;

/**
 * @todo - Make Singleton?
 * 
 * 
 */
public class Settings {

    private static int playboardSize = 10;
    private static ArrayList<Player> players = new ArrayList<Player>();

    public static void nullPlayers() {
        players = new ArrayList<Player>();
    }

    public static int getPlayboardSize() {
        return playboardSize;
    }

    public static void setPlayboardSize(int size) {
        playboardSize = size;
    }

    public static int getNrOfPlayers() {
        return 2;
    }

    public static boolean addPlayer(Player player) {
        players.add(player);
        return true;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static Player getPlayer(int i) {
        return getPlayers().get(i);
    }

    public static boolean paintGraphics() {
        return true;
    }

    public static Player getCurrentPlayer() {
        for(Player p : players){
            if(p.isItMyTurn()){
                return p;
            }
        }
        throw new IllegalStateException("It is no one's turn!");
    }
}
