package com.mygdx.game.entity.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.entity.controller.Attack_Status;
import com.mygdx.game.entity.controller.Direction;
import com.mygdx.game.entity.controller.Entity_Status;
import com.mygdx.game.entity.controller.Activity;

public class Entity {

    private boolean collision = false;

    // SETTING BAN ĐẦU:

        // Setting Nhân Vật:
        private float x, y; // Vị trí trong bản đồ.
        public void setPosision(float x, float y){
            this.setX(x);
            this.setY(y);
        }
        // Tốc độ:
        private float speed_Stright;
        private float speed_Cross;
        // Kích thước
        private int width;
        private int height;


    // QUẢN LÝ HÀNH ĐỘNG
    public Activity activity;
    public Direction direction;
    public Entity_Status status;
    public Attack_Status attackStatus;

    // Dành riêng cho quái

        // SETTING DI CHUYỂN MẶC ĐỊNH TRONG MAP:
        public String direction_Static;
        public float xMin, yMin, xMax, yMax;


    // VA CHẠM
    public TiledMapTileLayer collisionLayer;


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Entity_Status getStatus() {
        return status;
    }

    public void setStatus(Entity_Status status) {
        this.status = status;
    }

    public float getSpeed_Stright() {
        return speed_Stright;
    }

    public void setSpeed_Stright(float speed_Stright) {
        this.speed_Stright = speed_Stright;
    }

    public float getSpeed_Cross() {
        return speed_Cross;
    }

    public void setSpeed_Cross(float speed_Cross) {
        this.speed_Cross = speed_Cross;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    public Entity() {
        x = y = 0;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }



    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }


}
