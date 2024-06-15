package com.mygdx.game.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.controller.Direction;
import com.mygdx.game.controller.movement.Monster_Movement;
import com.mygdx.game.model.gamemusic.MusicGame;
import com.mygdx.game.view.GameScreen;

public class Monster extends Entity{
    public GameScreen gameScreen;
    // ROLLS:
    private Texture texture_walking;
  //  private Texture texture_shooting;
   // private Texture texture_stabbing;
    private Texture texture_death;
    private Animation[] walking;
    private Animation[] death;
    private TextureRegion[] idle; // Ta chỉ set 1 số ảnh để làm IDLE thôi, Ko cần 1 cái Standing riêng, vì nó sẽ bị giật giật khi chuyển qua lại các status.

    // TẤN COONG:
    public Attack_Status attackStatus;
   // private Animation[] shootting;
  //  private Animation[] stabbing;

    // CHẾT:
    public int deathCountingTime = 0;
    public Monster(TiledMapTileLayer collsionLayer, GameScreen gameScreen, String direction_Static, float xKnight, float yKnight, int numberMap, boolean godmod) {
        this.gameScreen = gameScreen;
        //image
            this.texture_walking = new Texture("Apocalypse Character Pack/Zombie/Walk.png");
            //this.texture_shooting = new Texture("basic/character/Shoot.png");
            //this.texture_stabbing = new Texture("basic/character/Stab.png");
            this.texture_death = new Texture("Apocalypse Character Pack/Zombie/Death.png");
        // position
        if (numberMap == 1) this.setPlaceGenMap1(xKnight, yKnight);
        else  this.setPlaceGenMap2(xKnight, yKnight);
        //speed
        int sp = 80;
            if(godmod) sp = 160;
            this.setSpeed_Stright(sp);
            this.setSpeed_Cross((float) Math.sqrt(sp*sp / 2));
        // atk, hp
            this.currentHp = 100;
            this.maxHP = 100;
            this.damage = 20;
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

        //collsion:
        this.collisionLayer = collsionLayer;

        // quanr ly di chuyen
        this.moving = Monster_Movement.getInstance();

        rectangle.width = 48;
        rectangle.height = 55;
        // attack:
       // this.attackStatus = Attack_Status.STAB; // Mặc định là ban đầu sẽ chém

        //health:

    }

    private void setAnimation(){
        walking = new Animation[15];
     //   stabbing = new Animation[10];
       // shootting = new Animation[10];
        death = new Animation[15];

        idle = new TextureRegion[15];
        TextureRegion[][] region1 = TextureRegion.split(this.texture_walking, this.getWidth(), this.getHeight());
     //   TextureRegion[][] region2 = TextureRegion.split(this.texture_stabbing, this.getWidth(), this.getHeight());
     //   TextureRegion[][] region3 = TextureRegion.split(this.texture_shooting, this.getWidth(), this.getHeight());
        TextureRegion[][] region4 = TextureRegion.split(this.texture_death, this.getWidth(), this.getHeight());
        for(int i = 0; i < 4; ++i){
            walking[i] = new Animation(0.2f, region1[i]);
            death[i] = new Animation(0.2f, region4[i]);
        //    stabbing[i] = new Animation(0.2f, region2[i]);
        //    shootting[i] = new Animation(0.2f, region3[i]);
            idle[i] = region1[i][1];
        }
    }
    public void update(){
        if(this.status == Entity_Status.WALKING) this.moving.move(this,this.gameScreen);
    }

    public void draw(SpriteBatch batch, float stateTime, ShapeRenderer shapeRenderer){
        int index;
        if(direction == Direction.DOWN) index = 0;
        else if(direction == Direction.LEFT || direction == Direction.DOWNLEFT || direction == Direction.UPLEFT) index = 3;
        else if(direction == Direction.RIGHT || direction == Direction.DOWNRIGHT || direction == Direction.UPRIGHT) index = 2;
        else index = 1;

        //  System.out.println(this.gameScreen.knight.getX() + "-" + this.getX() + "-" + this.gameScreen.knight.screenX);
        float screenX = this.getX() - this.gameScreen.knight.getX() + this.gameScreen.knight.screenX;
        float screenY = this.getY() - this.gameScreen.knight.getY() + this.gameScreen.knight.screenY;
        rectangle.x = screenX +8;
        rectangle.y = screenY;
        //shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        check_MonsterAttackKnight();
        if(this.status == Entity_Status.WALKING){
            drawHealthBar(shapeRenderer, stateTime, screenX, screenY, index);
            drawMonster(batch, stateTime, screenX, screenY, index);
        }
        else if(this.status == Entity_Status.DEATH){
            drawMonster(batch, stateTime, screenX, screenY, index);
        }
    }
    int attackCounter = 0;
    public void check_MonsterAttackKnight(){
        if(this.status == Entity_Status.WALKING && this.getRectangle().overlaps( this.gameScreen.knight.rectangle)){
            if(attackCounter % 60 == 0) {
                this.gameScreen.knight.currentHp -= this.damage;
                MusicGame zombieAttack_Music = new MusicGame(gameScreen.musicHandler.zombie_Eating, false);
                zombieAttack_Music.setVolumeMusic(0.5f);
                zombieAttack_Music.setPlay();
            }
            attackCounter ++;
            if(attackCounter > 6000000) attackCounter = 0;
        }
    }
    private void drawHealthBar(ShapeRenderer shapeRenderer, float stateTime, float screenX, float screenY, int index){
        int hpBarWidth = (this.getWidth() * 2) * this.currentHp / this.maxHP;
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(screenX, screenY + this.getHeight() * 2, hpBarWidth, 8);
    }
    private void drawMonster(SpriteBatch batch, float stateTime, float screenX, float screenY, int index){
        // System.out.println("Knight: (" + this.gameScreen.knight.getX() + "," + this.gameScreen.knight.getY() + ")  " + screenX + " - " + screenY);
        if(status == Entity_Status.IDLE){
            batch.draw(idle[index], screenX, screenY,  this.getWidth()*2, this.getHeight()*2);
        }
        else if(status == Entity_Status.WALKING){
            // Khác 1 chút so với Knight, Khi knight nó luôn ở giữa màn hinhf.
            // Còn cái tk này là nó phải set dựa vào vị trí của tk knight so với bản đồ nữa. => Lại phải toán à :vvvv
            batch.draw((TextureRegion) walking[index].getKeyFrame(stateTime, true), screenX, screenY,  this.getWidth() * 2, this.getHeight() * 2);
        }
        else if(status == Entity_Status.DEATH){
            deathCountingTime += 1;
            //System.out.println(deathCountingTime);
            batch.draw((TextureRegion) death[index].getKeyFrame(stateTime, true), screenX, screenY,  this.getWidth() * 2, this.getHeight() * 2);
        }
    }
    public void setPlaceGen(float xKnight, float yKnight){
        int kc = 800/2; // 800 la do rong screen
        int x = MathUtils.random(1, 4); // tren, phai, duoi, trai
        int randomX = MathUtils.random(-kc, kc);
        int randomY = MathUtils.random(-kc, kc);
        setPosision((xKnight + randomX) > 0 ? (xKnight + randomX) : 0, (yKnight + randomY) > 0 ? (yKnight + randomY): 0);
       /* if(x == 1){
            setPosision(xKnight + random, yKnight + kc);
        }else if(x == 2){
            setPosision(xKnight + kc, yKnight + random);
        } else if(x == 3){
            setPosision(xKnight + random, yKnight - kc);
        }else if(x == 4){
            setPosision(xKnight - kc, yKnight + random);
        }*/
    }

    public void setPlaceGenMap2 (float x, float y){ // float xKnight, float yKnight
        int area = findAreaKnightMap2(x, y);
        int tileSize = 32;
        if(area == 1){
            setPosision(30*tileSize, 45*tileSize);
        }else if(area == 2){
            setPosision(50*tileSize, 55*tileSize);
        }else if(area == 3){
            setPosision(45*tileSize, 35*tileSize);
        }else if(area == 4){
            setPosision(15*tileSize, 65*tileSize);
        }else if(area == 5){
            setPosision(65*tileSize, 65*tileSize);
        }else if(area == 6){
            setPosision(65*tileSize, 15*tileSize);
        }else if(area == 7){
            setPosision(15*tileSize, 15*tileSize);
        }else setPosision(63*tileSize, 40*tileSize);
    }
    public int findAreaKnightMap2(float x, float y){
        int tileSize = 32;
        x = x/tileSize; y = y/tileSize;
        if(x > 20 && x < 40 && y > 40 && y < 60) return 1;
        else if(x > 40 && x < 60 && y > 40 && y < 60) return 2;
        else if(x > 20 && x < 60 && y > 20 && y < 40) return 3;
        else if( x < 40 && y > 40 ) return 4;
        else if(x > 40  && y > 40 ) return 5;
        else if(x > 40  &&  y < 40) return 6;
        else return 7;
    }
    public void setPlaceGenMap1 (float x, float y){ // float xKnight, float yKnight
        int area = findAreaKnightMap1(x, y);
        int tileSize = 32;
        if(area == 1){
            setPosision(15*tileSize, 80*tileSize);
        }else if(area == 2){
            setPosision(30*tileSize, 90*tileSize);
        }else if(area == 3){
            setPosision(86*tileSize, 70*tileSize);
        }else if(area == 4){
            setPosision(90*tileSize, 30*tileSize);
        }else if(area == 5){
            setPosision(50*tileSize, 55*tileSize);
        }else if(area == 6){
            setPosision(40*tileSize, 20*tileSize);
        }
    }
    public int findAreaKnightMap1(float x, float y){
        int tileSize = 32;
        x = x/tileSize; y = y/tileSize;
        if(x < 20 && y > 70 ) return 1;
        else if(x > 20 && x < 60 && y > 70 ) return 2;
        else if(x > 60 && y > 60) return 3;
        else if( x > 80 && x < 95 && y > 20 && y < 45 ) return 4;
        else if(y > 30  && y < 60 ) return 5;
        else  return 6; // y < 30;
    }


}
