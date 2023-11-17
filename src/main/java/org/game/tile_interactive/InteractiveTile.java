package org.game.tile_interactive;

import org.game.GamePanel;
import org.game.entity.Entity;

public class InteractiveTile extends Entity {
    public boolean destructible = false;
    public InteractiveTile(GamePanel gp) {
        super(gp);
    }
    public void update(){
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 20){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    public boolean isCorrectItem(Entity entity){
        return false;
    }
    public void playSE(){}
    public InteractiveTile getDestroyedForm(){
        return null;
    }
}
