package org.game;

import org.game.object.OBJ_Key;

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
    public int commandNum = 0;
    public int blinkOn = 0;
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
            case 0 -> drawTitleScreen();
            case 1 -> drawPlayScreen();
            case 2 -> drawPauseScreen();
            case 3 -> drawDialogueScreen();
        }
    }
    public void drawTitleScreen(){
        // BACKGROUND
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
        // SHADOW
        dText.MakeTextCenter("2D ADVENTURE",0,205,g2,g2.getFont().deriveFont(Font.BOLD,96F),Color.gray);
        // TITLE NAME
        dText.MakeTextCenter("2D ADVENTURE",0,200,g2,g2.getFont().deriveFont(Font.BOLD,96F),Color.white);
        // PLAYER IMAGE
        g2.drawImage(gp.player.down1,gp.screenWidth - (gp.tileSize*8)/2,gp.tileSize*6,gp.tileSize*2,gp.tileSize*2, null);
        // NPC IMAGE
        try{
            g2.drawImage(gp.player.setup("npc","mage_down_1"),  (gp.tileSize*4)/2,gp.tileSize*6,gp.tileSize*2,gp.tileSize*2, null);
        }catch (Exception e){
            e.printStackTrace();
        }
        // MENU
        dText.MakeTextCenter("NEW GAME",0,gp.tileSize*7,g2,g2.getFont().deriveFont(Font.BOLD,48F),Color.white);
        dText.MakeTextCenter("LOAD GAME",0,gp.tileSize*8,g2,g2.getFont().deriveFont(Font.BOLD,48F),Color.white);
        dText.MakeTextCenter("QUIT",0,gp.tileSize*9,g2,g2.getFont().deriveFont(Font.BOLD,48F),Color.white);

        if(blinkOn < 30){
            switch (commandNum){
                case 0 -> dText.MakeText(">",(gp.tileSize*9)/2,gp.tileSize*7,g2,g2.getFont().deriveFont(Font.BOLD,48F),Color.white);
                case 1 -> dText.MakeText(">",(gp.tileSize*9)/2 - 10,gp.tileSize*8,g2,g2.getFont().deriveFont(Font.BOLD,48F),Color.white);
                case 2 -> dText.MakeText(">",(gp.tileSize*12)/2,gp.tileSize*9,g2,g2.getFont().deriveFont(Font.BOLD,48F),Color.white);
            }
        }
        if(blinkOn > 60) {
            blinkOn = 0;
        }
        blinkOn++;
    }
    public void drawPauseScreen(){
        String text = "PAUSED";
        dText.MakeTextCenter(text,0, gp.screenHeight/2 - 100, this.g2, arial_40, Color.white);
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
            g2.setColor(new Color(0,0,0));
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
            dText.MakeTextCenter("You found the treasure!",0,gp.screenHeight/2 - 100,this.g2, arial_40, Color.white);
            dText.MakeTextCenter("Congratulations!",0,gp.screenHeight/2 - 50,this.g2, arial_60B, Color.red);
            dText.MakeTextCenter("You found the treasure!",0,gp.screenHeight/2,this.g2, arial_40, Color.white);
            gp.gameThread = null;
        }
        else{
            // DRAW AMOUNT OF KEY
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            dText.MakeText("x " + gp.player.hasKey,70,60,g2,arial_40,Color.white);

            // TIME
            playTime += (double)1/gp.FPS;

            // MESSAGE
            if(messageOn){
                dText.MakeTextCenter(message,0,gp.screenHeight/2 - 100,g2,g2.getFont().deriveFont(30F),Color.white);
                messageCounter++;
                if(messageCounter > gp.FPS * 2){ //
                    messageOn = false;
                    messageCounter = 0;
                }
            }
        }
    }
}
