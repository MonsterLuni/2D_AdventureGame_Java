package org.game;

import org.game.entity.Entity;

public class EventHandler {
    GamePanel gp;
    EventRect[][][] eventRect;
    int pEventX, pEventY;
    boolean canTouchEvent;
    int tempMap, tempCol, tempRow;
    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }
    }
    public void checkEvent(){
        // Check if player has moved more than 1 tile
        int xDistance = Math.abs(gp.player.worldX - pEventX);
        int yDistance = Math.abs(gp.player.worldY - pEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }
        if(canTouchEvent){

            if(hit(0,23,7,"up")){damagePit(gp.dialogueState);}
            else if(hit(0,25,7,"up")){healingPool(gp.dialogueState);}
            else if(hit(0,48,2,"up")){teleport(1,7,11);}
            else if(hit(1,7,13,"any")){teleport(0,48,2);}
            else if(hit(1,7,9,"any")) {speak(gp.npc[1][0]);}
        }
    }

    private void speak(Entity entity) {
        if(gp.keyH.enterPressed){
            gp.gameState = gp.dialogueState;
            entity.speak();
        }
    }

    private void teleport(int map, int col, int row) {
        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
    }

    public boolean hit(int map, int col, int row, String reqDirection){
        boolean hit = false;
        if(map == gp.currentMap){
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone){
                if(gp.player.direction.contentEquals(reqDirection) ||reqDirection.contentEquals("any")){
                    hit = true;

                    pEventX = gp.player.worldX;
                    pEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }
    public void damagePit(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fell into a pit!";
        gp.player.life--;
        // eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }
    public void healingPool(int gameState){
        if(gp.keyH.ePressed){
            if(gp.player.life + 1 > gp.player.maxLife){
                gp.gameState = gameState;
                gp.ui.currentDialogue = "You are no longer thirsty";
            }
            else {
                gp.gameState = gameState;
                gp.ui.currentDialogue = "You feel refreshed!";
                gp.player.life++;
                gp.aSetter.setMonster();
            }
        }
    }
}
