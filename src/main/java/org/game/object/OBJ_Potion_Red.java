package org.game.object;

import org.game.GamePanel;
import org.game.entity.Entity;

public class OBJ_Potion_Red extends Entity {
    int value = 5;
    public OBJ_Potion_Red(int x, int y, GamePanel gp) {
        super(gp);
        this.worldX = x;
        this.worldY = y;
        type = type_consumable;
        name = "Red Potion";
        description = "Gives you " + value + " health";
        down1 = setup("objects","potion_red");
    }
    public void use(Entity entity){
        //gp.ui.currentDialogue = "You drink the " + name + "!\n" + "Your life has been recovered by " + value + ".";
        entity.life += value;
        if(gp.player.life > gp.player.maxLife){
            entity.life = gp.player.maxLife;
        }
        gp.playSE(2);
    }
}
