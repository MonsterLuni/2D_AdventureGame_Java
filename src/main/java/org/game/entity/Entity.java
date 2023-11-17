package org.game.entity;

import org.game.GamePanel;
import org.game.UtilityTool;
import org.game.particle.Particle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class Entity {
    public GamePanel gp;
    public Entity(GamePanel gp){
        this.gp = gp;
    }

    // INTEGERS
    public int worldX, worldY, screenX, screenY, speed, maxLife, life, type, solidAreaDefaultX, solidAreaDefaultY, maxMana, mana, useCost,level;
    public int attackValue, defenseValue, coin, nextLevelExp, exp, defense, attack, dexterity, strength, value;
    // a public final int type_player = 0;
    // a public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickUpOnly = 7;
    public Projectile projectile;
    public Entity currentWeapon;
    public Entity currentShield;
    public String description = "";

    // COUNTER
    public int actionLockCounter = 0;
    public int shotAvailableCounter = 0;
    public int animationDuration = 60;
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
    public boolean hpBarOn = false;

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
            g2.drawImage(image, screenX, screenY, null);
            // Reset Alpha
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        }
    }
    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        if(dyingCounter % 15 == 0){
            if(dyingCounter % 2 == 0){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0f));
            }
            else{
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
            }
        } else if (dyingCounter > 40) {
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
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer){
            damagePlayer(attack);
        }
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 30){
                invincible = false;
                invincibleCounter = 0;
            }
        }
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
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
    }
    public void damagePlayer(int attack){
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
    public void use(Entity entity){}
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
    public void checkDrop(){

    }
    public void dropItem(Entity droppedItem){
        for (int i = 0; i < gp.obj.length; i++){
            if(gp.obj[i] == null){
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX; // the ones from the dead monster
                gp.obj[i].worldY = worldY; // the ones from the dead monster
                break;
            }
        }
    }
    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp,target,color,size,speed,maxLife, -2, -1);
        gp.particleList.add(p1);
        Particle p2 = new Particle(gp,target,color,size,speed,maxLife, 2, -1);
        gp.particleList.add(p2);
        Particle p3 = new Particle(gp,target,color,size,speed,maxLife, -2, 1);
        gp.particleList.add(p3);
        Particle p4 = new Particle(gp,target,color,size,speed,maxLife, 2, 1);
        gp.particleList.add(p4);
    }
    public Color getParticleColor(){
        return null;
    }
    public int getParticleSize(){
        return 0;
    }
    public int getParticleSpeed(){
        return 0;
    }
    public int getParticleMaxLife(){
        return 0;
    }
}
