package org.example;

import org.example.object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_60B;
    Font Kay;
    BufferedImage keyImage;
    DisplayText dText;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    double playTime;
    public String currentDialogue = "";
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public boolean gameFinished = false;
    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_60B = new Font("Arial", Font.BOLD, 60);
        try{
            System.out.println("Font loading started");
            InputStream is = new FileInputStream("src/main/res/font/KayPhoDu-Regular.ttf");
            Kay = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Font loading error");
        }
        System.out.println("Font loading started");
        OBJ_Key key = new OBJ_Key(0,0, this.gp);
        dText = new DisplayText(this.gp);
        keyImage = key.image;
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(Kay);
        g2.setColor(Color.white);
        switch (gp.gameState){
            case 1 -> drawPlayScreen();
            case 2 -> drawPauseScreen();
            case 3 -> drawDialogueScreen();
        }
    }
    public void drawPauseScreen(){
        String text = "PAUSED";
        dText.MakeTextCenter(text, gp.screenHeight/2 - 100, this.g2, arial_40, Color.white);
    }
    public void drawDialogueScreen(){
        // WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x,y,width,height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line,x,y);
            y += 40;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x,y,width,height,35,35);
    }
    public void drawPlayScreen(){
        if(gameFinished){
            dText.MakeTextCenter("You found the treasure!",gp.screenHeight/2 - 100,this.g2, arial_40, Color.white);
            dText.MakeTextCenter("Congratulations!",gp.screenHeight/2 - 50,this.g2, arial_60B, Color.red);
            dText.MakeTextCenter("You found the treasure!",gp.screenHeight/2,this.g2, arial_40, Color.white);
            gp.gameThread = null;
        }
        else{
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 70, 60);

            // TIME
            playTime += (double)1/gp.FPS;
            //g2.drawString("Time: " + dFormat.format(playTime) , gp.screenWidth - 250, 60);

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
