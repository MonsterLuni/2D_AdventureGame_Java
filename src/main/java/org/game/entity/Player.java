package org.game.entity;

import org.game.GamePanel;
import org.game.KeyHandler;
import org.game.object.projectile.OBJ_Fireball;
import org.game.object.upgrade.OBJ_Shield_Wood;
import org.game.object.upgrade.OBJ_Sword_Normal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity{
    KeyHandler keyH;
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
        setItems();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize * gp.maxWorldCol/2 - gp.tileSize * 2;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        // PLAYER STATUS
        level = 1;
        maxLife = 10;
        maxMana = 20;
        mana = maxMana;
        life = maxLife;
        strength = 1; // more strength == more damage
        dexterity = 1; // more dexterity == less damage receive
        exp = 1;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        attack = getAttack();
        defense = getDefense();
        animationDuration = 15;
    }
    public void setDefaultPositions(){
        worldX = gp.tileSize * gp.maxWorldCol/2 - gp.tileSize * 2;
        worldY = gp.tileSize * 21;
        direction = "down";
    }
    public void restoreLifeAndMana(){
        mana = maxMana;
        life = maxLife;
        invincible = false;
    }
    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
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
        if(currentWeapon.type == type_sword){
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
        else{
            //TODO: add Axe sprites
            System.out.println("Player Axe Attack loading started");
            attackUp1 = setup("player", "boy_attack_up_1",gp.tileSize,gp.tileSize*2);
            attackUp2 = setup("player", "boy_attack_up_2",gp.tileSize,gp.tileSize*2);
            attackDown1 = setup("player", "boy_attack_down_1",gp.tileSize,gp.tileSize*2);
            attackDown2 = setup("player", "boy_attack_down_2",gp.tileSize,gp.tileSize*2);
            attackLeft1 = setup("player", "boy_attack_left_1",gp.tileSize*2,gp.tileSize);
            attackLeft2 = setup("player", "boy_attack_left_2",gp.tileSize*2,gp.tileSize);
            attackRight1 = setup("player", "boy_attack_right_1",gp.tileSize*2,gp.tileSize);
            attackRight2 = setup("player", "boy_attack_right_2",gp.tileSize*2,gp.tileSize);
            System.out.println("Player Axe Attack loading started");
        }
    }
    public void update(){
        if(attacking){
            attacking();
        }
        if(keyH.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30 && projectile.haveResource(this)){
                projectile.set(worldX,worldY,direction,true,this);
                // subtracts mana
                projectile.subtractResource(this);
                // ADD IT TO THE LIST
                gp.projectileList.add(projectile);
                shotAvailableCounter = 0;

                gp.playSE(8);
        }
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
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
        // CHECK INTERACTIVE TILE COLLISION
        gp.cChecker.checkEntity(this, gp.iTile);
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
        if(life <= 0){
            gp.ui.commandNum = -1;
            gp.gameState = gp.gameOverState;
        }
    }
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot();
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            else if (selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            else if(selectedItem.type == type_consumable){
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
    public void pickUpObject(int i){
        if(i != 999){
            // PICKUP ONLY ITEMS
            if(gp.obj[gp.currentMap][i].type == type_pickUpOnly){
                gp.obj[gp.currentMap][i].use(this);
            }
            else {
                // INVENTORY ITEMS
                if(inventory.size() != inventorySize && !Objects.equals(gp.obj[gp.currentMap][i].name, "Door") && !Objects.equals(gp.obj[gp.currentMap][i].name, "Chest")){
                    inventory.add(gp.obj[gp.currentMap][i]);
                    gp.ui.addMessage("Got " + gp.obj[gp.currentMap][i].name);
                }
                else {
                    gp.ui.showMessage("You feel too heavy...");
                }
                String objectName = gp.obj[gp.currentMap][i].name;
                switch(objectName){
                    case "Red Potion" -> {
                        gp.playSE(1);
                        gp.ui.showMessage("You guess this thing can heal anything!");
                    }
                    case "Diamond Axe" -> {
                        gp.playSE(1);
                        gp.ui.showMessage("You found an interesting Axe!");
                    }
                    case "Diamond Shield" -> {
                        gp.playSE(1);
                        gp.ui.showMessage("You found an diamond Shield!");
                    }
                    case "Key" -> {
                        gp.playSE(1);
                        hasKey++;
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
                        gp.ui.showMessage("You feel a lot faster");
                    }
                    case "Chest" -> {
                        gp.ui.gameFinished = true;
                        gp.stopMusic();
                        gp.playSE(4);
                    }
                }
            }
            if(life > maxLife){
                life = maxLife;
            }
            if(mana > maxMana){
                mana = maxMana;
            }
            // DELETE OBJECT
            gp.obj[gp.currentMap][i] = null;
        }
    }
    public void interactNPC(int i){
        if(i != 999){
            if(keyH.ePressed){
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
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
            if(!invincible && !gp.monster[gp.currentMap][i].dying){
                gp.playSE(6);

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 0){
                    damage = 0;
                }

                life -= damage;
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
                damageMonster(monsterIndex, attack);

                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                damageInteractiveTile(iTileIndex);

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
    private void damageInteractiveTile(int i) {
        if(i != 999 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].isCorrectItem(this) && !gp.iTile[gp.currentMap][i].invincible){
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;

            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

            if(gp.iTile[gp.currentMap][i].life == 0){
                gp.iTile[gp.currentMap][i].playSE();
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }
    public void damageMonster(int i, int attack){
        if(i != 999){
            if(!gp.monster[gp.currentMap][i].invincible){
                gp.playSE(5);

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage < 0){
                    damage = 0;
                }
                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");

                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();
                if(gp.monster[gp.currentMap][i].life <= 0){
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp +" + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    private void checkLevelUp() {
        if(exp >= nextLevelExp){
            gp.playSE(2);
            level++;
            nextLevelExp *= 2;
            exp = 0;
            maxLife += 2;
            life = maxLife;
            strength++;
            dexterity++;
            // To recalculate
            attack = getAttack();
            defense = getDefense();
            gp.ui.showMessage("You are level " + level + " now");
        }
    }
}
