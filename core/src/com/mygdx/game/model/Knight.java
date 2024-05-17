package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.controller.Direction;
import com.mygdx.game.controller.movement.Player_Movement;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Knight extends Entity {
    public float screenX = 400, screenY = 400; // Cái này chỉ riêng tk NV Chính có.

    // ROLLS:
    private Texture texture_walking;
    private Texture texture_shooting;
    private Texture texture_stabbing;
    private Texture texture_bullet;
    private Animation[] walking;
    private Animation[] shootting;
    private Animation[] stabbing;

    private TextureRegion[] idle; // Ta chỉ set 1 số ảnh để làm IDLE thôi, Ko cần 1 cái Standing riêng, vì nó sẽ bị giật giật khi chuyển qua lại các status.
    // Shooting
    public ArrayList<Bullet> bullets;
    public Knight(float x, float y, float speed, TiledMapTileLayer collsionLayer) {
        // Đạn
        bullets = new ArrayList<Bullet>();
        // hinh anh
        this.texture_walking = new Texture("basic/character/Walk.png");
        this.texture_shooting = new Texture("basic/character/Shoot.png");
        this.texture_stabbing = new Texture("basic/character/Stab.png");

        // vij tri
        this.setPosision(x,y);

        //speed
        this.setSpeed_Stright(speed);
        this.setSpeed_Cross((float) Math.sqrt(speed * speed / 2));

        // chieu cao, phuong huong, trang thai:
        direction = Direction.DOWN;
        status = Entity_Status.IDLE;
        this.setWidth(32);
        this.setHeight(32);
        this.setAnimation();

        // Gọi cái class qua lý di chuyển ra
        this.moving = Player_Movement.getInstance();

        //collsion:
        this.collisionLayer = collsionLayer;

        // attack:
        this.attackStatus = Attack_Status.SHOOT; // Mặc định là ban đầu sẽ chém
    }


    private void setAnimation(){
        walking = new Animation[10];
        stabbing = new Animation[10];
        shootting = new Animation[10];

        idle = new TextureRegion[10];
        TextureRegion[][] region1 = TextureRegion.split(this.texture_walking, this.getWidth(), this.getHeight());
        TextureRegion[][] region2 = TextureRegion.split(this.texture_stabbing, this.getWidth(), this.getHeight());
        TextureRegion[][] region3 = TextureRegion.split(this.texture_shooting, this.getWidth(), this.getHeight());

        for(int i = 0; i < 4; ++i){
            walking[i] = new Animation(0.2f, region1[i]);
            stabbing[i] = new Animation(0.1f, region2[i]);
            shootting[i] = new Animation(0.1f, region3[i]);

            idle[i] = region1[i][1];
        }
    }
    public void update(float delta){
        this.moving.move(this);
        // update Bullet
        ArrayList<Bullet> bulletToRemove = new ArrayList<Bullet>();
        for(Bullet bullet : bullets){
            bullet.update(delta);
            if(bullet.remove) bulletToRemove.add(bullet);
        }
        bullets.removeAll(bulletToRemove);
        boolean spaceKey = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        if(spaceKey){
            if(direction == Direction.DOWN) bullets.add(new Bullet(screenX + 20,screenY - 20,1000,direction));
            else if(direction == Direction.UP) bullets.add(new Bullet(screenX + 20,screenY + 50,1000,direction));
            else if(direction == Direction.RIGHT || direction == Direction.DOWNRIGHT || direction == Direction.UPRIGHT) bullets.add(new Bullet(screenX + 50,screenY + 10,1000,direction));
            else if(direction == Direction.LEFT || direction == Direction.DOWNLEFT || direction == Direction.UPLEFT) bullets.add(new Bullet(screenX - 20,screenY + 10,1000,direction));
        }
    }
    public void draw(SpriteBatch batch, float stateTime){
        int index;
        if(direction == Direction.DOWN) index = 0;
        else if(direction == Direction.LEFT || direction == Direction.DOWNLEFT || direction == Direction.UPLEFT) index = 3;
        else if(direction == Direction.RIGHT || direction == Direction.DOWNRIGHT || direction == Direction.UPRIGHT) index = 2;
        else index = 1;

        if(status == Entity_Status.IDLE){
            batch.draw(idle[index], screenX, screenY,  this.getWidth() * 2, this.getHeight() * 2);
        }
        else if(status == Entity_Status.WALKING){
            batch.draw((TextureRegion) walking[index].getKeyFrame(stateTime, true), screenX, screenY,  this.getWidth() * 2, this.getHeight() * 2);
        }

        if(status == Entity_Status.ATTACKING){
            if(attackStatus == Attack_Status.STAB){
                batch.draw((TextureRegion) stabbing[index].getKeyFrame(stateTime, true), screenX, screenY,  this.getWidth() *2, this.getHeight()*2 );
            }else if(attackStatus == Attack_Status.SHOOT){
                batch.draw((TextureRegion) shootting[index].getKeyFrame(stateTime, true), screenX, screenY,  this.getWidth() *2, this.getHeight()*2 );
            }
        }
    }
}
