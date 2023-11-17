package org.game.object.upgrade;

import org.game.GamePanel;
import org.game.entity.Entity;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class OBJ_Boots extends Entity {
    public OBJ_Boots(int x, int y, GamePanel gp){
        super(gp);
        this.worldX = x;
        this.worldY = y;
        name = "Boots";
        down1 = setup("objects","shoe");
    }
}
