package com.mygdx.game.GameProcessing;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.screen.GamePlayingScreen;
import com.mygdx.game.setting.Variable;

public class Check_Collision {
    public GamePlayingScreen gamePlayingScreen;

    public Check_Collision(GamePlayingScreen gamePlayingScreen) {
        this.gamePlayingScreen = gamePlayingScreen;
    }
    public void checkTile(Entity entity){
        // Check va chạm cho không chỉ là Tường mà cả palyer khác, npc,..oloooooooooo

        // Có 4 điểm cần kiểm tra:
        float entityLeft_World_X = entity.worldX + entity.solidArea.x;
        float entityRight_World_X = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        float entityTop_World_Y = entity.worldY + entity.solidArea.y;
        float entityBottom_World_Y = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Từ 4 đ trên ta tìm số hàng, cột:
        int entityLeft_Col = (int) (entityLeft_World_X / Variable.TILE_SIZE);
        int entityRight_Col = (int) (entityRight_World_X / Variable.TILE_SIZE);
        int entityTop_Row = (int) (entityTop_World_Y / Variable.TILE_SIZE);
        int entityBottom_Row = (int) (entityBottom_World_Y / Variable.TILE_SIZE);

        //
        int tileNum1, tileNum2;
        switch(entity.direction){
            case "up":
            {
                // Thay TopRow thành cái ô mà ta SẼ ĐI VÀO NẾU ĐC(Ô CẦN CHECK)
                entityTop_Row = (int) ((entityTop_World_Y - entity.speed * Gdx.graphics.getDeltaTime()) / Variable.TILE_SIZE);

                // Có 2 loại ô có thể gặp: 1 là cụng đầu vào bên trái, 2 là bên phải khi đi lên trên.
                tileNum1 = gamePlayingScreen.mapScreenFull.mapScreen_Full_byNum[entityLeft_Col][entityTop_Row];
                tileNum2 = gamePlayingScreen.mapScreenFull.mapScreen_Full_byNum[entityRight_Col][entityTop_Row];

                System.out.println(entityLeft_Col + "-" + entityTop_Row + ":" + tileNum1 + " and " + entityRight_Col + "-" + entityTop_Row + ":" + tileNum2 + ".     " + entity.direction);

                if(gamePlayingScreen.mapScreenFull.mapScreenTiles[tileNum1].collision == true || gamePlayingScreen.mapScreenFull.mapScreenTiles[tileNum2].collision == true){ // Nễu bị cụng đầu => Không thể di chuyển nữa.
                    entity.collisionOn = true;
                }
                break;
            }
            case "down":{
                entityBottom_Row = (int) ((entityBottom_World_Y + entity.speed * Gdx.graphics.getDeltaTime() )/ Variable.TILE_SIZE);

                // Có 2 loại ô có thể gặp: 1 là cụng đầu vào bên trái, 2 là bên phải khi đi lên trên.
                tileNum1 = gamePlayingScreen.mapScreenFull.mapScreen_Full_byNum[entityLeft_Col][entityBottom_Row];
                tileNum2 = gamePlayingScreen.mapScreenFull.mapScreen_Full_byNum[entityRight_Col][entityBottom_Row];

                if(gamePlayingScreen.mapScreenFull.mapScreenTiles[tileNum1].collision == true || gamePlayingScreen.mapScreenFull.mapScreenTiles[tileNum2].collision == true){ // Nễu bị cụng đầu => Không thể di chuyển nữa.
                    entity.collisionOn = true;
                }
                break;
            }
            case "left": {
                entityLeft_Col = (int) ((entityLeft_World_X - entity.speed * Gdx.graphics.getDeltaTime()) / Variable.TILE_SIZE);

                // Có 2 loại ô có thể gặp: 1 là cụng đầu vào bên trái, 2 là bên phải khi đi lên trên.
                tileNum1 = gamePlayingScreen.mapScreenFull.mapScreen_Full_byNum[entityLeft_Col][entityTop_Row];
                tileNum2 = gamePlayingScreen.mapScreenFull.mapScreen_Full_byNum[entityLeft_Col][entityBottom_Row];

                if(gamePlayingScreen.mapScreenFull.mapScreenTiles[tileNum1].collision == true || gamePlayingScreen.mapScreenFull.mapScreenTiles[tileNum2].collision == true){ // Nễu bị cụng đầu => Không thể di chuyển nữa.
                    entity.collisionOn = true;
                }
                break;
            }
            case "right":{
                entityRight_Col = (int) ((entityRight_World_X + entity.speed * Gdx.graphics.getDeltaTime()) / Variable.TILE_SIZE);

                // Có 2 loại ô có thể gặp: 1 là cụng đầu vào bên trái, 2 là bên phải khi đi lên trên.
                tileNum1 = gamePlayingScreen.mapScreenFull.mapScreen_Full_byNum[entityRight_Col][entityTop_Row];
                tileNum2 = gamePlayingScreen.mapScreenFull.mapScreen_Full_byNum[entityRight_Col][entityBottom_Row];

                if(gamePlayingScreen.mapScreenFull.mapScreenTiles[tileNum1].collision == true || gamePlayingScreen.mapScreenFull.mapScreenTiles[tileNum2].collision == true){ // Nễu bị cụng đầu => Không thể di chuyển nữa.
                    entity.collisionOn = true;
                }
                break;
            }
        }
    }
}
