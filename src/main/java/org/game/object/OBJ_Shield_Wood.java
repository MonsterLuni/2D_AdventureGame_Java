package org.game.object;

import org.game.GamePanel;
import org.game.entity.Entity;

public class OBJ_Shield_Wood extends Entity {
    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);
        name = "Wood Shield";
        down1 = setup("objects","shield");
        defenseValue = 1;
    }
}