package org.game.object;

import org.game.GamePanel;
import org.game.entity.Projectile;

public class OBJ_Coconut extends Projectile {
    public OBJ_Coconut(GamePanel gp) {
        super(gp);

        name = "Coconut";
        speed = 2;
        maxLife = 160;
        life = maxLife;
        attack = 2;
        useCost = 2;
        alive = false;
        getImage();
    }
    public void getImage(){
        System.out.println("coconut loading started");
        up1 = setup("projectile","coconut");
        up2 = setup("projectile","coconut");
        down1 = setup("projectile","coconut");
        down2 = setup("projectile","coconut");
        left1 = setup("projectile","coconut");
        left2 = setup("projectile","coconut");
        right1 = setup("projectile","coconut");
        right2 = setup("projectile","coconut");
        System.out.println("coconut loading ended");
    }
}
