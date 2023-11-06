package org.example.object;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class OBJ_Chest extends SuperObject {
    public OBJ_Chest(int x,int y){
        name = "Chest";
        this.worldX = x;
        this.worldY = y;
        try{
            image = ImageIO.read(new FileInputStream("src/main/res/objects/chest.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
