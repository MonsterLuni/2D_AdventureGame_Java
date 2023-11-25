package org.game;

import java.io.*;

public class Config {
    GamePanel gp;
    public Config(GamePanel gp){
        this.gp = gp;
    }
    public void saveConfig(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/config.txt"));

            // Full Screen
            if(gp.fullScreenOn){
                bw.write("True");
            }
            else{
                bw.write("False");
            }
            bw.newLine();

            // Music Volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // SE Volume
            bw.write(String.valueOf(gp.sEffects.volumeScale));
            bw.newLine();

            bw.close();
            //

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/config.txt"));

            String s = br.readLine();

            // Full Screen
            if(s.equals("True")){
                gp.fullScreenOn = true;
            }
            else {
                gp.fullScreenOn = false;
            }

            // Music Volume
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            // SE Volume
            s = br.readLine();
            gp.sEffects.volumeScale = Integer.parseInt(s);

            br.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
