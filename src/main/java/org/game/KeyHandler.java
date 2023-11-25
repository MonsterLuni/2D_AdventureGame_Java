package org.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, ePressed, shotKeyPressed,enterPressed;
    int maxCommandNum = 0;
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
        // PLAY-STATE
        if(gp.gameState == gp.playState){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> upPressed = true;
                case KeyEvent.VK_A -> leftPressed = true;
                case KeyEvent.VK_S -> downPressed = true;
                case KeyEvent.VK_D -> rightPressed = true;
                case KeyEvent.VK_E -> ePressed = true;
                case KeyEvent.VK_T -> gp.player.life--;
                case KeyEvent.VK_Z -> gp.gameState = gp.optionState;
                case KeyEvent.VK_I -> gp.gameState = gp.characterState;
                case KeyEvent.VK_SPACE -> shotKeyPressed = true;
                case KeyEvent.VK_ENTER -> {
                    if(!gp.player.attacking){
                        gp.playSE(7);
                    }
                    gp.player.attacking = true;
                }
                case KeyEvent.VK_ESCAPE -> {
                    gp.gameState = gp.pauseState;
                    setFPS(5);
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
                    setFPS(60);
                }
                case KeyEvent.VK_Q -> System.out.println("TO REMOVE");
            }
        }
        // DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE -> gp.gameState = gp.playState;
                case KeyEvent.VK_Q -> System.out.println("TO REMOVE");
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
                        case 2 -> gp.gameState = gp.optionState;
                        case 3 -> System.exit(0);
                    }
                }

            }
        }
        // SETTINGS STATE
        else if(gp.gameState == gp.settingsScreen){
            switch (e.getKeyCode()){
                case KeyEvent.VK_ESCAPE -> gp.gameState = gp.titleState;
                case KeyEvent.VK_Q -> System.out.println("TO REMOVE");
            }
        }
        // CHARACTER STATE
        else if(gp.gameState == gp.characterState){
            switch (e.getKeyCode()){
                case KeyEvent.VK_I, KeyEvent.VK_ESCAPE -> gp.gameState = gp.playState;
                case KeyEvent.VK_ENTER -> gp.player.selectItem();
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
        // OPTION STATE
        else if (gp.gameState == gp.optionState){
            switch (e.getKeyCode()){
                case KeyEvent.VK_ENTER -> enterPressed = true;
                case KeyEvent.VK_Z -> gp.gameState = gp.playState;
                case KeyEvent.VK_Q -> System.out.println("TO REMOVE");
                case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                    ui.commandNum--;
                    if(ui.commandNum < 0){
                        ui.commandNum = maxCommandNum;
                    }
                }
                case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                    ui.commandNum++;
                    if(ui.commandNum > maxCommandNum){
                        ui.commandNum = 0;
                    }
                }
                case KeyEvent.VK_A -> {
                    if(ui.substate == 0){
                        if(ui.commandNum == 1 && gp.music.volumeScale > 0){
                            gp.music.volumeScale--;
                            gp.music.checkVolume();
                        }
                        if(ui.commandNum == 2 && gp.sEffects.volumeScale > 0){
                            gp.sEffects.volumeScale--;
                            gp.playSE(1);
                        }
                    }
                }
                case KeyEvent.VK_D -> {
                    if(ui.substate == 0){
                        if(ui.commandNum == 1 && gp.music.volumeScale < 5){
                            gp.music.volumeScale++;
                            gp.music.checkVolume();
                        }
                        if(ui.commandNum == 2 && gp.sEffects.volumeScale < 5){
                            gp.sEffects.volumeScale++;
                            gp.playSE(1);
                        }
                    }
                }
            }
            switch (ui.substate){
                case 0 -> maxCommandNum = 4;
            }
        }
        // GAME OVER STATE
        else if (gp.gameState == gp.gameOverState) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_W -> {
                    ui.commandNum++;
                    if(ui.commandNum > 1){
                        ui.commandNum = 0;
                    }
                }
                case KeyEvent.VK_S -> {
                    ui.commandNum--;
                    if(ui.commandNum < 0){
                        ui.commandNum = 1;
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    if(gp.ui.commandNum == 0){
                        gp.gameState = gp.playState;
                        gp.stopMusic();
                        gp.retry();
                        gp.playMusic(0);
                    }
                    else if(gp.ui.commandNum == 1){
                        gp.gameState = gp.titleState;
                        gp.stopMusic();
                        gp.restart();
                    }
                }
            }
        }
    }
    public void setFPS(int fps){
        gp.FPS = fps;
        gp.drawInterval = (double) 1000000000 / gp.FPS;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_SPACE -> shotKeyPressed = false;
        }
    }
}