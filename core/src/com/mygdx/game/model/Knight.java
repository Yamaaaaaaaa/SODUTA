package com.mygdx.game.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.controller.Direction;
import com.mygdx.game.controller.movement.Player_Movement;
import com.mygdx.game.model.gamemusic.MusicGame;
import com.mygdx.game.view.GameScreen;

import java.util.ArrayList;

public class Knight extends Entity {
    public float screenX = 400, screenY = 400; // Cái này chỉ riêng tk NV Chính có.
    public GameScreen gameScreen;
    // ROLLS:
    private Texture texture_walking;
    private Texture texture_shooting;
    private Texture texture_stabbing;
    private Texture texture_death;
    private Animation[] walking;
    private Animation[] shootting;
    private Animation[] stabbing;
    private Animation[] death;
    private TextureRegion[] idle; // Ta chỉ set 1 số ảnh để làm IDLE thôi, Ko cần 1 cái Standing riêng, vì nó sẽ bị giật giật khi chuyển qua lại các status.

    //Bullet:
    public int bulletCounter = 50; // demo
    public int bulletMax = 50;
    public ArrayList<Bullet> bullets;
    public MusicGame shoot_Music, stab_Music, change_Weapon_toGun_Music, change_Weapon_toKnife_Music, knight_Death_Music, pickup_Item_Music, reloadBullet_Music;
    public Knight(GameScreen gameScreen, float x, float y, float speed, TiledMapTileLayer collsionLayer) {
        this.gameScreen = gameScreen;
        this.setMusic();
        // hinh anh
        this.texture_walking = new Texture("basic/character/Walk.png");
        this.texture_shooting = new Texture("basic/character/Shoot.png");
        this.texture_stabbing = new Texture("basic/character/Stab.png");
        this.texture_death = new Texture("basic/character/Death.png");
        this.setPosision(x,y);
        this.setSpeed_Stright(speed);
        this.setSpeed_Cross((float) Math.sqrt(speed * speed / 2));

        // chieu cao, phuong huong, trang thai:
        direction = Direction.DOWN;
        status = Entity_Status.IDLE;
        this.setWidth(32);
        this.setHeight(32);
        this.setAnimation();
        // atk, hp
        this.currentHp = 100;
        this.maxHP = 100;
        this.damage = 50;

        this.rectangle.x = 400 + 8;
        this.rectangle.y = 400;
        this.rectangle.width = 48;
        this.rectangle.height = 55;

        // Gọi cái class qua lý di chuyển ra
        this.moving = Player_Movement.getInstance();

        //collsion:
        this.collisionLayer = collsionLayer;

        // attack:
        this.attackStatus = Attack_Status.SHOOT; // Mặc định là ban đầu sẽ chém
        bullets = new ArrayList<Bullet>();
    }
    public void setMusic(){
        this.shoot_Music = new MusicGame(gameScreen.musicHandler.shoot, false);
        this.stab_Music = new MusicGame(gameScreen.musicHandler.stab, false);
        this.change_Weapon_toGun_Music = new MusicGame(gameScreen.musicHandler.change_Weapon_toGun, false);
        this.change_Weapon_toKnife_Music = new MusicGame(gameScreen.musicHandler.change_Weapon_toKnife, false);
        this.knight_Death_Music = new MusicGame(gameScreen.musicHandler.knight_Death, false);
        this.pickup_Item_Music = new MusicGame(gameScreen.musicHandler.pickup_Item, false);
        this.reloadBullet_Music = new MusicGame(gameScreen.musicHandler.reloadBullet, false);

    }

    private void setAnimation(){
        walking = new Animation[10];
        stabbing = new Animation[10];
        shootting = new Animation[10];
        death = new Animation[10];
        idle = new TextureRegion[10];
        TextureRegion[][] region1 = TextureRegion.split(this.texture_walking, this.getWidth(), this.getHeight());
        TextureRegion[][] region2 = TextureRegion.split(this.texture_stabbing, this.getWidth(), this.getHeight());
        TextureRegion[][] region3 = TextureRegion.split(this.texture_shooting, this.getWidth(), this.getHeight());
        TextureRegion[][] region4 = TextureRegion.split(this.texture_death, this.getWidth(), this.getHeight());
        for(int i = 0; i < 4; ++i){
            walking[i] = new Animation(0.2f, region1[i]);
            stabbing[i] = new Animation(0.2f, region2[i]);
            shootting[i] = new Animation(0.2f, region3[i]);
            death[i] = new Animation(0.2f, region4[i]);
            idle[i] = region1[i][1];
        }
    }
    public void update(){
        if(this.currentHp <= 0){
            this.status = Entity_Status.DEATH;
        }else{
            updateAttack();
            updateKill();
            this.moving.move(this, this.gameScreen);
        }
    }
    public void updateKill(){
        // check va cham đạn và monster
        ArrayList<Monster> rejMons = new ArrayList<Monster>();
        ArrayList<Bullet> rejBullet = new ArrayList<Bullet>();
        for(Monster monster : this.gameScreen.monsters){
            if(monster.status == Entity_Status.DEATH && monster.deathCountingTime >= 60){
                rejMons.add(monster);
            }
            else if(monster.status == Entity_Status.WALKING){
                for(Bullet bullet : this.bullets){
                    if(!bullet.remove && monster.status == Entity_Status.WALKING && monster.getRectangle().overlaps( bullet.rectangle)){
                        monster.currentHp -= this.damage;
                        rejBullet.add(bullet);
                        if(monster.currentHp <= 0) {
                            monster.status = Entity_Status.DEATH;
                        }
                    }
                }
            }
        }
        for(Bullet bullet : this.bullets) {
            if (bullet.remove == true) {
                rejBullet.add(bullet);
            }
        }
        this.gameScreen.monsters.removeAll(rejMons);
        this.bullets.removeAll(rejBullet);
    }
    public void updateAttack(){
        boolean changeWeapon = Gdx.input.isKeyJustPressed(Input.Keys.J);

        if(changeWeapon){
            if(this.attackStatus == Attack_Status.STAB) {
                this.attackStatus = Attack_Status.SHOOT;
                this.change_Weapon_toGun_Music.setPlay();
            }
            else if(this.attackStatus == Attack_Status.SHOOT) {
                this.attackStatus = Attack_Status.STAB;
                this.change_Weapon_toKnife_Music.setPlay();
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && this.attackStatus == Attack_Status.SHOOT){
            MusicGame shootms = new MusicGame(this.gameScreen.musicHandler.shoot,false);
            shootms.setVolumeMusic(0.5f);
            shootms.setPlay();
            if(this.direction == Direction.DOWN) this.bullets.add(new Bullet(this.gameScreen,this.getX() + 20,this.getY(),400,this.direction, this.collisionLayer));
            else if(this.direction == Direction.UP) this.bullets.add(new Bullet(this.gameScreen,this.getX() + 20,this.getY() + 32,400,this.direction, this.collisionLayer));
            else if(this.direction == Direction.RIGHT || this.direction == Direction.DOWNRIGHT || this.direction == Direction.UPRIGHT) this.bullets.add(new Bullet(this.gameScreen,this.getX() + 30,this.getY() + 10,400,this.direction, this.collisionLayer));
            else if(this.direction == Direction.LEFT || this.direction == Direction.DOWNLEFT || this.direction == Direction.UPLEFT) this.bullets.add(new Bullet(this.gameScreen,this.getX(),this.getY() + 10,400,this.direction, this.collisionLayer));
        }

        // update Bullet:
     //   if(this.attackStatus == Attack_Status.SHOOT){
        ArrayList<Bullet> bulletToRemove = new ArrayList<Bullet>();
        for(Bullet bullet : this.bullets){
            bullet.update();
        }
     //   }
    }

    public void draw(SpriteBatch batch, float stateTime, ShapeRenderer shapeRenderer){
        int index;
        //shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
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
        if(status == Entity_Status.DEATH){
            batch.draw((TextureRegion) death[index].getKeyFrame(stateTime, true), screenX, screenY,  this.getWidth() *2, this.getHeight()*2 );
        }


    }
}
