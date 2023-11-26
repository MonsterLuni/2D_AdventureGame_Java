package org.game;

import org.game.entity.Entity;

public class CollisionDetection {
    GamePanel gp;
    public CollisionDetection(GamePanel gp){
        this.gp = gp;
    }
    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1 = 0;
        int tileNum2 = 0;

        switch(entity.direction){
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
            }
        }
        if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
            entity.collisionOn = true;
        }
    }
    public int checkObject(Entity entity, boolean player){
        int index = 999;

        for(int i = 0; i < gp.obj[1].length; i++){
            if(gp.obj[gp.currentMap][i] != null){
                getSolidAreaPosition(entity);
                getSolidAreaPosition(gp.obj[gp.currentMap][i]);

                entityDirection(entity);

                if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)){
                    if(gp.obj[gp.currentMap][i].collision){
                        entity.collisionOn = true;
                    }
                    if(player){
                        index = i;
                    }
                }
                setSolidAreaPositionDefault(entity);
                setSolidAreaPositionDefault(gp.obj[gp.currentMap][i]);
            }
        }
        return index;
    }
    //NPC OR MONSTER COLLISION
    public int checkEntity(Entity entity, Entity[][] target){
        int index = 999;

        for(int i = 0; i < target[1].length; i++){
            if(target[gp.currentMap][i] != null){
                getSolidAreaPosition(entity);

                getSolidAreaPosition(target[gp.currentMap][i]);

                entityDirection(entity);

                if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)){
                    if(target[gp.currentMap][i] != entity){
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                setSolidAreaPositionDefault(entity);
                setSolidAreaPositionDefault(target[gp.currentMap][i]);
            }
        }
        return index;
    }
    public boolean checkPlayer(Entity entity){
        boolean contactPlayer = false;
        if(gp.player != null) {
            getSolidAreaPosition(entity);
            getSolidAreaPosition(gp.player);

            entityDirection(entity);

            if (entity.solidArea.intersects(gp.player.solidArea)) {
                entity.collisionOn = true;
                contactPlayer = true;
            }
            setSolidAreaPositionDefault(entity);
            setSolidAreaPositionDefault(gp.player);
        }
        return contactPlayer;
    }
    public void entityDirection(Entity entity){
        switch (entity.direction){
            case "up" -> entity.solidArea.y -= entity.speed;
            case "down" -> entity.solidArea.y += entity.speed;
            case "left" -> entity.solidArea.x -= entity.speed;
            case "right" -> entity.solidArea.x += entity.speed;
        }
    }
    public void getSolidAreaPosition(Entity entity){
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
    }
    public void setSolidAreaPositionDefault(Entity entity){
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
    }
}
