package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.controller.CheckCollision;
import com.mygdx.game.controller.Direction;

public class Bullet extends Entity {
    private int width = 30, height = 30;
    private Direction direction;
    private Texture texture;
    public boolean remove = false;

    public Bullet(float x, float y, float speed, Direction direction, TiledMapTileLayer collisionLayer) {
        this.setPosision(x, y);
        rectangle = new Rectangle((int)x, (int)y, width, height);
        this.setSpeed_Cross(speed);
        this.direction = direction;
        this.collisionLayer = collisionLayer;
        //if(this.texture == null) this.texture = new Texture("basic/Bullet/Bullet_of.png");
    }
    public void render(SpriteBatch batch){
            batch.draw(new Texture("basic/Bullet/Bullet_of.png"),getX(),getY());
    }
    public void update(float deltaTime){
        if(this.direction == Direction.DOWN){
            setY(getY() - getSpeed_Cross()*deltaTime);
            if(getY()<0) remove = true;
        }
        else if(this.direction == Direction.UP){
            setY(getY() +getSpeed_Cross()*deltaTime);
            if(getY()>Gdx.graphics.getHeight()) remove = true;
        }
        else if(this.direction == Direction.LEFT || this.direction == Direction.UPLEFT || this.direction == Direction.DOWNLEFT){
            setX(getX() - getSpeed_Cross()*deltaTime);
            if(getX()<0) remove = true;
        }
        else {
            setX(getX() + getSpeed_Cross()*deltaTime);
            if(getX()>Gdx.graphics.getWidth()) remove = true;
        }
        rectangle.x = getX();
        rectangle.y = getY();
    }
}