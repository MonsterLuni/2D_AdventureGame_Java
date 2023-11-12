package org.game.object;

import org.game.GamePanel;
import org.game.entity.Entity;

public class OBJ_Chest extends Entity {
    public OBJ_Chest(int x, int y, GamePanel gp){
        super(gp);
        this.worldX = x;
        this.worldY = y;
        name = "Chest";
        down1 = setup("objects","chest");
    }
}
