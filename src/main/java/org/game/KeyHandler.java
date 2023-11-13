package org.game;

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
                case KeyEvent.VK_T -> gp.player.life--;
                case KeyEvent.VK_I -> gp.gameState = gp.characterState;
                case KeyEvent.VK_ENTER -> {
                    if(!gp.player.attacking){
                        gp.playSE(7);
                    }
                    gp.player.attacking = true;
                }
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
        // TITLE STATE
        else if(gp.gameState == gp.titleState){
            switch (e.getKeyCode()){
                case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                    ui.commandNum--;
                    if(ui.commandNum < 0){
                        gp.ui.commandNum = 3;
                    }
                    ui.blinkOn = 0;
                }
                case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                    ui.commandNum++;
                    if(ui.commandNum > 3){
                        ui.commandNum = 0;
                    }
                    ui.blinkOn = 0;
                }
                case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> {
                    switch (ui.commandNum){
                        case 0 -> {
                            gp.gameState = gp.playState;
                            System.out.println("Music loading started");
                            gp.playMusic(0);
                            System.out.println("Music loading ended");
                        }
                        case 1 -> System.out.println("Coming soon...");
                        case 2 -> gp.gameState = gp.settingsScreen;
                        case 3 -> System.exit(0);
                    }
                }

            }
        }
        // SETTINGS STATE
        else if(gp.gameState == gp.settingsScreen){
            switch (e.getKeyCode()){
                case KeyEvent.VK_ESCAPE -> {
                    gp.gameState = gp.titleState;
                }
            }
        }
        // CHARACTER STATE
        else if(gp.gameState == gp.characterState){
            switch (e.getKeyCode()){
                case KeyEvent.VK_I -> gp.gameState = gp.playState;
                case KeyEvent.VK_W -> {
                    if(gp.ui.slotRow != 0){
                        gp.ui.slotRow--;
                    }
                }
                case KeyEvent.VK_A-> {
                    if(gp.ui.slotCol != 0){
                        gp.ui.slotCol--;
                    }
                }
                case KeyEvent.VK_S -> {
                    if(gp.ui.slotRow != 3) {
                        gp.ui.slotRow++;
                    }
                }
                case KeyEvent.VK_D -> {
                    if(gp.ui.slotCol != 4) {
                        gp.ui.slotCol++;
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
