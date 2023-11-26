package org.game;

import org.game.entity.Entity;
import org.game.entity.Player;
import org.game.tile.TileManager;
import org.game.tile_interactive.InteractiveTile;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS
    final int originalTitleSize = 16; //16x16 tile
    final int scale = 3; //multiplies the pixel size of originalTitleSize
    public final int tileSize = originalTitleSize * scale; // 48x48 tile
    public final int maxScreenCol = 20; // x
    public final int maxScreenRow = 12; // y
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    // FOR FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    // FPS
    int FPS = 60;
    int drawCount = 0;
    long timer = 0;
    int currentFps = FPS;
    boolean musicPlaying = true;
    /*
    * In Laptop BiCT
    * -
    * -
    * -
    * 300'000 thousand fps
    * -
    * -
    * -
    */
    /* Laptop At Home
    15 million fps
    11 million fps
    8 million fps
    4.5 million fps
    3.5 million fps
    1 million fps
    1.6 million fps
     */
    //SYSTEM
    public TileManager tileM = new TileManager(this);
    public Sound sEffects = new Sound();
    public Sound music = new Sound();
    Font arial_20;
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    KeyHandler keyH = new KeyHandler(this, this.ui);
    public CollisionDetection cChecker = new CollisionDetection(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;
    public final int maxMap = 10;
    public int currentMap = 0;
    // ENTITY AND OBJECT
    public Player player = new Player(this,keyH);

    public Entity[][] obj = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][10];
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][20];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    public double drawInterval = (double) 1000000000 /FPS;
    // GAME STATE
    public int gameState;

    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int settingsScreen = 4;
    public final int characterState = 5;
    public final int optionState = 6;
    public final int gameOverState = 7;
    public final int transitionState = 8;
    public boolean fullScreenOn = false;
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        arial_20 = new Font("arial", Font.ITALIC, 20);
    }
    public void retry(){
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        aSetter.setMonster();
        aSetter.setNPC();
    }
    public void restart(){
        player.setDefaultValues();
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
    }
    public void setupGame(){
        System.out.println("Object loading started");
        aSetter.setObject();
        System.out.println("Object loading ended");
        System.out.println("NPC loading started");
        aSetter.setNPC();
        System.out.println("NPC loading started");
        System.out.println("Monster loading started");
        aSetter.setMonster();
        System.out.println("Monster loading started");
        System.out.println("iTiles loading started");
        aSetter.setInteractiveTile();
        System.out.println("iTiles loading ended");
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight,BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if(fullScreenOn){
            setFullScreen();
        }
    }
    public void setFullScreen(){
        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
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
                drawToTempScreen(g2);
                drawToScreen();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                currentFps = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {
        if(gameState == playState){
            player.update();
            // NPC
            for (Entity entity : npc[currentMap]) {
                if (entity != null) {
                    entity.update();
                }
            }
            // Monster
            for (int i = 0; i < monster[1].length; i++) {
                //TODO: What the actual fuck is going on here
                if (monster[currentMap][i] != null) {
                    if(monster[currentMap][i].alive && !monster[currentMap][i].dying){
                        monster[currentMap][i].update();
                    }
                    else {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if(projectileList.get(i).alive){
                        projectileList.get(i).update();
                    }
                    if(!projectileList.get(i).alive){
                        projectileList.remove(i);
                    }
                }
            }
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if(particleList.get(i).alive){
                        particleList.get(i).update();
                    }
                    if(!particleList.get(i).alive){
                        particleList.remove(i);
                    }
                }
            }
            for (InteractiveTile interactiveTile : iTile[currentMap]) {
                if (interactiveTile != null) {
                    interactiveTile.update();
                }
            }
        } else if (gameState == pauseState) {
            // nothing
            System.out.println("game_paused");
        }
    }
    public void drawToTempScreen(Graphics g){
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
            // Interactive Tile
            for (InteractiveTile interactiveTile : iTile[currentMap]) {
                if (interactiveTile != null) {
                    interactiveTile.draw(g2);
                }
            }

            // ADD ENTITIES TO LIST
            entityList.add(player);
            for (Entity entity : npc[currentMap]) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity monster : monster[currentMap]) {
                if (monster != null) {
                    entityList.add(monster);
                }
            }
            for (Entity object : obj[currentMap]) {
                if (object != null) {
                    entityList.add(object);
                }
            }
            for (Entity value : projectileList) {
                if (value != null) {
                    entityList.add(value);
                }
            }
            for (Entity value : particleList) {
                if (value != null) {
                    entityList.add(value);
                }
            }
            // SORT
            entityList.sort(Comparator.comparingInt(e -> e.worldY));
            // DRAW ENTITIES
            for (Entity entity : entityList) {
                entity.draw(g2);
            }
            // EMPTY ENTITY LIST
            for (int i = 0; i < entityList.size(); i++){
                entityList.remove(i);
            }
            // UI
            ui.draw(g2);
        }
        // DEBUG
        if(keyH.debug){
            double drawEnd = System.nanoTime();
            double passed = drawEnd - drawStart;
            g2.setFont(arial_20);
            g2.setColor(Color.white);
            int defaultNum = 160;
            int step = 30;
            g2.drawString("Draw Time: " + ui.dFormat.format(passed / 1000000) + "ms", 10, defaultNum);
            g2.drawString("FPS: " + currentFps, 10, defaultNum + step);
            g2.drawString("Music (F2): " + musicPlaying, 10, defaultNum + step*2);
            g2.drawString("Time: " + ui.dFormat.format(ui.playTime) + "s", 10, defaultNum + step*3);
            g2.drawString("Invincible: " + player.invincible, 10, defaultNum + step*4);
            g2.drawString("InvincibleCounter: " + player.invincibleCounter, 10, defaultNum + step*5);
            g2.drawString("EntityCount: " + entityList.size(), 10, defaultNum + step*6);
            g2.drawString("Y Row : " + (player.worldY / tileSize + 1), 10, defaultNum + step*7);
            g2.drawString("X Col : " + player.worldX / tileSize, 10, defaultNum + step*8);
        }
    }
    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0, screenWidth2,screenHeight2,null);
        g.dispose();
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
