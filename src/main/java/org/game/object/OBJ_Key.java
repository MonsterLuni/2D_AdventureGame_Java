package org.game.object;

import org.game.GamePanel;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class OBJ_Key extends SuperObject {
    public OBJ_Key(int x, int y, GamePanel gp){
        name = "Key";
        this.worldX = x;
        this.worldY = y;
        try{
            image = ImageIO.read(new FileInputStream("src/main/res/objects/Key.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
