package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.entity.controller.Attack_Status;
import com.mygdx.game.entity.controller.Direction;
import com.mygdx.game.entity.controller.Entity_Status;
import com.mygdx.game.entity.controller.Moving;

import java.security.Key;

public class Knight extends Entity{
    public Direction direction;
    public Entity_Status status;



    public float screenX = 400, screenY = 400;

    // SETTING BAN ĐẦU:
    private float speed_Stright;
    private float speed_Cross;
    private int width;
    private int height;

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

    //DI CHUYỂN
    private Moving moving;
    // ROLLS:
    private Animation[] walking;
    private TextureRegion[] idle; // Ta chỉ set 1 số ảnh để làm IDLE thôi, Ko cần 1 cái Standing riêng, vì nó sẽ bị giật giật khi chuyển qua lại các status.

    // TẤN COONG:
    public Attack_Status attackStatus;
    private Animation[] shootting;
    private Animation[] stabbing;


    // VA CHẠM
    public TiledMapTileLayer collisionLayer;

    public Knight(float x, float y, float speed, TiledMapTileLayer collsionLayer) {
        //image
        this.setTexture_walking(new Texture("basic/character/Walk.png"));
        this.setTexture_shooting(new Texture("basic/character/Shoot.png"));
        this.setTexture_stabbing(new Texture("basic/character/Stab.png"));
        // position
        this.setPosision(x,y);

        //speed
        this.speed_Stright = speed;
        this.speed_Cross = (float) Math.sqrt(speed * speed / 2);

        // first setting:
        direction = Direction.DOWN;
        status = Entity_Status.IDLE;
        this.width = 32;
        this.height = 32;
        this.setAnimation();
        moving = new Moving(this);

        //collsion:
        this.collisionLayer = collsionLayer;

        // attack:
        this.attackStatus = Attack_Status.STAB; // Mặc định là ban đầu sẽ chém
    }

    public void setPosision(float x, float y){
        this.setX(x);
        this.setY(y);
    }
    private void setAnimation(){
        walking = new Animation[10];
        stabbing = new Animation[10];
        shootting = new Animation[10];
        idle = new TextureRegion[10];
        TextureRegion[][] region1 = TextureRegion.split(this.getTexture_walking(), this.width, this.height);
        TextureRegion[][] region2 = TextureRegion.split(this.getTexture_stabbing(), this.width, this.height);
        TextureRegion[][] region3 = TextureRegion.split(this.getTexture_shooting(), this.width, this.height);
        for(int i = 0; i < 4; ++i){
            walking[i] = new Animation(0.2f, region1[i]);
            stabbing[i] = new Animation(0.2f, region2[i]);
            shootting[i] = new Animation(0.2f, region3[i]);

            idle[i] = region1[i][1];
        }
    }
    public void update(){
        moving.move_Update_Location();
    }
    public void draw(SpriteBatch batch, float stateTime){
        int index;

        if(direction == Direction.DOWN) index = 0;
        else if(direction == Direction.LEFT || direction == Direction.DOWNLEFT || direction == Direction.UPLEFT) index = 3;
        else if(direction == Direction.RIGHT || direction == Direction.DOWNRIGHT || direction == Direction.UPRIGHT) index = 2;
        else index = 1;

        if(status == Entity_Status.IDLE){
            batch.draw(idle[index], screenX, screenY, width * 2, height * 2);
        }
        else if(status == Entity_Status.WALKING){
            batch.draw((TextureRegion) walking[index].getKeyFrame(stateTime, true), screenX, screenY, width * 2, height * 2);
        }
        else if(status == Entity_Status.ATTACK){
            if(attackStatus == Attack_Status.STAB){
                batch.draw((TextureRegion) stabbing[index].getKeyFrame(stateTime, true), screenX, screenY, width * 2, height * 2);
            }else if(attackStatus == Attack_Status.SHOOT){
                batch.draw((TextureRegion) shootting[index].getKeyFrame(stateTime, true), screenX, screenY, width * 2, height * 2);
            }
        }
    }
}
