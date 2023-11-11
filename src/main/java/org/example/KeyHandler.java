package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    GamePanel gp;
    UI ui;
    public KeyHandler(GamePanel gp, UI ui){
        this.gp = gp;
        this.ui = ui;
    }
    // DEBUG
    boolean debug = false;
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_ESCAPE -> {
                if(gp.gameState == gp.playState){
                    gp.gameState = gp.pauseState;
                    gp.FPS = 5;
                }
                else if(gp.gameState == gp.pauseState){
                    gp.gameState = gp.playState;
                    gp.FPS = 60;
                }
                gp.drawInterval = (double)1000000000 / gp.FPS;
            }
            // DEBUG
            case KeyEvent.VK_F3 -> debug = !debug;
            case KeyEvent.VK_F4 -> {
                if(debug){
                    ui.gameFinished = true;
                }
            }
            case KeyEvent.VK_F2 -> {
                if(debug){
                    if(gp.musicPlaying){
                        gp.stopMusic();
                    }
                    else {
                        gp.playMusic(0);
                    }

                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
        }
    }
}
