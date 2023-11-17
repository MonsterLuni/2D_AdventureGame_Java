package org.game.object.pickUpOnly;

import org.game.GamePanel;
import org.game.entity.Entity;

public class OBJ_ManaCrystal extends Entity {
    public OBJ_ManaCrystal(int x, int y, GamePanel gp) {
        super(gp);
        type = type_pickUpOnly;
        this.worldX = x;
        this.worldY = y;
        name = "Mana Crystal";
        value = 10;
        down1  = setup("objects","manacrystal_full");
        image = setup("objects","manacrystal_full");
        image2 = setup("objects","manacrystal_blank");
    }
    @Override
    public void use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("Mana + " + value);
        entity.mana += value;
    }
}
