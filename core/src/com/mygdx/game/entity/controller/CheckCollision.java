package com.mygdx.game.entity.controller;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.entity.Knight;

public class CheckCollision {
    // check collision version ngây thơ
    private Knight knight;
    private String blockedKey = "blocked";
    public CheckCollision(Knight knight) {
        this.knight = knight;
    }

    public void check(float delta, float oldX, float oldY) {
        if (knight.direction == Direction.LEFT) {
            if(collidesLeft()){
                knight.setX(oldX);
            }
        } else if (knight.direction == Direction.RIGHT) {
            if(collidesRight()){
                knight.setX(oldX);
            }
        }else if (knight.direction == Direction.UP) {
            if(collidesTop()){
                knight.setY(oldY);
            }
        }else if (knight.direction == Direction.DOWN) {
            if(collidesBottom()){
                knight.setY(oldY);
            }
        }

        else if (knight.direction == Direction.UPLEFT) {
            if(collidesTop()){
                knight.setY(oldY);
            }
            if(collidesLeft()){
                knight.setX(oldX);
            }
        }else if (knight.direction == Direction.UPRIGHT) {
            if(collidesTop()){
                knight.setY(oldY);
            }
            if(collidesRight()){
                knight.setX(oldX);
            }
        }else if (knight.direction == Direction.DOWNLEFT) {
            if(collidesBottom()){
                knight.setY(oldY);
            }
            if(collidesLeft()){
                knight.setX(oldX);
            }
        }
        else if (knight.direction == Direction.DOWNRIGHT) {
            if(collidesBottom()){
                knight.setY(oldY);
            }
            if(collidesRight()){
                knight.setX(oldX);
            }
        }
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
        TiledMapTileLayer.Cell cell = knight.collisionLayer.getCell((int) (x / knight.collisionLayer.getTileWidth()), (int) (y / knight.collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
    }

    public boolean collidesRight() {
        for(float step = 0; step <= knight.getHeight(); step += (float) knight.collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(knight.getX() + knight.getWidth(), knight.getY() + step))
                return true;
        return false;
    }

    public boolean collidesLeft() {
        for(float step = 0; step <= knight.getHeight(); step += knight.collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(knight.getX(), knight.getY() + step))
                return true;
        return false;
    }

    public boolean collidesTop() {
        for(float step = 0; step <= knight.getWidth(); step += knight.collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(knight.getX() + step, knight.getY() + knight.getHeight()))
                return true;
        return false;
    }

    public boolean collidesBottom() {
        for(float step = 0; step <= knight.getWidth(); step += knight.collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(knight.getX() + step, knight.getY()))
                return true;
        return false;
    }
}
