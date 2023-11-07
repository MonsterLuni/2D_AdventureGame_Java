package org.example;

import org.example.entity.Player;
import org.example.object.SuperObject;
import org.example.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS
    final int originalTitleSize = 16; //16x16 tile
    final int scale = 3; //multiplies the pixel size of originalTitleSize
    public final int tileSize = originalTitleSize * scale; // 48x48 tile
    public final int maxScreenCol = 16; // x
    public final int maxScreenRow = 12; // y
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    //WORLDSETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    // FPS
    int FPS = 60;
    /*
    11 million fps
    15 million fps
     */
    //SYSTEM
    public TileManager tileM = new TileManager(this);
    Sound sEffects = new Sound();
    Sound music = new Sound();
    KeyHandler keyH = new KeyHandler();
    public UI ui = new UI(this);
    public CollisionDetection cChecker = new CollisionDetection(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;
    // ENTITY AND OBJECT
    public SuperObject[] obj = new SuperObject[10];
    public Player player = new Player(this,keyH);
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        System.out.println("Object loading started");
        aSetter.setObject();
        System.out.println("Object loading ended");
        System.out.println("Music loading started");
        playMusic(0);
        System.out.println("Music loading ended");
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start(); // calls run()
    }
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime(); // returns the time from the Java Engine
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if(delta >= 1){
                update(); // 1 UPDATE: update information such as character positions
                repaint(); // 2 DRAW: draw the screen with the updated information. Calls paintComponent (java function)
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
               System.out.println("FPS" + drawCount);
               drawCount = 0;
               timer = 0;
            }
        }
    }
    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g); // super means parent class aka JPanel
        Graphics2D g2 = (Graphics2D) g; // has more functions
        // TILE
        tileM.draw(g2);
        // OBJECT
        for (SuperObject object : obj) {
            if (object != null) {
                object.draw(g2, this);
            }
        }
        // PLAYER
        player.draw(g2);
        // UI
        ui.draw(g2);
        g2.dispose(); // saves memory because it's deleted
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        // sound effect (SE)
        sEffects.setFile(i);
        sEffects.play();
    }
}
