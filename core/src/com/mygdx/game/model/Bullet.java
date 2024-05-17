package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.controller.Direction;

public class Bullet extends Entity{
    public static final int SPEED = 500;

    private static Texture texture_bullet;
    public boolean removeBullet = false;
    Animation[] bullet;


    private float bulletX, bulletY;
    public Bullet(float bulletX, float bulletY){
        this.bulletX = bulletX;
        this.bulletY = bulletY;

        if(texture_bullet == null){
            this.texture_bullet = new Texture("basic/Bullet/Bullet_demo.png");
        }

    }

    public void update(float stateTime){
        // kiểm tra đang quay mặt hướng nào để xử lí đường đạn
        if(this.direction == Direction.UP ) {
            bulletY += SPEED * stateTime;
            // kiểm tra va chạm ( chưa làm được nên để tạm hết map thì biến mất )
            if (bulletY > Gdx.graphics.getHeight()) removeBullet = true;
        } else if(this.direction == Direction.LEFT) {
            bulletX -= SPEED*stateTime;
            if(bulletX < Gdx.graphics.getWidth() ) removeBullet = true;
        } else if(this.direction == Direction.DOWN){
            bulletY -= SPEED*stateTime;
            if (bulletY > Gdx.graphics.getHeight()) removeBullet = true;
        } else if(this.direction == Direction.RIGHT){
            bulletX += SPEED * stateTime;
            if(bulletX > Gdx.graphics.getWidth() ) removeBullet = true;
        }
    }
    public void render(SpriteBatch batch,float stateTime,float x, float y){
        bullet = new Animation[10];
        TextureRegion[][] regions = TextureRegion.split(this.texture_bullet, 32,32);
        for(int i=0;i<4;++i) bullet[i] = new Animation(0.3f,regions[i]);

        int index = 0;
        if(this.direction == Direction.UP) index = 0;
        else if(this.direction == Direction.LEFT) index  = 3;
        else if(this.direction == Direction.RIGHT) index = 2;
        else if(this.direction == Direction.DOWN) index = 1;

        batch.draw((TextureRegion) bullet[index].getKeyFrame(stateTime,true),x,y,32,32);
    }

    public float getBulletY() {
        return bulletY;
    }

    public void setBulletY(float bulletY) {
        this.bulletY = bulletY;
    }

    public float getBulletX() {
        return bulletX;
    }

    public void setBulletX(float bulletX) {
        this.bulletX = bulletX;
    }
}
