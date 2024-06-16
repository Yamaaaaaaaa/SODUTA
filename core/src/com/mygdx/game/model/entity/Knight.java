package com.mygdx.game.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
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
    private Texture texture_slash_up;
    private Texture texture_slash_down;
    private Texture texture_slash_left;
    private Texture texture_slash_right;

    public Animation[] getWalking() {
        return walking;
    }

    private Animation[] walking;
    private Animation[] shootting;
    private Animation[] stabbing;
    private Animation[] death;
    private Animation[] slashing_right;
    private Animation[] slashing_up;
    private Animation[] slashing_down;
    private Animation[] slashing_left;
    private TextureRegion[] idle; // Ta chỉ set 1 số ảnh để làm IDLE thôi, Ko cần 1 cái Standing riêng, vì nó sẽ bị giật giật khi chuyển qua lại các status.

    //Bullet:
    public int bulletCounter = 50; // demo
    public int bulletMax = 50;
    public ArrayList<Bullet> bullets;
    public MusicGame shoot_Music, stab_Music, change_Weapon_toGun_Music, change_Weapon_toKnife_Music, knight_Death_Music, pickup_Item_Music, reloadBullet_Music;

    // Point - Counter.
    public int point_Counter;
    //Tấn công bằng dao:
    public Rectangle stab_Border = new Rectangle();
    //Item:
    public int counter_ItemBullet = 0;
    public int counter_MedKit = 0;
    public Knight(){}
    public Knight(GameScreen gameScreen, float x, float y, float speed, TiledMapTileLayer collsionLayer) {
        this.gameScreen = gameScreen;
        this.setMusic();
        // hinh anh
        this.texture_walking = new Texture("basic/character/Walk.png");
        this.texture_shooting = new Texture("basic/character/Shoot.png");
        this.texture_stabbing = new Texture("basic/character/Stab.png");
        this.texture_death = new Texture("basic/character/Death.png");
        this.texture_slash_right = new Texture("basic/slash/small_sting.png");
        this.texture_slash_up = new Texture("basic/slash/stingup.png");
        this.texture_slash_down = new Texture("basic/slash/stingdown.png");
        this.texture_slash_left = new Texture("basic/slash/stingleft.png");


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

        this.damageGun = 50;
        this.damageKnife = 40;

        this.rectangle.x = 400 + 8;
        this.rectangle.y = 400;
        this.rectangle.width = 48;
        this.rectangle.height = 55;


        this.stab_Border.x = (int) (this.rectangle.x + 13);
        this.stab_Border.y = (int) (this.rectangle.y - 50);
        this.stab_Border.width = 24;
        this.stab_Border.height = 44;


        // Gọi cái class qua lý di chuyển ra
        this.moving = Player_Movement.getInstance();

        //collsion:
        this.collisionLayer = collsionLayer;

        // attack:
        this.attackStatus = Attack_Status.SHOOT; // Mặc định là ban đầu sẽ chém
        bullets = new ArrayList<Bullet>();

        this.point_Counter = 0;

        //Item:

    }
    public void setMusic(){
        this.shoot_Music = new MusicGame(gameScreen.musicHandler.shoot, false);
        this.stab_Music = new MusicGame(gameScreen.musicHandler.stab, false);
        this.change_Weapon_toGun_Music = new MusicGame(gameScreen.musicHandler.change_Weapon_toGun, false);
        this.change_Weapon_toKnife_Music = new MusicGame(gameScreen.musicHandler.change_Weapon_toKnife, false);
        this.knight_Death_Music = new MusicGame(gameScreen.musicHandler.knight_Death, false);

        this.reloadBullet_Music = new MusicGame(gameScreen.musicHandler.reloadBullet, false);

    }

    private void setAnimation(){
        walking = new Animation[10];
        stabbing = new Animation[10];
        shootting = new Animation[10];
        slashing_right = new Animation[10];
        slashing_up = new Animation[10];
        slashing_down = new Animation[10];
        slashing_left = new Animation[10];


        death = new Animation[10];
        idle = new TextureRegion[10];

        TextureRegion[][] region1 = TextureRegion.split(this.texture_walking, this.getWidth(), this.getHeight());
        TextureRegion[][] region2 = TextureRegion.split(this.texture_stabbing, this.getWidth(), this.getHeight());
        TextureRegion[][] region3 = TextureRegion.split(this.texture_shooting, this.getWidth(), this.getHeight());
        TextureRegion[][] region4 = TextureRegion.split(this.texture_death, this.getWidth(), this.getHeight());
        TextureRegion[][] region5 = TextureRegion.split(this.texture_slash_right, 88, 45);
        TextureRegion[][] region6 = TextureRegion.split(this.texture_slash_up, 45, 89);

        TextureRegion[][] region7 = TextureRegion.split(this.texture_slash_down, 45, 89);
        TextureRegion[][] region8 = TextureRegion.split(this.texture_slash_left, 88, 45);
        for(int i = 0; i < 4; ++i){
            walking[i] = new Animation(0.2f, region1[i]);
            stabbing[i] = new Animation(0.2f, region2[i]);
            shootting[i] = new Animation(0.2f, region3[i]);
            death[i] = new Animation(0.2f, region4[i]);
            idle[i] = region1[i][1];
        }
        for(int i = 0; i < 1; ++i){
            slashing_right[i] = new Animation(0.3f, region5[i]);
            slashing_up[i] = new Animation(0.3f, region6[i]);
            slashing_left[i] = new Animation(0.3f, region8[i]);
            slashing_down[i] = new Animation(0.3f, region7[i]);
        }
    }
    int checkdie = 0;
    int timeAnimationDeath = 0;
    public void update(){
       if(this.currentHp <= 0){
            if(checkdie == 0) {
                this.updateRanking();
                checkdie = 1;
            }
            this.status = Entity_Status.DEATH;
            this.timeAnimationDeath ++;
            if(this.currentHp < 0) currentHp = 0;
        }else{
            this.moving.move(this, this.gameScreen);
            check_PickUpItem();
            updateAttack();
            update_Healing();
            updateKill_byBullet();
        }
    }
    public int rank = 0;
    public void updateRanking(){
        rank = this.gameScreen.spaceGame.fileHandler.checkRanking(this.point_Counter);
        this.gameScreen.spaceGame.fileHandler.addRanking("SODUTA",this.point_Counter);
        this.gameScreen.spaceGame.fileHandler.coutRanking();
    }
    public void update_Healing(){
        if(status != Entity_Status.DEATH && Gdx.input.isKeyJustPressed(Input.Keys.H)){
            if(counter_MedKit > 0){
                this.currentHp = this.maxHP;
                MusicGame healing_Music = new MusicGame(this.gameScreen.musicHandler.healing,false);
                healing_Music.setVolumeMusic(0.5f);
                healing_Music.setPlay();
                counter_MedKit --;
            }
            else {
                MusicGame outofBullet_Music = new MusicGame(this.gameScreen.musicHandler.outOfBullet,false);
                outofBullet_Music.setVolumeMusic(0.5f);
                outofBullet_Music.setPlay();
            }
        }
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
            if(this.bulletCounter > 0){
                MusicGame shootms = new MusicGame(this.gameScreen.musicHandler.shoot,false);
                shootms.setVolumeMusic(0.2f);
                shootms.setPlay();
                if(this.direction == Direction.DOWN) this.bullets.add(new Bullet(this.gameScreen,this.getX() + 20,this.getY(),400,this.direction, this.collisionLayer));
                else if(this.direction == Direction.UP) this.bullets.add(new Bullet(this.gameScreen,this.getX() + 20,this.getY() + 32,400,this.direction, this.collisionLayer));
                else if(this.direction == Direction.RIGHT || this.direction == Direction.DOWNRIGHT || this.direction == Direction.UPRIGHT) this.bullets.add(new Bullet(this.gameScreen,this.getX() + 30,this.getY() + 10,400,this.direction, this.collisionLayer));
                else if(this.direction == Direction.LEFT || this.direction == Direction.DOWNLEFT || this.direction == Direction.UPLEFT) this.bullets.add(new Bullet(this.gameScreen,this.getX(),this.getY() + 10,400,this.direction, this.collisionLayer));
                this.bulletCounter --;
            }
            else {
                MusicGame outofBullet_Music = new MusicGame(this.gameScreen.musicHandler.outOfBullet,false);
                outofBullet_Music.setVolumeMusic(1f);
                outofBullet_Music.setPlay();
            }
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && this.attackStatus == Attack_Status.STAB){
            MusicGame stabMusic = new MusicGame(this.gameScreen.musicHandler.stab, false);
            stabMusic.setVolumeMusic(0.2f);
            stabMusic.setPlay();

            if(this.direction == Direction.UP || this.direction == Direction.UPLEFT || direction == Direction.UPRIGHT){
                this.counterSlashAnimation_up = 0;
                this.stab_Border.x = (int) (this.rectangle.x + 13);
                this.stab_Border.y = (int) (this.rectangle.y + 60);
                this.stab_Border.width = 24;
                this.stab_Border.height = 44;
            }
            else if(this.direction == Direction.DOWN || this.direction == Direction.DOWNLEFT || direction == Direction.DOWNRIGHT){
                this.counterSlashAnimation_down = 0;
                this.stab_Border.x = (int) (this.rectangle.x + 13);
                this.stab_Border.y = (int) (this.rectangle.y - 50);
                this.stab_Border.width = 24;
                this.stab_Border.height = 44;
            }
            else if(this.direction == Direction.RIGHT){
                this.counterSlashAnimation_right = 0;
                this.stab_Border.x = (int) (this.rectangle.x + 10 + 30);
                this.stab_Border.y = (int) (this.rectangle.y + 20);
                this.stab_Border.width = 44;
                this.stab_Border.height = 22;
            }
            else if(this.direction == Direction.LEFT){
                this.counterSlashAnimation_left = 0;
                this.stab_Border.x = (int) (this.rectangle.x + 10 - 50);
                this.stab_Border.y = (int) (this.rectangle.y + 20);
                this.stab_Border.width = 44;
                this.stab_Border.height = 22;
            }

            updateKill_byKnife();
        }

        // Sạc đạn
        if(Gdx.input.isKeyJustPressed(Input.Keys.R) && this.attackStatus == Attack_Status.SHOOT){
            if(counter_ItemBullet > 0){
                this.bulletCounter = 50;
                MusicGame reloadBullet_Music = new MusicGame(this.gameScreen.musicHandler.reloadBullet,false);
                reloadBullet_Music.setVolumeMusic(1.0f);
                reloadBullet_Music.setPlay();
                counter_ItemBullet --;
            }
            else {
                MusicGame outofBullet_Music = new MusicGame(this.gameScreen.musicHandler.outOfBullet,false);
                outofBullet_Music.setVolumeMusic(0.5f);
                outofBullet_Music.setPlay();
            }
        }

        // update Bullet:
        //   if(this.attackStatus == Attack_Status.SHOOT){
        ArrayList<Bullet> bulletToRemove = new ArrayList<Bullet>();
        for(Bullet bullet : this.bullets){
            bullet.update();
        }
        //   }
    }
    public void updateKill_byKnife(){
        // check va cham đạn và monster
        ArrayList<Monster> rejMons = new ArrayList<Monster>();
        for(Monster monster : this.gameScreen.monsters){
            if(monster.status == Entity_Status.DEATH && monster.deathCountingTime >= 60){
                rejMons.add(monster);
            }
            else if(monster.status == Entity_Status.WALKING){
                if(monster.getRectangle().overlaps(stab_Border)){
                    monster.currentHp -= this.damageKnife;
                    if(monster.currentHp <= 0) {
                        monster.status = Entity_Status.DEATH;
                    }
                }
            }
        }
        point_Counter += rejMons.size();
        this.gameScreen.monsters.removeAll(rejMons);
    }

    public void updateKill_byBullet(){
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
                        monster.currentHp -= this.damageGun;
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
        point_Counter += rejMons.size();
        this.gameScreen.monsters.removeAll(rejMons);
        this.bullets.removeAll(rejBullet);
    }
    public void check_PickUpItem(){
        for(Items it: this.gameScreen.itemBullets){
            if(this.getRectangle().overlaps(it.rectangle)){
                if(it.alive){
                    // hiển thị yêu cầu bấm phím F đêr nhặt
                    it.collisionKnight = true;
                    // check bấm F
                    if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
                        MusicGame pickup_Item_Music = new MusicGame(gameScreen.musicHandler.pickup_Item, false);
                        pickup_Item_Music.setVolumeMusic(0.5f);
                        pickup_Item_Music.setPlay();
                        it.alive = false;
                        it.counterTime = 3600 * 2; //3600 * 3; // tru dan ve 0
                        this.counter_ItemBullet ++;
                    }
                }
                else it.collisionKnight = false;
            }
            else it.collisionKnight = false;
        }

        for(Items med: this.gameScreen.medKits){
            if(this.getRectangle().overlaps(med.rectangle)){
                if(med.alive){
                    // hiển thị yêu cầu bấm phím F đêr nhặt
                    med.collisionKnight = true;
                    // check bấm F
                    if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
                        MusicGame pickup_Item_Music = new MusicGame(gameScreen.musicHandler.pickup_Item, false);
                        pickup_Item_Music.setVolumeMusic(0.5f);
                        pickup_Item_Music.setPlay();
                        med.alive = false;
                        med.counterTime = 3600 * 3; //3600 * 3; // tru dan ve 0
                        this.counter_MedKit ++;
                    }
                }
                else med.collisionKnight = false;
            }
            else med.collisionKnight = false;
        }
    }

    private int counterSlashAnimation_up = 30;
    private int counterSlashAnimation_down = 30;
    private int counterSlashAnimation_left = 30;
    private int counterSlashAnimation_right = 30;

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
            if(this.timeAnimationDeath == 60) {
                this.gameScreen.setEndGame_Screen(this.point_Counter, this.rank);
            }
        }

        //shapeRenderer.rect(stab_Border.x, stab_Border.y, stab_Border.width, stab_Border.height);
        if(counterSlashAnimation_right < 15){
            batch.draw((TextureRegion) slashing_right[0].getKeyFrame(stateTime, true), stab_Border.x, stab_Border.y,stab_Border.width * 2, stab_Border.height * 2);
            this.counterSlashAnimation_right ++;
        }
        if(counterSlashAnimation_left < 15){
            batch.draw((TextureRegion) slashing_left[0].getKeyFrame(stateTime, true), stab_Border.x, stab_Border.y,stab_Border.width , stab_Border.height * 2);
            this.counterSlashAnimation_left ++;
        }
        if(counterSlashAnimation_up < 15){
            batch.draw((TextureRegion) slashing_up[0].getKeyFrame(stateTime, true), stab_Border.x, stab_Border.y,stab_Border.width * 2, stab_Border.height );
            this.counterSlashAnimation_up ++;
        }
        if(counterSlashAnimation_down < 15){
            batch.draw((TextureRegion) slashing_down[0].getKeyFrame(stateTime, true), stab_Border.x, stab_Border.y,stab_Border.width * 2, stab_Border.height * 2);
            this.counterSlashAnimation_down ++;
        }
    }
}
