package org.game;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
public class Main {
    public static JFrame window;
    public static void main(String[] args) {
        window = new JFrame();
        ImageIcon img = new ImageIcon("src/main/res/icon/Key.png");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");
        window.setIconImage(img.getImage());

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if(gamePanel.fullScreenOn){
            window.setUndecorated(true);
        }

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}