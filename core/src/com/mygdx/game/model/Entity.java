package com.mygdx.game.model;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.controller.Direction;
import com.mygdx.game.controller.Moving;

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
    private int blood;
    public Rectangle rectangle = new Rectangle() ;
// QUẢN LÝ HÀNH ĐỘNG
    public Moving moving;
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

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
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

    public Moving getActivity() {
        return moving;
    }

    public void setActivity(Moving moving) {
        this.moving = moving;
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
        //this.rectangle.y = y;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        //this.rectangle.y = y;
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



    public void setRectangle(int width, int height) {
        this.rectangle.height = height;
        this.rectangle.width = width;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
