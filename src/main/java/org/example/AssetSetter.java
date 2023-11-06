package org.example;

import org.example.object.OBJ_Chest;
import org.example.object.OBJ_Door;
import org.example.object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){
        gp.obj[0] = new OBJ_Key(23 * gp.tileSize, 7 * gp.tileSize);
        gp.obj[1] = new OBJ_Key(23 * gp.tileSize, 40 * gp.tileSize);
        gp.obj[2] = new OBJ_Key(38 * gp.tileSize, 8 * gp.tileSize);
        gp.obj[3] = new OBJ_Door(10 * gp.tileSize, 11 * gp.tileSize);
        gp.obj[4] = new OBJ_Door(8 * gp.tileSize, 28 * gp.tileSize);
        gp.obj[5] = new OBJ_Door(12 * gp.tileSize, 22 * gp.tileSize);
        gp.obj[6] = new OBJ_Chest(10 * gp.tileSize, 7 * gp.tileSize);
    }
}
