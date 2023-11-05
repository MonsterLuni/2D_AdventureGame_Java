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
    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage(){
        try{
            System.out.println("Image loading started");
            up1 = ImageIO.read(new FileInputStream("F:/Java/_2D_Game_without_Library/src/main/res/player/boy_up_1.png"));
            up2 = ImageIO.read(new FileInputStream("F:/Java/_2D_Game_without_Library/src/main/res/player/boy_up_2.png"));
            down1 = ImageIO.read(new FileInputStream("F:/Java/_2D_Game_without_Library/src/main/res/player/boy_down_1.png"));
            down2 = ImageIO.read(new FileInputStream("F:/Java/_2D_Game_without_Library/src/main/res/player/boy_down_2.png"));
            left1 = ImageIO.read(new FileInputStream("F:/Java/_2D_Game_without_Library/src/main/res/player/boy_left_1.png"));
            left2 = ImageIO.read(new FileInputStream("F:/Java/_2D_Game_without_Library/src/main/res/player/boy_left_2.png"));
            right1 = ImageIO.read(new FileInputStream("F:/Java/_2D_Game_without_Library/src/main/res/player/boy_right_1.png"));
            right2 = ImageIO.read(new FileInputStream("F:/Java/_2D_Game_without_Library/src/main/res/player/boy_right_2.png"));
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Image loading ended");
        }
    }
    public void update(){
        pressing = false;
        if(keyH.upPressed){
            direction = "up";
            y -= speed;
            pressing = true;
        }
        if(keyH.downPressed){
            direction = "down";
            y += speed;
            pressing = true;
        }
        if(keyH.leftPressed){
            direction = "left";
            x -= speed;
            pressing = true;
        }
        if(keyH.rightPressed){
            direction = "right";
            x += speed;
            pressing = true;
        }
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
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
