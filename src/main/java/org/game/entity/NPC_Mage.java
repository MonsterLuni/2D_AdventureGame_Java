package org.game.entity;

import org.game.GamePanel;

public class NPC_Mage extends Entity{
    public NPC_Mage(GamePanel gp){
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }
    public void getImage(){
        System.out.println("npc loading started");
        up1 = setup("npc","mage_up_1");
        down1 = setup("npc","mage_down_1");
        left1 = setup("npc","mage_left_1");
        right1 = setup("npc","mage_right_1");
        System.out.println("npc loading ended");
    }
    public void setDialogue(){
        dialogues[0] = "Hello..";
        dialogues[1] = "It's dangerous to be here...";
        dialogues[2] = "What are you doing here?";
        dialogues[3] = "Crazy? I was Crazy once, \n they locked me in a Room. \n a rubber room.";
        dialogues[4] = "Don't go in the forest!";
    }
    @Override
    public void speak(){
        super.speak();
    }
}
