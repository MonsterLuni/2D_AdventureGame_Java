package org.example.object;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class OBJ_Door extends SuperObject {
    public OBJ_Door(int x,int y){
        name = "Door";
        this.worldX = x;
        this.worldY = y;
        try{
            image = ImageIO.read(new FileInputStream("src/main/res/objects/door.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
