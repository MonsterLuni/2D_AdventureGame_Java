package org.example.tile;

import org.example.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap("src/main/res/maps/map01.txt");
    }
    public void getTileImage(){
        try{
            System.out.println("Tile loading started");
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new FileInputStream("src/main/res/tiles/grass.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new FileInputStream("src/main/res/tiles/wall.png"));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new FileInputStream("src/main/res/tiles/water.png"));
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Tile loading error");
        }
        System.out.println("Tile loading ended");
    }
    public void loadMap(String filePath){
        try{
            System.out.println("Map loading started");
            InputStream is = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (col < gp.maxScreenCol && row < gp.maxScreenRow){
                String line = br.readLine();
                while(col < gp.maxScreenCol){
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Map loading error");
        }
        System.out.println("Map loading ended");
    }
    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;
            if(col == gp.maxScreenCol){
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
