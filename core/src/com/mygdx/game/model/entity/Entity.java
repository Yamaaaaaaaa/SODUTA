package com.mygdx.game.model.entity;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.controller.Direction;
import com.mygdx.game.controller.Movement;

public class Entity {
    private boolean collision = false;

// SETTING BAN ĐẦU:
    // Vị trí:
    private float x, y; // Vị trí trong bản đồ.
    public void setPosision(float x, float y){
        this.setX(x);
        this.setY(y);
    }
    // status: Tốc độ, HP, Dam, kích cỡ nhân vật.
    private float speed_Stright;
    private float speed_Cross;

    public int damage;
    public int damageGun, damageKnife;
    public int currentHp;
    public int maxHP;

    private int width;
    private int height;
    public Rectangle rectangle = new Rectangle() ;

    // QUẢN LÝ HÀNH ĐỘNG
    public Movement moving;
    public Direction direction;
    public Entity_Status status;
    public Attack_Status attackStatus;

    // Dành riêng cho quái
    public boolean remove = false;

        // SETTING DI CHUYỂN MẶC ĐỊNH TRONG MAP:
        public String direction_Static;
        public float xMin, yMin, xMax, yMax;
    // Dành riêng cho Bullet.


// VA CHẠM
    public TiledMapTileLayer collisionLayer;

    public Rectangle getRectangle() {
        return rectangle;
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
