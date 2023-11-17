package org.game.object.consumable;

import org.game.GamePanel;
import org.game.entity.Entity;

public class OBJ_Potion_Red extends Entity {
    public OBJ_Potion_Red(int x, int y, GamePanel gp) {
        super(gp);
        this.worldX = x;
        this.worldY = y;
        type = type_consumable;
        name = "Red Potion";
        description = "Gives you " + value + " health";
        down1 = setup("objects","potion_red");
        value = 5;
    }
    public void use(Entity entity){
        //gp.ui.currentDialogue = "You drink the " + name + "!\n" + "Your life has been recovered by " + value + ".";
        gp.playSE(2);
        entity.life += value;
        if(entity.life > entity.maxLife){
            entity.life = entity.maxLife;
        }
    }
}
