package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    // DEBUG
    boolean debug = false;
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W -> {
                upPressed = true;
            }
            case KeyEvent.VK_A -> {
                leftPressed = true;
            }
            case KeyEvent.VK_S -> {
                downPressed = true;
            }
            case KeyEvent.VK_D -> {
                rightPressed = true;
            }
            // DEBUG
            case KeyEvent.VK_F3 -> {
                if(!debug){
                    debug = true;
                }
                else{
                    debug = false;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W -> {
                upPressed = false;
            }
            case KeyEvent.VK_A -> {
                leftPressed = false;
            }
            case KeyEvent.VK_S -> {
                downPressed = false;
            }
            case KeyEvent.VK_D -> {
                rightPressed = false;
            }
        }
    }
}
