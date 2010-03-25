/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mushai;

import java.awt.Color;

/**
 *
 * @author bark
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

       Settings.addPlayer(new Player("bark", Color.yellow));
       Settings.addPlayer(new Player("ai", Color.ORANGE));
       Window win = new Window();

    }

}
