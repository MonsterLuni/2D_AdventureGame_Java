package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, ePressed;
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
        // PLAYSTATE
        if(gp.gameState == gp.playState){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> upPressed = true;
                case KeyEvent.VK_A -> leftPressed = true;
                case KeyEvent.VK_S -> downPressed = true;
                case KeyEvent.VK_D -> rightPressed = true;
                case KeyEvent.VK_E -> ePressed = true;
                case KeyEvent.VK_ESCAPE -> {
                    gp.gameState = gp.pauseState;
                    gp.FPS = 5;
                    gp.drawInterval = (double) 1000000000 / gp.FPS;
                }
                // DEBUG
                case KeyEvent.VK_F3 -> debug = !debug;
                case KeyEvent.VK_F4 -> {
                    if (debug) {
                        ui.gameFinished = true;
                    }
                }
                case KeyEvent.VK_F2 -> {
                    if (gp.musicPlaying) {
                        gp.stopMusic();
                    } else {
                        gp.playMusic(0);
                    }
                }
            }
        }
        // PAUSE STATE
        else if(gp.gameState == gp.pauseState){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE -> {
                    gp.gameState = gp.playState;
                    gp.FPS = 60;
                    gp.drawInterval = (double) 1000000000 / gp.FPS;
                }
            }
        }
        // DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE -> gp.gameState = gp.playState;
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
