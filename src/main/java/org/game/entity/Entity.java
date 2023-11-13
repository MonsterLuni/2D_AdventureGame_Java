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
    public GamePanel gp;
    public Entity(GamePanel gp){
        this.gp = gp;
    }

    // INTEGERS
    public int worldX, worldY, screenX, screenY, speed, maxLife, life, type, solidAreaDefaultX, solidAreaDefaultY;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_shield = 4;
    public final int type_consumable = 5;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public int attackValue;
    public int defenseValue;
    public String description = "";

    // COUNTER
    public int actionLockCounter = 0;
    public int actionLockCounterNumber = 120;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public int invincibleCounter = 0;
    public int dyingCounter = 0;
    public int hpBarCounter = 0;

    // BOOLEANS
    public boolean attacking = false;
    public boolean collisionOn = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean invincible = false;
    public boolean collision = false;
    boolean hpBarOn = false;

    // RECTANGLES / BUFFERS
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, image, image2, image3;
    public BufferedImage attackUp1, attackUp2, attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2;

    // DIALOGUE
    public String direction = "down";
    public String name;
    int dialogueIndex = 0;
    String[] dialogues = new String[20];

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
            // Monster HP bar
            if(type == 2 && hpBarOn){
                // find current length of bar
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-2,screenY-17,gp.tileSize + 3,14);
                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY - 15 ,(int)hpBarValue,10);
                hpBarCounter++;
                if(hpBarCounter > 500){
                    hpBarOn = false;
                    hpBarCounter = 0;
                }
            }
            if(invincible){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
                hpBarOn = true;
                hpBarCounter = 0;
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
            hpBarOn = false;
            hpBarCounter = 0;
        }
    }
    public void damageReaction(){}
    public void setAction(){}
    public void update(){
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer){
            if(!gp.player.invincible){
                gp.playSE(6);

                int damage = attack - gp.player.defense;
                if(damage < 0){
                    damage = 0;
                }

                gp.player.life -= damage;
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
