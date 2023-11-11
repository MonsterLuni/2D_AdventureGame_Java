package org.example.entity;

import org.example.GamePanel;

public class NPC_Mage extends Entity{
    public NPC_Mage(GamePanel gp){
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
    }
    public void getImage(){
        System.out.println("npc loading started");
        up1 = setup("npc","mage_up_1");
        down1 = setup("npc","mage_down_1");
        left1 = setup("npc","mage_left_1");
        right1 = setup("npc","mage_right_1");
        System.out.println("npc loading ended");
    }
}
