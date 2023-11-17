package org.game.object.upgrade;

import org.game.GamePanel;
import org.game.entity.Entity;

public class OBJ_Axe extends Entity {

    public OBJ_Axe(int x, int y, GamePanel gp) {
        super(gp);
        this.worldX = x;
        this.worldY = y;
        name = "Diamond Axe";
        type = type_axe;
        description = "[" + name + "]\nA almost perfect\nAxe";
        down1 = setup("objects","axe");
        attackValue = 2;
        attackArea.width = 20;
        attackArea.height = 20;
    }
}
