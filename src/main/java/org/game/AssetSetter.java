package org.game;

import org.game.entity.NPC_Mage;
import org.game.monster.MON_GreenSlime;
import org.game.monster.MON_RedSlime;
import org.game.object.*;
import org.game.object.consumable.OBJ_Potion_Red;
import org.game.object.pickUpOnly.OBJ_Coin_Bronze;
import org.game.object.pickUpOnly.OBJ_Heart;
import org.game.object.pickUpOnly.OBJ_ManaCrystal;
import org.game.object.upgrade.OBJ_Axe;
import org.game.object.upgrade.OBJ_Boots;
import org.game.object.upgrade.OBJ_Shield_diamond;
import org.game.tile_interactive.IT_RottenTree;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){
        gp.obj[0] = new OBJ_Key(23 * gp.tileSize, 7 * gp.tileSize, this.gp);
        gp.obj[1] = new OBJ_Key(23 * gp.tileSize, 40 * gp.tileSize, this.gp);
        gp.obj[2] = new OBJ_Key(38 * gp.tileSize, 8 * gp.tileSize, this.gp);
        gp.obj[3] = new OBJ_Door(10 * gp.tileSize, 11 * gp.tileSize, this.gp);
        gp.obj[4] = new OBJ_Door(8 * gp.tileSize, 28 * gp.tileSize, this.gp);
        gp.obj[5] = new OBJ_Door(12 * gp.tileSize, 22 * gp.tileSize, this.gp);
        gp.obj[6] = new OBJ_Chest(10 * gp.tileSize, 7 * gp.tileSize, this.gp);
        gp.obj[7] = new OBJ_Boots(37 * gp.tileSize, 42 * gp.tileSize, this.gp);
        gp.obj[8] = new OBJ_Axe(37 * gp.tileSize, 43 * gp.tileSize, this.gp);
        gp.obj[9] = new OBJ_Shield_diamond(40 * gp.tileSize, 24 * gp.tileSize, this.gp);
        gp.obj[10] = new OBJ_Potion_Red(23 * gp.tileSize, 28 * gp.tileSize, this.gp);
        gp.obj[11] = new OBJ_Coin_Bronze(23 * gp.tileSize, 30 * gp.tileSize, this.gp);
        gp.obj[12] = new OBJ_Coin_Bronze(23 * gp.tileSize, 34 * gp.tileSize, this.gp);
        gp.obj[13] = new OBJ_Heart(23 * gp.tileSize, 35 * gp.tileSize, this.gp);
        gp.obj[14] = new OBJ_ManaCrystal(23 * gp.tileSize, 36 * gp.tileSize, this.gp);
    }
    public void setNPC(){
        gp.npc[0] = new NPC_Mage(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }
    public void setMonster(){
        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = gp.tileSize*23;
        gp.monster[0].worldY = gp.tileSize*9;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = gp.tileSize*23;
        gp.monster[1].worldY = gp.tileSize*12;

        gp.monster[2] = new MON_GreenSlime(gp);
        gp.monster[2].worldX = gp.tileSize*24;
        gp.monster[2].worldY = gp.tileSize*13;

        gp.monster[3] = new MON_GreenSlime(gp);
        gp.monster[3].worldX = gp.tileSize*23;
        gp.monster[3].worldY = gp.tileSize*13;

        gp.monster[4] = new MON_RedSlime(gp);
        gp.monster[4].worldX = gp.tileSize*23;
        gp.monster[4].worldY = gp.tileSize*8;

        gp.monster[5] = new MON_RedSlime(gp);
        gp.monster[5].worldX = gp.tileSize*23;
        gp.monster[5].worldY = gp.tileSize*7;
    }
    public void setInteractiveTile(){
        gp.iTile[0] = new IT_RottenTree(39* gp.tileSize,21* gp.tileSize,gp);
        gp.iTile[1] = new IT_RottenTree(40* gp.tileSize,21* gp.tileSize,gp);
    }
}
