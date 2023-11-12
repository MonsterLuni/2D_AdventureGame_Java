package org.game;

public class EventHandler {
    GamePanel gp;
    EventRect[][] eventRect;
    int pEventX, pEventY;
    boolean canTouchEvent;
    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }
    public void checkEvent(){
        // Check if player has moved more than 1 tile
        int xDistance = Math.abs(gp.player.worldX - pEventX); // Betragsstrich :D
        int yDistance = Math.abs(gp.player.worldY - pEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }
        if(canTouchEvent){
            if(hit(23,7,"up")){damagePit(gp.dialogueState);}
            if(hit(25,7,"up")){ healingPool(gp.dialogueState);}
        }
    }
    public boolean hit(int col, int row, String reqDirection){
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;
        if(gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone){
            if(gp.player.direction.contentEquals(reqDirection) ||reqDirection.contentEquals("any")){
                hit = true;

                pEventX = gp.player.worldX;
                pEventY = gp.player.worldY;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
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
            }
        }
    }
}
