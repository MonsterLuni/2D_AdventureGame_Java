package org.game.object;

import org.game.GamePanel;
import org.game.entity.Entity;

public class OBJ_Shield_diamond extends Entity {
    public OBJ_Shield_diamond(int x, int y, GamePanel gp) {
        super(gp);
        name = "Diamond Shield";
        this.worldX = x;
        this.worldY = y;
        type = type_shield;
        description = "[" + name + "]\nAn new diamond shield";
        down1 = setup("objects","shield_diamond");
        defenseValue = 2;
    }
}
