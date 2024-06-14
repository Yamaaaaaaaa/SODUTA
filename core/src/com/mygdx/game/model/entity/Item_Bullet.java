package com.mygdx.game.model.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.controller.Direction;
import com.mygdx.game.controller.movement.Bullet_Movement;
import com.mygdx.game.view.GameScreen;

public class Item_Bullet extends Entity{
    public GameScreen gameScreen;
    private int width = 46, height = 46;
    private Texture texture;
    public boolean alive = true;
    public int counterTime = 0;
    public boolean collisionKnight = false;
    public Texture FIcon;

    public Item_Bullet(String imgPath,GameScreen gameScreen, float x, float y) {
        this.texture = new Texture(imgPath);
        this.gameScreen = gameScreen;
        this.setPosision(x, y);
        rectangle.width = width;
        rectangle.height = height;
        FIcon = new Texture("button/letter-f.png");
    }
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer){
        //  System.out.println(this.gameScreen.knight.getX() + "-" + this.getX() + "-" + this.gameScreen.knight.screenX);
        float screenX = this.getX() - this.gameScreen.knight.getX() + this.gameScreen.knight.screenX;
        float screenY = this.getY() - this.gameScreen.knight.getY() + this.gameScreen.knight.screenY;
        rectangle.x = screenX;
        rectangle.y = screenY;
        //shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        if(this.alive) batch.draw(texture,screenX,screenY);
        if(collisionKnight) batch.draw(FIcon, this.gameScreen.knight.screenX + 54,this.gameScreen.knight.screenY + 54);
    }

    public void update(){
        if(counterTime > 0){
            counterTime --;
        }
        else {
            counterTime = 0;
            alive = true;
        }
    }
}
