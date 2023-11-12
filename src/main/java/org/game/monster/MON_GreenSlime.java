package org.game.monster;

import org.game.GamePanel;
import org.game.entity.Entity;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        type = 2;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        actionLockCounterNumber = 60;

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
}
