package org.game.tile_interactive;

import org.game.GamePanel;
import org.game.entity.Entity;

import java.awt.*;

public class IT_RottenTree extends InteractiveTile{
    public IT_RottenTree(int x, int y, GamePanel gp) {
        super(gp);
        this.worldX = x;
        this.worldY = y;
        life = 3;
        down1 = setup("tile_interactive","tree_rotten");
        destructible = true;
    }
    @Override
    public boolean isCorrectItem(Entity entity){
        return entity.currentWeapon.type == type_axe;
    }
    @Override
    public void playSE(){
        gp.playSE(1);
    }
    @Override
    public InteractiveTile getDestroyedForm(){
        System.out.println(worldX + " " + worldY);
        return new IT_Trunk(worldX,worldY,gp);
    }
    @Override
    public Color getParticleColor(){
        return new Color(65,50,30);
    }
    @Override
    public int getParticleSize(){
        return 6;
    }
    @Override
    public int getParticleSpeed(){
        return 1;
    }
    @Override
    public int getParticleMaxLife(){
        return 20;
    }
}
