package org.game;

import org.game.entity.Entity;
import org.game.entity.Player;
import org.game.object.SuperObject;
import org.game.tile.TileManager;

import javax.sound.sampled.FloatControl;
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
    int drawCount = 0;
    long timer = 0;
    int currentfps = FPS;
    boolean musicPlaying = true;
    /*
    15 million fps
    11 million fps
    8 million fps
    4.5 million fps
     */
    //SYSTEM
    public TileManager tileM = new TileManager(this);
    Sound sEffects = new Sound();
    Sound music = new Sound();
    Font arial_20;
    public UI ui = new UI(this);
    KeyHandler keyH = new KeyHandler(this, this.ui);
    public CollisionDetection cChecker = new CollisionDetection(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;
    // ENTITY AND OBJECT
    public SuperObject[] obj = new SuperObject[10];
    public Player player = new Player(this,keyH);
    public Entity[] npc = new Entity[10];
    public double drawInterval = (double) 1000000000 /FPS;

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        arial_20 = new Font("arial", Font.ITALIC, 20);
    }

    public void setupGame(){
        System.out.println("Object loading started");
        aSetter.setObject();
        System.out.println("Object loading ended");
        System.out.println("NPC loading started");
        aSetter.setNPC();
        System.out.println("NPC loading started");
        gameState = titleState;
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start(); // calls run()
    }
    @Override
    public void run() {
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

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
                currentfps = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update(){
        if(gameState == playState){
            player.update();
            // NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
        } else if (gameState == pauseState) {
            // nothing
            System.out.println("game_paused");
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g); // super means parent class aka JPanel
        Graphics2D g2 = (Graphics2D) g; // has more functions
        // DEBUG
        double drawStart = 0;
        if(keyH.debug){
            drawStart = System.nanoTime();
        }
        // TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        // OTHERS
        else{
            // TILE
            tileM.draw(g2);
            // OBJECT
            for (SuperObject object : obj) {
                if (object != null) {
                    object.draw(g2, this);
                }
            }
            // NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.draw(g2);
                }
            }
            // PLAYER
            player.draw(g2);
            // UI
            ui.draw(g2);
        }
        // DEBUG
        if(keyH.debug){
            double drawEnd = System.nanoTime();
            double passed = drawEnd - drawStart;
            g2.setFont(arial_20);
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + ui.dFormat.format(passed / 1000000) + "ms", 10, 100);
            g2.drawString("FPS: " + currentfps, 10, 130);
            g2.drawString("Music (F2): " + musicPlaying, 10, 160);
            g2.drawString("Time: " + ui.dFormat.format(ui.playTime) + "s", 10, 190);
        }
        g2.dispose(); // saves memory because it's deleted
    }
    public void playMusic(int i){
        System.out.println("MUSIC IS PLAYING");
        music.setFile(i);
        FloatControl control = (FloatControl) music.clip.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(-12.0f);
        music.play();
        music.loop();
        musicPlaying = true;
    }
    public void stopMusic(){
        music.stop();
        musicPlaying = false;
    }
    public void playSE(int i){
        // sound effect (SE)
        sEffects.setFile(i);
        sEffects.play();
    }
}