package org.game.entity;

import org.game.GamePanel;
import org.game.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    KeyHandler keyH;

    public int animationDuration = 15;
    public int hasKey = 0;
    public int spriteCounterAttack = 0;
    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(12, 30, 30, 20);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    public void setDefaultValues(){
        //TODO: make that this is in the middle
        worldX = gp.tileSize * gp.maxWorldCol/2 - gp.tileSize * 2;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        // PLAYER SATUS
        maxLife = 10;
        life = maxLife;
    }
    public void getPlayerImage(){
        System.out.println("Player loading started");
        up1 = setup("player","boy_up_1");
        up2 = setup("player","boy_up_2");
        down1 = setup("player","boy_down_1");
        down2 = setup("player","boy_down_2");
        left1 = setup("player","boy_left_1");
        left2 = setup("player","boy_left_2");
        right1 = setup("player","boy_right_1");
        right2 = setup("player","boy_right_2");
        System.out.println("Player loading ended");
    }
    public void getPlayerAttackImage(){
        System.out.println("Player Attack loading started");
        attackUp1 = setup("player", "boy_attack_up_1",gp.tileSize,gp.tileSize*2);
        attackUp2 = setup("player", "boy_attack_up_2",gp.tileSize,gp.tileSize*2);
        attackDown1 = setup("player", "boy_attack_down_1",gp.tileSize,gp.tileSize*2);
        attackDown2 = setup("player", "boy_attack_down_2",gp.tileSize,gp.tileSize*2);
        attackLeft1 = setup("player", "boy_attack_left_1",gp.tileSize*2,gp.tileSize);
        attackLeft2 = setup("player", "boy_attack_left_2",gp.tileSize*2,gp.tileSize);
        attackRight1 = setup("player", "boy_attack_right_1",gp.tileSize*2,gp.tileSize);
        attackRight2 = setup("player", "boy_attack_right_2",gp.tileSize*2,gp.tileSize);
        System.out.println("Player Attack loading started");
    }
    public void update(){
        if(attacking){
            attacking();
        }
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        boolean pressing = false;
        if(keyH.upPressed){
            direction = "up";
            pressing = true;
        }
        if(keyH.downPressed){
            direction = "down";
            pressing = true;
        }
        if(keyH.leftPressed){
            direction = "left";
            pressing = true;
        }
        if(keyH.rightPressed){
            direction = "right";
            pressing = true;
        }
        //CHECK TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);
        // CHECK NPC COLLISION
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);
        // CHECK MONSTER COLLISION
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        contactMonster(monsterIndex);
        // CHECK EVENT
        gp.eHandler.checkEvent();
        // CHECK OBJECT COLLISION
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);
        //ePressed FALSE
        keyH.ePressed = false;
        //IF COLLISION IS FALSE, THE PLAYER CAN MOVE
        if(pressing){
            if(!attacking){
                spriteCounter++;
                if(spriteCounter > animationDuration){
                    if(spriteNumber == 1){
                        spriteNumber = 2;
                    }
                    else{
                        spriteNumber = 1;
                    }
                    spriteCounter = 0;
                }
            }
            if(!collisionOn){
                switch(direction){
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
        }
    }
    public void pickUpObject(int i){
        if(i != 999){
            String objectName = gp.obj[i].name;
            switch(objectName){
                case "Key" -> {
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    if(Math.random() > 0.8f){
                        gp.ui.showMessage("You found a perfectly fine key");
                    }
                    else{
                        gp.ui.showMessage("You found an rusty old key");
                    }
                }
                case "Door" -> {
                    if(hasKey > 0){
                        gp.playSE(3);
                        hasKey--;
                        gp.obj[i] = null;
                        gp.ui.showMessage("You managed to open the door");
                    }
                    else{
                        gp.ui.showMessage("This door seems to be locked");
                    }
                }
                case "Boots" -> {
                    gp.playSE(2);
                    speed += 2;
                    animationDuration = 10;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You feel a lot faster");
                }
                case "Chest" -> {
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                }
            }
        }
    }
    public void interactNPC(int i){
        if(i != 999){
            if(keyH.ePressed){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        if(attacking){
            if(spriteNumber == 1){
                switch(direction){
                    case "up" -> {
                        tempScreenY = screenY - gp.tileSize;
                        image = attackUp1;
                    }
                    case "down" -> image = attackDown1;
                    case "left" -> {
                        tempScreenX = screenX - gp.tileSize;
                        image = attackLeft1;
                    }
                    case "right" -> image = attackRight1;
                }
            }
            else{
                switch(direction){
                    case "up" -> {
                        tempScreenY = screenY - gp.tileSize;
                        image = attackUp2;
                    }
                    case "down" -> image = attackDown2;
                    case "left" -> {
                        tempScreenX = screenX - gp.tileSize;
                        image = attackLeft2;
                    }
                    case "right" -> image = attackRight2;
                }
            }
        }
        else{
            if(spriteNumber == 1){
                switch(direction){
                    case "up" -> image = up1;
                    case "down" -> image = down1;
                    case "left" -> image = left1;
                    case "right" -> image = right1;
                }
            }
            else{
                switch(direction){
                    case "up" -> image = up2;
                    case "down" -> image = down2;
                    case "left" -> image = left2;
                    case "right" -> image = right2;
                }
            }
        }
        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY,null);
        // Reset Alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    }
    public void contactMonster(int i){
        if(i != 999){
            if(!invincible){
                life--;
                invincible = true;
            }
        }
    }
    public void attacking(){
        if (spriteCounterAttack < 30){
            spriteCounterAttack++;
            if(spriteCounterAttack < 10){
                spriteNumber = 1;
            }
            else if(spriteCounterAttack < 25){
                spriteNumber = 2;
                // Save the current Data
                int currentWorldX = worldX;
                int currentWorldY = worldY;
                int solidAreaWidth = solidArea.width;
                int solidAreaHeight = solidArea.height;

                // Adjust player's Data
                switch (direction){
                    case "up" -> worldY -= attackArea.height;
                    case "down" -> worldY += attackArea.height;
                    case "left" -> worldX -= attackArea.width;
                    case "right" -> worldX += attackArea.width;
                }
                solidArea.width = attackArea.width;
                solidArea.height = attackArea.height;

                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                damageMonster(monsterIndex);

                worldX = currentWorldX;
                worldY = currentWorldY;
                solidArea.width = solidAreaWidth;
                solidArea.height = solidAreaHeight;
            }
            else {
                spriteNumber = 1;
            }
        }
        else{
            spriteCounterAttack = 0;
            attacking = false;
        }
    }
    public void damageMonster(int i){
        if(i != 999){
            if(!gp.monster[i].invincible){
                gp.monster[i].life--;
                gp.monster[i].invincible = true;
                if(gp.monster[i].life <= 0){
                    gp.monster[i] = null;
                }
            }
        }
    }
}
