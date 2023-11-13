package org.game.object;

import org.game.GamePanel;
import org.game.entity.Entity;

public class OBJ_Sword_Normal extends Entity {
    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);
        name = "Normal Sword";
        type = type_sword;
        description = "[" + name + "]\nAn old sword";
        down1 = setup("objects","sword");
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
    }

}
