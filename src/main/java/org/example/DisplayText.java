package org.example;

import java.awt.*;

public class DisplayText {
    GamePanel gp;
    Graphics2D g2;
    public DisplayText(GamePanel gp){
        this.gp = gp;
    }
    public void MakeText(String text, int x, int y,Graphics2D g2, Font font, Color color){
        this.g2 = g2;
        g2.setFont(font);
        g2.setColor(color);
        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text,x,y);
    }
    public void MakeTextCenter(String text, int y,Graphics2D g2, Font font, Color color){
        this.g2 = g2;
        g2.setFont(font);
        g2.setColor(color);
        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text,gp.screenWidth/2 - textLength/2,y);
    }
}
