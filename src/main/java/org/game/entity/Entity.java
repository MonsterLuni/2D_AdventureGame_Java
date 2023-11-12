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
    public int screenX;
    public int screenY;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2;
    public int actionLockCounter = 0;
    public int actionLockCounterNumber = 120;
    public String direction = "down";
    public boolean attacking = false;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public boolean invincible = false;
    public int invincibleCounter = 0;
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
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;
    public int type;
    public BufferedImage setup(String packagePath, String imageName, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(new FileInputStream("src/main/res/" + packagePath + "/" + imageName + ".png"));
            image = uTool.scaleImage(image, width, height);
        }catch (IOException e){
            System.out.println("Player loading error");
            e.printStackTrace();
        }
        return image;
    }
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
            if(invincible){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
            }
            if(dying){
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY,gp.tileSize, gp.tileSize, null);
            // Reset Alpha
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        }
    }
    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        if(dyingCounter % 5 == 0){
            if(dyingCounter % 2 == 0){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0f));
            }
            else{
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
            }
        } else if (dyingCounter > 40) {
            dying = false;
            alive = false;
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
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer){
            if(!gp.player.invincible){
                gp.player.life--;
                gp.player.invincible = true;
            }
        }
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 30){
                invincible = false;
                invincibleCounter = 0;
            }
        }

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
