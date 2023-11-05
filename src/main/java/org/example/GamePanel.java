package org.example;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS
    final int originalTitleSize = 16; //16x16 tile
    final int scale = 3; //multiplies the pixel size of originalTitleSize
    final int tileSize = originalTitleSize * scale; // 48x48 tile
    final int maxScreenCol = 16; // x
    final int maxScreenRow = 12; // y
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    // FPS
    int FPS = 60;
    /*
    11 million fps
     */
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    //Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
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
                // 1 UPDATE: update information such as character positions
                update();
                // 2 DRAW: draw the screen with the updated information
                repaint(); // calls paintComponent (java function)
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
        if(keyH.upPressed){
            playerY -= playerSpeed;
        }
        if(keyH.downPressed){
            playerY += playerSpeed;
        }
        if(keyH.leftPressed){
            playerX -= playerSpeed;
        }
        if(keyH.rightPressed){
            playerX += playerSpeed;
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g); // super means parent class aka JPanel
        Graphics2D g2 = (Graphics2D) g; // has more functions
        g2.setColor(Color.white);
        g2.fillRect(playerX,playerY,tileSize,tileSize);
        g2.dispose(); // saves memory because it's deleted
    }
}
