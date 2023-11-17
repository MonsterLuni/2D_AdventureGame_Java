package org.game.object.pickUpOnly;

import org.game.GamePanel;
import org.game.entity.Entity;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(int x, int y, GamePanel gp){
        super(gp);
        type = type_pickUpOnly;
        this.worldX = x;
        this.worldY = y;
        name = "Heart";
        value = 2;
        down1  = setup("objects","heart_full");
        image = setup("objects","heart_full");
        image2 = setup("objects","heart_half");
        image3 = setup("objects","heart_broken");
    }
    @Override
    public void use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("Life + " + value);
        entity.life += value;
    }
}
