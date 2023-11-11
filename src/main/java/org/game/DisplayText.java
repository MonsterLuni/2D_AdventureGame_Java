package org.game;

import java.awt.*;

public class DisplayText {
    GamePanel gp;
    Graphics2D g2;
    public DisplayText(GamePanel gp){
        this.gp = gp;
    }
    /**
     * Method to Make any Text appear.
     * @param text Text that gets displayed
     * @param x offset from the left of the Screen
     * @param y offset from the top of the Screen
     * @param g2 Graphics 2D element for displaying Text
     * @param font Font to choose
     * @param color Color to choose
     */
    public void MakeText(String text, int x, int y,Graphics2D g2, Font font, Color color){
        this.g2 = g2;
        g2.setFont(font);
        g2.setColor(color);
        g2.drawString(text,x,y);
    }
    /**
     * Method to Center any Text at the X-Axis, offsetting it is possible via the x variable.
     * @param text Text that gets displayed
     * @param x offset from the center of the Screen
     * @param y offset from the top of the Screen
     * @param g2 Graphics 2D element for displaying Text
     * @param font Font to choose
     * @param color Color to choose
     */
    public void MakeTextCenter(String text,int x, int y,Graphics2D g2, Font font, Color color){
        this.g2 = g2;
        g2.setFont(font);
        g2.setColor(color);
        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text,(gp.screenWidth/2 - textLength/2) + x,y);
    }
}
