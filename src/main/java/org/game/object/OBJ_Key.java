package org.game.object;

import org.game.GamePanel;
import org.game.entity.Entity;

public class OBJ_Key extends Entity {
    public OBJ_Key(int x, int y, GamePanel gp){
        super(gp);
        this.worldX = x;
        this.worldY = y;

        name = "Key";
        down1 = setup("objects", "Key");
    }
}
