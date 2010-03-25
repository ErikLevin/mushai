/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mushai;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

public abstract class PieceIcon implements Icon {
    protected Color color;

    public abstract void paintIcon(Component c, Graphics g, int x, int y);
}
