package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.controller.CheckCollision;
import com.mygdx.game.controller.Direction;
import com.mygdx.game.controller.movement.Bullet_Movement;
import com.mygdx.game.view.GameScreen;

public class Bullet extends Entity {
    public GameScreen gameScreen;
    private int width = 30, height = 30;
    private Texture texture;


    public Bullet(GameScreen gameScreen, float x, float y, float speed, Direction direction, TiledMapTileLayer collisionLayer) {
        this.texture = new Texture("basic/Bullet/Bullet_of.png");
        this.gameScreen = gameScreen;
        this.setPosision(x, y);
        rectangle.width = width;
        rectangle.height = height;
        this.setSpeed_Cross(speed);
        this.direction = direction;
        this.collisionLayer = collisionLayer;

        this.moving = Bullet_Movement.getInstance();
        //if(this.texture == null) this.texture = new Texture("basic/Bullet/Bullet_of.png");
    }
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
        //  System.out.println(this.gameScreen.knight.getX() + "-" + this.getX() + "-" + this.gameScreen.knight.screenX);
        float screenX = this.getX() - this.gameScreen.knight.getX() + this.gameScreen.knight.screenX;
        float screenY = this.getY() - this.gameScreen.knight.getY() + this.gameScreen.knight.screenY;
        rectangle.x = screenX;
        rectangle.y = screenY;
        //shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        if(!this.remove) batch.draw(texture,screenX,screenY);
    }
    public void update(){
        moving.move(this, this.gameScreen);
    }
}