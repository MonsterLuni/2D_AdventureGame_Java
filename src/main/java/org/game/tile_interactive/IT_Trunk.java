package org.game.tile_interactive;

import org.game.GamePanel;

public class IT_Trunk extends InteractiveTile{
    public IT_Trunk(int x, int y, GamePanel gp) {
        super(gp);
        this.worldX = x;
        this.worldY = y;
        down1 = setup("tile_interactive","tree_stump");
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
