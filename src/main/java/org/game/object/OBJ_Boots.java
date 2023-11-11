package org.game.object;

import org.game.GamePanel;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class OBJ_Boots extends SuperObject{
    public OBJ_Boots(int x,int y, GamePanel gp){
        name = "Boots";
        this.worldX = x;
        this.worldY = y;
        try{
            image = ImageIO.read(new FileInputStream("src/main/res/objects/shoe.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
