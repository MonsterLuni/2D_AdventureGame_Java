package org.example;

import org.example.object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_60B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public boolean gameFinished = false;
    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_60B = new Font("Arial", Font.BOLD, 60);
        OBJ_Key key = new OBJ_Key(0,0, this.gp);
        keyImage = key.image;
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        if(gp.gameState == gp.playState){
            // Do playstate stuff later
            if(gameFinished){
                g2.setFont(arial_40);
                g2.setColor(Color.white);
                String text;
                int textLength;
                int x;
                int y;
                text = "You found the treasure!";
                textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                x = gp.screenWidth/2 - textLength/2;
                y = gp.screenHeight/2 - 100;
                g2.drawString(text,x,y);
                g2.setFont(arial_60B);
                g2.setColor(Color.red);
                text = "Congratulations!";
                textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                x = gp.screenWidth/2 - textLength/2;
                y = gp.screenHeight/2 - 50;
                g2.drawString(text,x,y);
                g2.setFont(arial_40);
                g2.setColor(Color.white);
                text = "Your Time: " + dFormat.format(playTime);
                textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                x = gp.screenWidth/2 - textLength/2;
                y = gp.screenHeight/2;
                g2.drawString(text,x,y);
                gp.gameThread = null;
            }
            else{
                g2.setFont(arial_40);
                g2.setColor(Color.white);
                g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
                g2.drawString("x " + gp.player.hasKey, 70, 60);

                // TIME
                playTime += (double)1/gp.FPS;
                g2.drawString("Time: " + dFormat.format(playTime) , gp.screenWidth - 250, 60);

                // MESSAGE
                if(messageOn){
                    g2.setFont(g2.getFont().deriveFont(30F));
                    g2.drawString(message,gp.screenWidth/2 - ((int)g2.getFontMetrics().getStringBounds(message, g2).getWidth() / 2),gp.screenHeight/2 - 100);
                    messageCounter++;
                    if(messageCounter > gp.FPS * 2){ //
                        messageOn = false;
                        messageCounter = 0;
                    }
                }
            }
        }
        else if(gp.gameState == gp.pauseState){

        }
    }

    // -----------------------------------------------

    public void draw_old(Graphics2D g2){
        if(gameFinished){
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            String text;
            int textLength;
            int x;
            int y;
            text = "You found the treasure!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - 100;
            g2.drawString(text,x,y);
            g2.setFont(arial_60B);
            g2.setColor(Color.red);
            text = "Congratulations!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - 50;
            g2.drawString(text,x,y);
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            text = "Your Time: " + dFormat.format(playTime);
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2;
            g2.drawString(text,x,y);
            gp.gameThread = null;
        }
        else{
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 70, 60);

            // TIME
            playTime += (double)1/gp.FPS;
            g2.drawString("Time: " + dFormat.format(playTime) , gp.screenWidth - 250, 60);

            // MESSAGE
            if(messageOn){
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message,gp.screenWidth/2 - ((int)g2.getFontMetrics().getStringBounds(message, g2).getWidth() / 2),gp.screenHeight/2 - 100);
                messageCounter++;
                if(messageCounter > gp.FPS * 2){ //
                    messageOn = false;
                    messageCounter = 0;
                }
            }
        }
    }
}
