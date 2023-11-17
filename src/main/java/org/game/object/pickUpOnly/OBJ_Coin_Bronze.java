package org.game.object.pickUpOnly;

import org.game.GamePanel;
import org.game.entity.Entity;

public class OBJ_Coin_Bronze extends Entity {
    public OBJ_Coin_Bronze(int x, int y, GamePanel gp) {
        super(gp);
        this.worldX = x;
        this.worldY = y;
        type = type_pickUpOnly;
        name = "Bronze Coin";
        down1 = setup("objects","bronze_coin");
        value = 1;
    }
    public void use(Entity entity){
        gp.playSE(1);
        gp.ui.addMessage("Coin + " + value);
        gp.player.coin += value;
    }
}
