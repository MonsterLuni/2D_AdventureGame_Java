package org.game.entity;

import org.game.GamePanel;
import org.game.object.pickUpOnly.OBJ_ManaCrystal;
public class NPC_Merchant extends Entity{
    public NPC_Merchant(GamePanel gp){
        super(gp);
        direction = "down";
        speed = 0;
        getImage();
        setDialogue();
        setItems();
    }
    public void getImage(){
        System.out.println("merchant loading started");
        up1 = setup("npc","merchant_down_1");
        up2 = setup("npc","merchant_down_2");
        down1 = setup("npc","merchant_down_1");
        down2 = setup("npc","merchant_down_2");
        left1 = setup("npc","merchant_down_1");
        left2 = setup("npc","merchant_down_2");
        right1 = setup("npc","merchant_down_1");
        right2 = setup("npc","merchant_down_2");
        System.out.println("merchant loading ended");
    }
    public void setDialogue(){
        dialogues[0] = "You found me... \nI have some good stuff for you...\nDo you want to trade?";
    }
    public void setItems(){
        inventory.add(new OBJ_ManaCrystal(0,0,gp));
        inventory.add(new OBJ_ManaCrystal(0,0,gp));
        inventory.add(new OBJ_ManaCrystal(0,0,gp));
        inventory.add(new OBJ_ManaCrystal(0,0,gp));
    }
    public void speak(){
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
