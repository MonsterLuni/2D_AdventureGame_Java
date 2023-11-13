package org.game.object;

import org.game.GamePanel;
import org.game.entity.Entity;

public class OBJ_Key extends Entity {
    public OBJ_Key(int x, int y, GamePanel gp){
        super(gp);
        this.worldX = x;
        this.worldY = y;

        name = "Key";
        description = "[" + name + "]\n opens a door...";
        down1 = setup("objects", "Key");
    }
}
