package org.game.monster;

import org.game.GamePanel;
import org.game.entity.Entity;
import org.game.object.projectile.OBJ_Coconut;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MON_RedSlime extends Entity {
    public MON_RedSlime(GamePanel gp) {
        super(gp);
        type = type_monster;
        name = "Red Slime";
        speed = 2;
        maxLife = 16;
        life = maxLife;
        actionLockCounterNumber = 60;
        attack = 8;
        defense = 2;
        exp = 20;
        projectile = new OBJ_Coconut(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage(){
        up1 = setup("monster","red_slime_down_1");
        up2 = setup("monster","red_slime_down_2");
        down1 = setup("monster","red_slime_down_1");
        down2 = setup("monster","red_slime_down_2");
        left1 = setup("monster","red_slime_down_1");
        left2 = setup("monster","red_slime_down_2");
        right1 = setup("monster","red_slime_down_1");
        right2 = setup("monster","red_slime_down_2");
    }
    public void setAction(){
        actionLockCounter++;
        if(actionLockCounter == actionLockCounterNumber){
            Random random = new Random();
            int i = random.nextInt(100)+1;
            if(i <= 25){
                direction = "up";
            }
            else if(i <= 50){
                direction = "down";
            }
            else if(i <= 75){
                direction = "left";
            }
            else{
                direction = "right";
            }
            actionLockCounter = 0;
        }
        int i = new Random().nextInt(100)+1;
        if(i > 99 && !projectile.alive && shotAvailableCounter == 30){
            projectile.set(worldX,worldY,direction,true,this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }
    public void damageReaction(){
        actionLockCounter = 0;
        switch (gp.player.direction){
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }
    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(     worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            BufferedImage image = null;
            if(spriteNumber == 1){
                switch(direction){
                    case "up" -> image = up1;
                    case "down" -> image = down1;
                    case "left" -> image = left1;
                    case "right" -> image = right1;
                }
            }
            else {
                switch(direction){
                    case "up" -> image = up2;
                    case "down" -> image = down2;
                    case "left" -> image = left2;
                    case "right" -> image = right2;
                }
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
}
