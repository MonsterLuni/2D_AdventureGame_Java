package org.example.entity;

import org.example.GamePanel;
import org.example.KeyHandler;
import org.example.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    private boolean pressing = false;
    public final int screenX;
    public final int screenY;
    public int animationDuration = 15;
    public int hasKey = 0;
    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(12, 30, 30, 20);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize * 40;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage(){
        System.out.println("Player loading started");
        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
        System.out.println("Player loading ended");
    }
    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(new FileInputStream("src/main/res/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            System.out.println("Player loading error");
            e.printStackTrace();
        }
        return image;
    }
    public void update(){
        pressing = false;
        if(keyH.upPressed){
            direction = "up";
            pressing = true;
        }
        if(keyH.downPressed){
            direction = "down";
            pressing = true;
        }
        if(keyH.leftPressed){
            direction = "left";
            pressing = true;
        }
        if(keyH.rightPressed){
            direction = "right";
            pressing = true;
        }
        //CHECK TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);
        // CHECK OBJECT COLLISION
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);
        //IF COLLISION IS FALSE, THE PLAYER CAN MOVE
        if(pressing){
            spriteCounter++;
            if(spriteCounter > animationDuration){
                if(spriteNumber == 1){
                    spriteNumber = 2;
                }
                else{
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
            if(!collisionOn){
                switch(direction){
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
        }
    }
    public void pickUpObject(int i){
        if(i != 999){
            String objectName = gp.obj[i].name;
            switch(objectName){
                case "Key" -> {
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    if(Math.random() > 0.8f){
                        gp.ui.showMessage("You found a perfectly fine key");
                    }
                    else{
                        gp.ui.showMessage("You found an rusty old key");
                    }
                }
                case "Door" -> {
                    if(hasKey > 0){
                        gp.playSE(3);
                        hasKey--;
                        gp.obj[i] = null;
                        gp.ui.showMessage("You managed to open the door");
                    }
                    else{
                        gp.ui.showMessage("This door seems to be locked");
                    }
                }
                case "Boots" -> {
                    gp.playSE(2);
                    speed += 2;
                    animationDuration = 10;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You feel a lot faster");
                }
                case "Chest" -> {
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                }
            }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        if(spriteNumber == 1){
            switch(direction){
                case "up" -> {
                    image = up1;
                }
                case "down" -> {
                    image = down1;
                }
                case "left" -> {
                    image = left1;
                }
                case "right" -> {
                    image = right1;
                }
            }
        }
        else{
            switch(direction){
                case "up" -> {
                    image = up2;
                }
                case "down" -> {
                    image = down2;
                }
                case "left" -> {
                    image = left2;
                }
                case "right" -> {
                    image = right2;
                }
            }
        }
        g2.drawImage(image, screenX, screenY,null);
    }
}
