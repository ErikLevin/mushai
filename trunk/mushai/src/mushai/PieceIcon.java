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
    protected int width, height;

    public PieceIcon(Color color) {
        this.color = color;
    }

    /**
     * @return the width of icon
     */
    @Override
    public int getIconWidth() {
        return width;
    }

    /**
     * @return the height of icon
     */
    @Override
    public int getIconHeight() {
        return height;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        width = c.getWidth() - (c.getWidth() / 4);
        height = c.getHeight() - (c.getHeight() / 4);
    }
}
