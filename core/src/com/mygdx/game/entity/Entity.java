package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;

public class Entity {
    private float x, y;
    private int width, height;
    private String direction;
    private Texture texture_walking;
    private Texture texture_shooting;
    private Texture texture_stabbing;
    private boolean collision = false;

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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public Texture getTexture_walking() {
        return texture_walking;
    }

    public void setTexture_walking(Texture texture_walking) {
        this.texture_walking = texture_walking;
    }

    public Texture getTexture_shooting() {
        return texture_shooting;
    }

    public void setTexture_shooting(Texture texture_shooting) {
        this.texture_shooting = texture_shooting;
    }

    public Texture getTexture_stabbing() {
        return texture_stabbing;
    }

    public void setTexture_stabbing(Texture texture_stabbing) {
        this.texture_stabbing = texture_stabbing;
    }
}
