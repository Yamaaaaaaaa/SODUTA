package com.mygdx.game.entity.model;

import com.badlogic.gdx.Gdx;
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


        // Path finding:
        OnPath = true;
        // attack:
       // this.attackStatus = Attack_Status.STAB; // Mặc định là ban đầu sẽ chém
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
        if(OnPath == true){
            int goalCol = 15;
            int goalRow = 5;
            searchPath(goalCol, goalRow);
        }
        else direction = Direction.DOWN;

        float oldX, oldY, x, y;
        oldX = x = this.getX();
        oldY = y = this.getY();
        // Vector2 oldPosition = new Vector2(x, y);
        if(this.direction == Direction.UP){
            y += this.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
        }
        if(this.direction == Direction.DOWN){
            y -= this.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
        }
        if(this.direction == Direction.LEFT){
            x -= this.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
        }
        if(this.direction == Direction.RIGHT){
            x += this.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
        }
        if(this.direction == Direction.UPLEFT){
            y += this.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
            x -= this.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
        }
        if(this.direction == Direction.UPRIGHT){
            y += this.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
            x += this.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
        }
        if(this.direction == Direction.DOWNLEFT){
            y -= this.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
            x -= this.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
        }
        if(this.direction == Direction.DOWNRIGHT){
            y -= this.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
            x += this.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
        }

        //  Vector2 newPosition = new Vector2(x, y);
        this.setPosision(x, y);
    }
    public void searchPath(int goalCol, int goalRow){
        int startCol = (int) ((this.getX()) / 32);
        int startRow = (int) ((this.getY()) / 32);;

        gameScreen.pathFinder.setNodes(startCol, startRow, goalCol, goalRow, this);
        if(gameScreen.pathFinder.search()){
            // next x, y;
            int nextX = gameScreen.pathFinder.pathList.get(0).col + 32;
            int nextY = gameScreen.pathFinder.pathList.get(0).row + 32;

            if(this.getX() > nextX){
                if(this.getY() > nextY){
                    direction = Direction.DOWNLEFT;
                }else if(this.getY() == nextY){
                    direction = Direction.LEFT;
                }else if(this.getY() < nextY){
                    direction = Direction.UPLEFT;
                }
            }
            else if(this.getX() == nextX){
                if(this.getY() > nextY){
                    direction = Direction.DOWN;
                }else if(this.getY() == nextY){
                    direction = Direction.DOWN;
                }else if(this.getY() < nextY){
                    direction = Direction.UP;
                }
            }
            else{
                if(this.getY() > nextY){
                    direction = Direction.DOWNRIGHT;
                }else if(this.getY() == nextY){
                    direction = Direction.RIGHT;
                }else if(this.getY() < nextY){
                    direction = Direction.UPRIGHT;
                }
            }

            // timf thay dich
            int nextCol = gameScreen.pathFinder.pathList.get(0).col;
            int nextRow = gameScreen.pathFinder.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow){
                OnPath = false;
            }
        }
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
