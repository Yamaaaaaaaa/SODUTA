package com.mygdx.game.controller;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.model.Entity;

public class CheckCollision {
    // check collision version ngây thơ
    private Entity entity;
    private String blockedKey = "blocked";
    public CheckCollision(Entity entity) {
        this.entity = entity;
    }

    public void check_KnightWithMap(float delta, float oldX, float oldY) {
        if (entity.direction == Direction.LEFT) {
            if(collidesLeft()){
                entity.setX(oldX);
            }
        } else if (entity.direction == Direction.RIGHT) {
            if(collidesRight()){
                entity.setX(oldX);
            }
        }else if (entity.direction == Direction.UP) {
            if(collidesTop()){
                entity.setY(oldY);
            }
        }else if (entity.direction == Direction.DOWN) {
            if(collidesBottom()){
                entity.setY(oldY);
            }
        }

        else if (entity.direction == Direction.UPLEFT) {
            if(collidesTop()){
                entity.setY(oldY);
            }
            if(collidesLeft()){
                entity.setX(oldX);
            }
        }else if (entity.direction == Direction.UPRIGHT) {
            if(collidesTop()){
                entity.setY(oldY);
            }
            if(collidesRight()){
                entity.setX(oldX);
            }
        }else if (entity.direction == Direction.DOWNLEFT) {
            if(collidesBottom()){
                entity.setY(oldY);
            }
            if(collidesLeft()){
                entity.setX(oldX);
            }
        }
        else if (entity.direction == Direction.DOWNRIGHT) {
            if(collidesBottom()){
                entity.setY(oldY);
            }
            if(collidesRight()){
                entity.setX(oldX);
            }
        }
    }
    public void check_MonsterWithMap(float oldX, float oldY) {
        if(collidesLeft() || collidesRight()){
            entity.setX(oldX);
        }
        if(collidesTop() || collidesBottom()){
            entity.setY(oldY);
        }
    }

    public boolean check_BulletWithMap(float oldX, float oldY) {
        if (entity.direction == Direction.LEFT) {
            if(collidesLeft()){
                entity.setX(oldX);
                return true;
            }
        } else if (entity.direction == Direction.RIGHT) {
            if(collidesRight()){
                entity.setX(oldX);
                return true;
            }
        }else if (entity.direction == Direction.UP) {
            if(collidesTop()){
                entity.setY(oldY);
                return true;
            }
        }else if (entity.direction == Direction.DOWN) {
            if(collidesBottom()){
                entity.setY(oldY);
                return true;
            }
        }
        else if (entity.direction == Direction.UPLEFT) {
            boolean ok = false;
            if(collidesTop()){
                entity.setY(oldY);
                ok = true;
            }
            if(collidesLeft()){
                entity.setX(oldX);
                ok = true;
            }
            return ok;
        }else if (entity.direction == Direction.UPRIGHT) {
            boolean ok = false;
            if(collidesTop()){
                entity.setY(oldY);
                ok = true;
            }
            if(collidesRight()){
                entity.setX(oldX);
                ok = true;
            }
            return ok;
        }else if (entity.direction == Direction.DOWNLEFT) {
            boolean ok = false;
            if(collidesBottom()){
                entity.setY(oldY);
                ok = true;
            }
            if(collidesLeft()){
                entity.setX(oldX);
                ok = true;
            }
            return ok;
        }
        else if (entity.direction == Direction.DOWNRIGHT) {
            boolean ok = false;
            if(collidesBottom()){
                entity.setY(oldY);
                ok = true;
            }
            if(collidesRight()){
                entity.setX(oldX);
                ok = true;
            }
            return ok;
        }
        return false;
    }


    // Cách làm: Tìm tới tệp chưa cái tile cần block, Thêm Phần code sau:
    //    <tile id="0">
    //        <properties>
    //            <property name="blocked" value=""/>
    //        </properties>
    //    </tile>
    // Explain: Cái hàm dưới, nó check cái Value của Property, nó có Key = "blocked" => Ta chỉ cần tạo cho nó 1 cái đấy là đc(vì chỉ check là null thôi mà)
    //id là cái ID của tile trong bản đồ(lúc mk tạo map bằng Tiled nó đã có, xem thêm ở file .tmx.
    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = entity.collisionLayer.getCell((int) (x / entity.collisionLayer.getTileWidth()), (int) (y / entity.collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
    }

    public boolean collidesRight() {
        for(float step = 0; step <= entity.getHeight(); step += (float) entity.collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(entity.getX() + entity.getWidth(), entity.getY() + step))
                return true;
        return false;
    }

    public boolean collidesLeft() {
        for(float step = 0; step <= entity.getHeight(); step += (float) entity.collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(entity.getX(), entity.getY() + step))
                return true;
        return false;
    }

    public boolean collidesTop() {
        for(float step = 0; step <= entity.getWidth(); step += (float) entity.collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(entity.getX() + step, entity.getY() + entity.getHeight()))
                return true;
        return false;
    }

    public boolean collidesBottom() {
        for(float step = 0; step <= entity.getWidth(); step += (float) entity.collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(entity.getX() + step, entity.getY()))
                return true;
        return false;
    }
}
