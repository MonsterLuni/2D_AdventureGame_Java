package org.example;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    public Sound(){
        try{
            System.out.println("Music Fetching started");
            soundURL[0] = new File("src/main/res/sound/BoyBoy.wav").toURI().toURL();
            soundURL[1] = new File("src/main/res/sound/Coin.wav").toURI().toURL();
            soundURL[2] = new File("src/main/res/sound/PowerUp.wav").toURI().toURL();
            soundURL[3] = new File("src/main/res/sound/Unlock.wav").toURI().toURL();
            soundURL[4] = new File("src/main/res/sound/Ending.wav").toURI().toURL();
        }catch (Exception e){
            System.out.println("Music Fetching error");
        }
        System.out.println("Music Fetching ended");
    }
    public void setFile(int i){
        try {
            // OPEN AUDIO FILE IN JAVA
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch (Exception e){

        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
