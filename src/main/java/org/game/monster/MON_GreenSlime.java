package org.game.monster;

import org.game.GamePanel;
import org.game.entity.Entity;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        type = 2;
        name = "Green Slime";
        speed = 1;
        maxLife = 8;
        life = maxLife;
        actionLockCounterNumber = 60;
        attack = 5;
        defense = 0;
        exp = 2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage(){
        up1 = setup("monster","slime_down_1");
        up2 = setup("monster","slime_down_2");
        down1 = setup("monster","slime_down_1");
        down2 = setup("monster","slime_down_2");
        left1 = setup("monster","slime_down_1");
        left2 = setup("monster","slime_down_2");
        right1 = setup("monster","slime_down_1");
        right2 = setup("monster","slime_down_2");
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
    }
    public void damageReaction(){
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
}
