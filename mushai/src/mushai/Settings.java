/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mushai;

import java.util.ArrayList;

/**
 *
 * @author bark
 */
public class Settings {
private static int playboardSize=4;
private static ArrayList<Player> ar= new ArrayList<Player>();

    public static int getPlayboardSize(){
        return playboardSize;
    }
    public static int getNrOfPlayers(){
        return 2;
    }
    public static boolean addPlayer(Player player){
        ar.add(player);
        return true;
    }
    public static ArrayList<Player> getPlayers(){
       return ar;
    }
}
