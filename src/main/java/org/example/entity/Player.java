package org.example.entity;

import org.example.GamePanel;
import org.example.KeyHandler;

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
        try{
            System.out.println("Player loading started");
            up1 = ImageIO.read(new FileInputStream("src/main/res/player/boy_up_1.png"));
            up2 = ImageIO.read(new FileInputStream("src/main/res/player/boy_up_2.png"));
            down1 = ImageIO.read(new FileInputStream("src/main/res/player/boy_down_1.png"));
            down2 = ImageIO.read(new FileInputStream("src/main/res/player/boy_down_2.png"));
            left1 = ImageIO.read(new FileInputStream("src/main/res/player/boy_left_1.png"));
            left2 = ImageIO.read(new FileInputStream("src/main/res/player/boy_left_2.png"));
            right1 = ImageIO.read(new FileInputStream("src/main/res/player/boy_right_1.png"));
            right2 = ImageIO.read(new FileInputStream("src/main/res/player/boy_right_2.png"));
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Player loading error");
        }
        System.out.println("Player loading ended");
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
        //IF COLLISION IS FALSE, THE PLAYER CAN MOVE
        if(pressing){
            spriteCounter++;
            if(spriteCounter > 15){
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
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
