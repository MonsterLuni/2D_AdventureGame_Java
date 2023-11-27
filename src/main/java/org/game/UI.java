package org.game;

import org.game.entity.Entity;
import org.game.object.pickUpOnly.OBJ_Heart;
import org.game.object.OBJ_Key;
import org.game.object.pickUpOnly.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_60B;
    Font Kay;
    BufferedImage keyImage;
    DisplayText dText;
    BufferedImage heart_full, heart_half, heart_broken, crystal_full, crystal_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    ArrayList<String> messageBottom = new ArrayList<>();
    ArrayList<Integer> messageCounterBottom = new ArrayList<>();
    double playTime;
    public String currentDialogue = "";
    public int slotCol;
    public int slotRow = 0;
    public int substate;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public boolean gameFinished = false;
    public int commandNum = 0;
    public int blinkOn = 0;
    public Entity npc;

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
        keyImage = key.down1;
        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(0,0,gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_broken = heart.image3;
        Entity crystal = new OBJ_ManaCrystal(0,0,gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void addMessage(String text){
        messageBottom.add(text);
        messageCounterBottom.add(0);
    }
    public void drawPlayerMana(){
        int x = gp.tileSize*13;
        int y = gp.tileSize*2 - 70;
        int i = 0;
        // DRAW MAX MANA
        while (i < gp.player.maxMana/10){
            g2.drawImage(crystal_blank,x,y,null);
            i += 1;
            x += 35;
        }
        int maxX = x;
        // DRAW MANA
        x = gp.tileSize*13;
        y = gp.tileSize*2 - 70;
        i = 0;
        while (i < gp.player.mana/10){
            g2.drawImage(crystal_full,x,y,null);
            i++;
            x += 35;
        }
        i *= 10;
        int amount = 0;
        while(i < gp.player.mana){
            amount++;
            i++;
        }
        dText.MakeText("+ " + amount,maxX + 5,y + 30,g2,g2.getFont().deriveFont(Font.ITALIC,24F),Color.white);
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(Kay);
        g2.setColor(Color.white);
        switch (gp.gameState){
            case 0 -> drawTitleScreen();
            case 1 -> {
                drawPlayerLife();
                drawPlayerMana();
                drawPlayScreen();
                drawMessage();
            }
            case 2 -> {
                drawPlayerLife();
                drawPlayerMana();
                drawPauseScreen();
            }
            case 3 -> {
                drawPlayerLife();
                drawPlayerMana();
                drawDialogueScreen();
            }
            case 4 -> drawSettingsScreen();
            case 5 -> {
                drawCharacterScreen();
                drawInventory();
            }
            case 6 -> drawOptionScreen();
            case 7 -> drawGameOverScreen();
            case 8 -> transitionScreen();
            case 9 -> drawTradeScreen();
        }
    }

    private void drawTradeScreen() {
        switch (substate){
            case 0: trade_select();
            case 1: trade_buy();
            case 2: trade_sell();
        }
        gp.keyH.enterPressed = false;
    }
    public void trade_select(){
        drawDialogueScreen();
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 5;
        int width = gp.tileSize * 3;
        int height = (int)(gp.tileSize * 3.5);

        // WINDOW
        drawSubWindow(x,y,width,height);

        // DRAW TEXTS
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy",x,y);
        y += gp.tileSize;
        g2.drawString("Sell",x,y);
        y += gp.tileSize;
        g2.drawString("Leave",x,y);
    }
    public void trade_buy(){

    }
    public void trade_sell(){

    }

    int counter = 0;

    private void transitionScreen() {
        counter++;
        g2.setColor(new Color(0,0,0,counter * 5));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        if(counter == 50){
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.pEventX = gp.player.worldX;
            gp.eHandler.pEventY = gp.player.worldY;
        }
    }

    private void drawGameOverScreen() {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int y = gp.tileSize*5;

        // SHADOW
        dText.MakeTextCenter("GameOver",0,y,g2,g2.getFont().deriveFont(Font.BOLD, 110f),Color.black);

        // MAIN
        dText.MakeTextCenter("GameOver",-4,y-4,g2,g2.getFont().deriveFont(Font.BOLD, 110f),Color.white);

        // Retry
        dText.MakeTextCenter("Retry",-4,y + gp.tileSize*3,g2,g2.getFont().deriveFont(Font.BOLD, 50f),Color.white);
        if(commandNum == 0){
            dText.MakeTextCenter(">",-gp.tileSize*2,y + gp.tileSize*3,g2,g2.getFont().deriveFont(Font.BOLD, 50f),Color.white);
        }

        // Back to the title Screen
        dText.MakeTextCenter("Quit",-4,y + gp.tileSize*4 + 10,g2,g2.getFont().deriveFont(Font.BOLD, 50f),Color.white);
        if(commandNum == 1){
            dText.MakeTextCenter(">",-gp.tileSize*2 + 15,y + gp.tileSize*4 + 10,g2,g2.getFont().deriveFont(Font.BOLD, 50f),Color.white);
        }
    }
    private void drawOptionScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        int x = gp.tileSize*6;
        int y = gp.tileSize;
        // SUB WINDOW
        drawSubWindow(gp.tileSize*6,gp.tileSize,gp.tileSize*8,gp.tileSize*10);

        switch (substate){
            case 0 -> option_top(x,y);
            case 1 -> options_fullScreenNotification(x,y);
            case 2 -> options_control(x,y);
            case 3 -> options_endGameConfirmation();
        }
        gp.keyH.enterPressed = false;
    }
    private void options_endGameConfirmation() {
        //TODO: Video 35 41.11 make that
        substate = 0;
        gp.gameState = gp.titleState;
    }
    private void option_top(int frameX, int frameY) {
        g2.setFont(g2.getFont().deriveFont(24F));
        int textY = frameY + gp.tileSize;
        // TITLE
        dText.MakeTextCenter("Options",0,textY, g2, g2.getFont(),Color.white);

        // FULL SCREEN ON / OFF
        textY += gp.tileSize*2;
        dText.MakeText("Full Screen",frameX + gp.tileSize,textY, g2, g2.getFont(),Color.white);
        if(commandNum == 0){
            g2.drawString(">", frameX+25, textY);
            if(gp.keyH.enterPressed){
                if(gp.fullScreenOn){
                    gp.fullScreenOn = false;
                }
                else {
                    gp.fullScreenOn = true;
                }
                substate = 1;
            }
        }

        // MUSIC
        textY += gp.tileSize;
        dText.MakeText("Music",frameX + gp.tileSize,textY, g2, g2.getFont(),Color.white);
        if(commandNum == 1){
            g2.drawString(">", frameX+25, textY);
        }

        // SE
        textY += gp.tileSize;
        dText.MakeText("Sound Effects",frameX + gp.tileSize,textY, g2, g2.getFont(),Color.white);
        if(commandNum == 2){
            g2.drawString(">", frameX+25, textY);
        }

        // CONTROL
        textY += gp.tileSize;
        dText.MakeText("Control",frameX + gp.tileSize,textY, g2, g2.getFont(),Color.white);
        if(commandNum == 3){
            g2.drawString(">", frameX+25, textY);
            if(gp.keyH.enterPressed){
                substate = 2;
                commandNum = 0;
            }
        }

        // END GAME
        textY += gp.tileSize;
        dText.MakeText("End Game",frameX + gp.tileSize,textY, g2, g2.getFont(),Color.white);
        if(commandNum == 4){
            g2.drawString(">", frameX+25, textY);
            if(gp.keyH.enterPressed){
                substate = 3;
                commandNum = 0;
            }
        }
        int textX;

        // FULL SCREEN CHECK BOX
        g2.setStroke(new BasicStroke(1));
        textX = frameX + gp.tileSize*5;
        textY = frameY + gp.tileSize*2 + 24;
        g2.drawRect(textX,textY,24,24);
        if(gp.fullScreenOn){
            g2.fillRect(textX,textY,24,24);
        }

        // MUSIC VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX,textY,120,24); // 120/5 = 24
        int volumeWidth = 24*gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        // SE VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX,textY,120,24);
        volumeWidth = 24*gp.sEffects.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        gp.config.saveConfig();
    }
    public void options_control(int frameX, int frameY){
        int textY = frameY + gp.tileSize;
        int textX;
        // TITLE
        String text = "Control";
        dText.MakeTextCenter(text,0,textY,g2,g2.getFont(),Color.white);
        textY += gp.tileSize;
        dText.MakeText("Move",frameX + 25,textY += gp.tileSize,g2,g2.getFont(),Color.white);
        dText.MakeText("Confirm",frameX + 25,textY += gp.tileSize,g2,g2.getFont(),Color.white);
        dText.MakeText("Shoot / Cast",frameX + 25,textY += gp.tileSize,g2,g2.getFont(),Color.white);
        dText.MakeText("Character Screen",frameX + 25,textY += gp.tileSize,g2,g2.getFont(),Color.white);
        dText.MakeText("Pause",frameX + 25,textY += gp.tileSize,g2,g2.getFont(),Color.white);
        dText.MakeText("Options",frameX + 25,textY + gp.tileSize,g2,g2.getFont(),Color.white);

        textY = frameY + gp.tileSize*2;
        textX = frameX + gp.tileSize*7;

        dText.MakeText("A",textX,textY += gp.tileSize,g2,g2.getFont(),Color.white);
        dText.MakeText("B",textX,textY += gp.tileSize,g2,g2.getFont(),Color.white);
        dText.MakeText("C",textX,textY += gp.tileSize,g2,g2.getFont(),Color.white);
        dText.MakeText("D",textX,textY += gp.tileSize,g2,g2.getFont(),Color.white);
        dText.MakeText("E",textX,textY += gp.tileSize,g2,g2.getFont(),Color.white);
        dText.MakeText("F",textX,textY + gp.tileSize,g2,g2.getFont(),Color.white);
    }
    public void options_fullScreenNotification(int frameX, int frameY){
        currentDialogue = "The change will take \neffect after restarting \nthe game.";

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, frameX + gp.tileSize/2,frameY + gp.tileSize*3);
            frameY += 40;
        }

        // BACK
        frameY += gp.tileSize*6;
        g2.drawString("Back",frameX + gp.tileSize/2,frameY);
        if(commandNum == 0){
            g2.drawString(">",frameX + gp.tileSize/2 - 25,frameY);
            if(gp.keyH.enterPressed){
                substate = 0;
            }
        }

    }
    private void drawInventory() {

        // FRAME
        int frameX = gp.tileSize;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 3;

        // DRAW PLAYER'S ITEMS
        for(int i = 0; i < gp.player.inventory.size(); i++){
            // EQUIP CURSOR
            if(gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield){
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            }
            g2.drawImage(gp.player.inventory.get(i).down1,slotX,slotY,null);
            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // CURSOR

        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotXStart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);

        // DESCRIPTION FRAME
        int dFrameY = frameY + frameHeight;
        int dFrameHeight = gp.tileSize*3;


        // DRAW DESCRIPTION TEXT
        int textX = frameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28f));

        int itemIndex = getItemIndexOnSlot();

        if(itemIndex < gp.player.inventory.size()){
            drawSubWindow(frameX,dFrameY, frameWidth,dFrameHeight);
            for(String line: gp.player.inventory.get(itemIndex).description.split("\n")){
                g2.drawString(line,textX,textY);
                textY += 32;
            }
        }
    }
    public int getItemIndexOnSlot(){
        return slotCol + (slotRow*5);
    }
    private void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*10;
        for(int i = 0; i < messageBottom.size(); i++){
            if(messageBottom.get(i) != null){
                dText.MakeText(messageBottom.get(i),messageX + 2,messageY + 2,g2,g2.getFont().deriveFont(Font.BOLD,16F),Color.black);
                dText.MakeText(messageBottom.get(i),messageX,messageY,g2,g2.getFont().deriveFont(Font.BOLD,16F),Color.white);
                int counter = messageCounterBottom.get(i) + 1; //messageCounter++
                messageCounterBottom.set(i,counter); // set the counter to the array
                messageY += 20;

                if(messageCounterBottom.get(i) > 120){
                    messageBottom.remove(i);
                    messageCounterBottom.remove(i);
                }
            }
        }
    }
    private void drawCharacterScreen() {
        // CREATE A FRAME
        final int frameX = gp.tileSize * 13;
        int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*6;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);
        // TEXT
        dText.MakeText("Level",frameX + 20,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);
        frameY += 40;
        dText.MakeText("Life",frameX + 20,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);
        frameY += 40;
        dText.MakeText("Strength",frameX + 20,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);
        frameY += 40;
        dText.MakeText("Dexterity",frameX + 20,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);
        frameY += 40;
        dText.MakeText("Attack",frameX + 20,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);
        frameY += 40;
        dText.MakeText("Defense",frameX + 20,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);
        frameY += 40;
        dText.MakeText("Exp",frameX + 20,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);
        frameY += 40;
        dText.MakeText("Next Level",frameX + 20,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);
        frameY += 40;
        dText.MakeText("Coin",frameX + 20,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);
        frameY += 40;
        dText.MakeText("Weapon",frameX + 20,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);
        frameY += 40;
        dText.MakeText("Shield",frameX + 20,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);

        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        int textX;
        String value;

        frameY = gp.tileSize;
        value = String.valueOf(gp.player.level);
        textX = getXForAlignToRightText(value,tailX);
        dText.MakeText(value,textX + 10,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);

        frameY += 40;
        value = gp.player.life + " / " + gp.player.maxLife;
        textX = getXForAlignToRightText(value,tailX);
        dText.MakeText(value,textX + 10,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);

        frameY += 40;
        value = String.valueOf(gp.player.strength);
        textX = getXForAlignToRightText(value,tailX);
        dText.MakeText(value,textX + 10,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);

        frameY += 40;
        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignToRightText(value,tailX);
        dText.MakeText(value,textX + 10,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);

        frameY += 40;
        value = String.valueOf(gp.player.attack);
        textX = getXForAlignToRightText(value,tailX);
        dText.MakeText(value,textX + 10,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);

        frameY += 40;
        value = String.valueOf(gp.player.defense);
        textX = getXForAlignToRightText(value,tailX);
        dText.MakeText(value,textX + 10,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);

        frameY += 40;
        value = String.valueOf(gp.player.exp);
        textX = getXForAlignToRightText(value,tailX);
        dText.MakeText(value,textX + 10,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);

        frameY += 40;
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXForAlignToRightText(value,tailX);
        dText.MakeText(value,textX + 10,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);

        frameY += 40;
        value = String.valueOf(gp.player.coin);
        textX = getXForAlignToRightText(value,tailX);
        dText.MakeText(value,textX + 10,frameY + gp.tileSize,g2,g2.getFont().deriveFont(32F),Color.white);

        frameY += 48;
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize + 10, frameY, null);

        frameY += 48;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize + 10, frameY, null);
    }
    public int getXForAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return tailX - length;
    }
    public void drawPlayerLife(){
        int i = 0;
        int x = gp.tileSize / 2;
        // DRAW MAX LIFE
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_broken, x, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            x += gp.tileSize;
            i++;
        }
        i = 0;
        x = gp.tileSize / 2;
        // DRAW CURRENT LIFE
        while (i < gp.player.life){
            g2.drawImage(heart_half, x, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            }
            i++;
            x += gp.tileSize;
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
        dText.MakeTextCenter("SETTINGS",0,gp.tileSize*9,g2,g2.getFont().deriveFont(Font.BOLD,48F),Color.white);
        dText.MakeTextCenter("QUIT",0,gp.tileSize*10,g2,g2.getFont().deriveFont(Font.BOLD,48F),Color.white);

        if(blinkOn < 30){
            switch (commandNum){
                case 0 -> dText.MakeText(">",(gp.tileSize*13)/2,gp.tileSize*7,g2,g2.getFont().deriveFont(Font.BOLD,48F),Color.white);
                case 1 -> dText.MakeText(">",(gp.tileSize*13)/2 - 10,gp.tileSize*8,g2,g2.getFont().deriveFont(Font.BOLD,48F),Color.white);
                case 2 -> dText.MakeText(">",(gp.tileSize*13)/2 + 10,gp.tileSize*9,g2,g2.getFont().deriveFont(Font.BOLD,48F),Color.white);
                case 3 -> dText.MakeText(">",(gp.tileSize*16)/2,gp.tileSize*10,g2,g2.getFont().deriveFont(Font.BOLD,48F),Color.white);
            }
        }
        if(blinkOn > 60) {
            blinkOn = 0;
        }
        blinkOn++;
    }
    public void drawSettingsScreen(){
        // BACKGROUND
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        dText.MakeTextCenter("SETTINGS",0, gp.screenHeight/2 - 100, this.g2, arial_40, Color.white);
    }
    public void drawPauseScreen(){
        dText.MakeTextCenter("PAUSED",0, gp.screenHeight/2 - 100, this.g2, arial_40, Color.white);
    }
    public void drawDialogueScreen(){
        // WINDOW
        int x = gp.tileSize * 3;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*6);
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
            dText.MakeTextCenter("Time: " + dFormat.format(playTime) + "s",0,gp.screenHeight/2,this.g2, arial_40, Color.white);
            gp.gameThread = null;
        }
        else{
            // DRAW AMOUNT OF KEY
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize*2, gp.tileSize, gp.tileSize, null);
            dText.MakeText("x " + gp.player.hasKey,70,gp.tileSize*2 + 40,g2,arial_40,Color.white);

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