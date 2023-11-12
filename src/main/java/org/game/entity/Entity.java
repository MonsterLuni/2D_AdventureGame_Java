package org.game.entity;

import org.game.GamePanel;
import org.game.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public abstract class Entity {
    GamePanel gp;
    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public int actionLockCounter = 0;
    public int actionLockCounterNumber = 120;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    String[] dialogues = new String[20];
    int dialogueIndex = 0;
    // From former SuperObject
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;

    // CHARACTER STATUS
    public int maxLife;
    public int life;

    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public BufferedImage setup(String packagePath, String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(new FileInputStream("src/main/res/" + packagePath + "/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            System.out.println("Player loading error");
            e.printStackTrace();
        }
        return image;
    }
    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(     worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            BufferedImage image = null;
            switch(direction){
                case "up" -> image = up1;
                case "down" -> image = down1;
                case "left" -> image = left1;
                case "right" -> image = right1;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
    public void setAction(){
        actionLockCounter++;
        if(actionLockCounter == actionLockCounterNumber){
            Random random = new Random();
            int i = random.nextInt(100)+1;
            if(i <= 25){
                direction = "up";
            }
            if(i > 25 && i <= 50){
                direction = "down";
            }
            if(i > 50 && i <= 75){
                direction = "left";
            }
            if(i > 75){
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }
    public void update(){
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkPlayer(this);

        spriteNumber = 1;

        if(!collisionOn){
            switch(direction){
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }
    }
    public void speak(){
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        switch (gp.player.direction){
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";

        }
    }
}
