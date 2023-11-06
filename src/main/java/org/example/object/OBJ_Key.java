package org.example.object;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class OBJ_Key extends SuperObject {
    public OBJ_Key(int x,int y){
        name = "Key";
        this.worldX = x;
        this.worldY = y;
        try{
            image = ImageIO.read(new FileInputStream("src/main/res/objects/Key.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
