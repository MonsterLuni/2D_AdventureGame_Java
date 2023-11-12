package org.game.object;

import org.game.GamePanel;
import org.game.entity.Entity;

public class OBJ_Door extends Entity {
    public OBJ_Door(int x, int y, GamePanel gp){
        super(gp);
        this.worldX = x;
        this.worldY = y;
        name = "Door";
        down1 = setup("objects","door");
        collision = true;
        // Make door top smaller
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
