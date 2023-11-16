package org.game.object;

import org.game.GamePanel;
import org.game.entity.Projectile;

public class OBJ_Fireball extends Projectile {
    public OBJ_Fireball(GamePanel gp) {
        super(gp);

        name = "Fireball";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 5;
        useCost = 2;
        alive = false;
        getImage();
    }
    public void getImage(){
        System.out.println("Fireball loading started");
        up1 = setup("projectile","fireball_up_1");
        up2 = setup("projectile","fireball_up_2");
        down1 = setup("projectile","fireball_down_1");
        down2 = setup("projectile","fireball_down_2");
        left1 = setup("projectile","fireball_left_1");
        left2 = setup("projectile","fireball_left_2");
        right1 = setup("projectile","fireball_right_1");
        right2 = setup("projectile","fireball_right_2");
        System.out.println("Fireball loading ended");
    }

}
