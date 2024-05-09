package com.mygdx.game.entity.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.entity.controller.Attack_Status;
import com.mygdx.game.entity.controller.Direction;
import com.mygdx.game.entity.controller.Entity_Status;
import com.mygdx.game.entity.controller.Activity;
import com.mygdx.game.screen.GameScreen;

import java.awt.*;

public class Monster extends Entity{
    public GameScreen gameScreen;
    // ROLLS:
    private Texture texture_walking;
  //  private Texture texture_shooting;
   // private Texture texture_stabbing;
    private Animation[] walking;
    private TextureRegion[] idle; // Ta chỉ set 1 số ảnh để làm IDLE thôi, Ko cần 1 cái Standing riêng, vì nó sẽ bị giật giật khi chuyển qua lại các status.

    // TẤN COONG:
    public Attack_Status attackStatus;
   // private Animation[] shootting;
  //  private Animation[] stabbing;

    // VA CHẠM
    public TiledMapTileLayer collisionLayer;

    public Monster(float x, float y, float speed, TiledMapTileLayer collsionLayer, GameScreen gameScreen, String direction_Static) {

        this.gameScreen = gameScreen;


        //image
        this.texture_walking = new Texture("basic/Slimes/Slime_Medium_Blue.png");
     //   this.texture_shooting = new Texture("basic/character/Shoot.png");
        //this.texture_stabbing = new Texture("basic/character/Stab.png");
        // position
        this.setPosision(x,y);


        //speed
        this.setSpeed_Stright(speed);
        this.setSpeed_Cross((float) Math.sqrt(speed * speed / 2));

        // first setting:
        this.direction_Static = direction_Static;
        if(direction_Static.equals("vertical")) {
            direction = Direction.DOWN;
            this.xMin = this.getX();
            this.yMin = this.getY() - 32;
            this.xMax = this.getX();
            this.yMax = this.getY() + 32;
        }
        else if(direction_Static.equals("horizontal")){
            direction = Direction.RIGHT;
            this.xMin = this.getX() - 32;
            this.yMin = this.getY();
            this.xMax = this.getX() + 32;
            this.yMax = this.getY();
        }

        status = Entity_Status.WALKING;
        this.setWidth(32);
        this.setHeight(32);
        this.setAnimation();
        this.setActivity(new Activity(this));

        //collsion:
        this.collisionLayer = collsionLayer;

        // attack:
       // this.attackStatus = Attack_Status.STAB; // Mặc định là ban đầu sẽ chém
    }

    public void setPosision(float x, float y){
        this.setX(x);
        this.setY(y);
    }
    private void setAnimation(){

        walking = new Animation[10];
     //   stabbing = new Animation[10];
       // shootting = new Animation[10];
        idle = new TextureRegion[10];
        TextureRegion[][] region1 = TextureRegion.split(this.texture_walking, this.getWidth(), this.getHeight());
     //   TextureRegion[][] region2 = TextureRegion.split(this.texture_stabbing, this.getWidth(), this.getHeight());
     //   TextureRegion[][] region3 = TextureRegion.split(this.texture_shooting, this.getWidth(), this.getHeight());
        for(int i = 0; i < 4; ++i){
            walking[i] = new Animation(0.2f, region1[i]);
        //    stabbing[i] = new Animation(0.2f, region2[i]);
        //    shootting[i] = new Animation(0.2f, region3[i]);

            idle[i] = region1[i][1];
        }
    }
    public void update(){
        this.activity.move_Update_Location(1);
    }
    public void draw(SpriteBatch batch, float stateTime){
        int index;
        if(direction == Direction.DOWN) index = 0;
        else if(direction == Direction.LEFT || direction == Direction.DOWNLEFT || direction == Direction.UPLEFT) index = 3;
        else if(direction == Direction.RIGHT || direction == Direction.DOWNRIGHT || direction == Direction.UPRIGHT) index = 1;
        else index = 2;

      //  System.out.println(this.gameScreen.knight.getX() + "-" + this.getX() + "-" + this.gameScreen.knight.screenX);
        float screenX = this.getX() - this.gameScreen.knight.getX() + this.gameScreen.knight.screenX;
        float screenY = this.getY() - this.gameScreen.knight.getY() + this.gameScreen.knight.screenY;

       // System.out.println("Knight: (" + this.gameScreen.knight.getX() + "," + this.gameScreen.knight.getY() + ")  " + screenX + " - " + screenY);
        if(status == Entity_Status.IDLE){
            batch.draw(idle[index], screenX, screenY,  this.getWidth() * 2, this.getHeight() * 2);
        }
        else if(status == Entity_Status.WALKING){

            // Khác 1 chút so với Knight, Khi knight nó luôn ở giữa màn hinhf.
            // Còn cái tk này là nó phải set dựa vào vị trí của tk knight so với bản đồ nữa. => Lại phải toán à :vvvv
            batch.draw((TextureRegion) walking[index].getKeyFrame(stateTime, true), screenX, screenY,  this.getWidth() * 2, this.getHeight() * 2);
        }

    }
}
