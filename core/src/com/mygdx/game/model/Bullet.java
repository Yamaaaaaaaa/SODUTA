package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.controller.Direction;

public class Bullet {
    private float x,y;
    private float speed;
    private Direction direction;
    private Texture texture;
    public boolean remove = false;
    public Bullet(float x, float y, float speed, Direction direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        //if(this.texture == null) this.texture = new Texture("basic/Bullet/Bullet_of.png");
    }
    public void render(SpriteBatch batch){
        batch.draw(new Texture("basic/Bullet/Bullet_of.png"),x,y);
    }
    public void update(float deltaTime){
        if(this.direction == Direction.DOWN){
            y-=speed*deltaTime;
            if(y<0) remove = true;
        }
        else if(this.direction == Direction.UP){
            y+=speed*deltaTime;
            if(y>Gdx.graphics.getHeight()) remove = true;
        }
        else if(this.direction == Direction.LEFT || this.direction == Direction.UPLEFT || this.direction == Direction.DOWNLEFT){
            x-=speed*deltaTime;
            if(x<0) remove = true;
        }
        else {
            x+=speed*deltaTime;
            if(x>Gdx.graphics.getWidth()) remove = true;
        }
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

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }


}
