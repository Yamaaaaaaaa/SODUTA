package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.SpaceGame;

import com.mygdx.game.model.entity.*;
import com.mygdx.game.model.gamemusic.MusicGame;
import com.mygdx.game.model.gamemusic.MusicHandler;

import java.util.ArrayList;

public class GameScreen implements Screen {
    public SpaceGame spaceGame;
    private SpriteBatch batch;
    private String mapPath;
    private ShapeRenderer shapeRenderer;
    public MusicHandler musicHandler;
    private float stateTime = 0;
    private float tile_Size = 32;
    private int numberMap;
    public float timePlayed;

//NHÂN VẬT:
    // DI CHUYEN NHAN VAT
    public float speed;
    public Knight knight;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    public int roll;
    // VA CHAM
    private TiledMapTileLayer collsionLayer;
    private TmxMapLoader loader;
    private TiledMap map;
// QUÁI VẬT:
    public ArrayList<Monster> monsters ;
    private int sprites_Counting = 0;
    private int sprites_Num = 1;
    private long timeGenBabyMonster;
// UI
    private Status_UI statusUI;
    // Pause:
    public boolean isPaused = false;
    public boolean newGame = true;
// MUSIC:
    public MusicGame background_Game_Music, zombie_WaveStart_Music;
// ITEM
    public ArrayList<Item_Bullet> itemBullets;
    public ArrayList<Item_Bullet> medKits;
//Mod cuồng loạn
    public int time_s;
    public int time_m;
    public int timeEndGodWave_m = 0, timeEndGodWave_s = 0;
    public boolean godMod = false;

    public GameScreen(SpaceGame spaceGame, String mapPath, int mapType) {
        timePlayed = 0;
        this.numberMap = mapType;
        this.mapPath = mapPath;
        this.spaceGame = spaceGame;
        batch = spaceGame.getBatch();
        shapeRenderer = spaceGame.shapeRenderer;
        this.musicHandler = new MusicHandler();
        this.background_Game_Music = new MusicGame(this.musicHandler.background_Game, true);
        this.background_Game_Music.setVolumeMusic(0.3f);
        this.background_Game_Music.setPlay();

        this.zombie_WaveStart_Music = new MusicGame(this.musicHandler.zombie_WaveStart, false);
        this.zombie_WaveStart_Music.setVolumeMusic(0.8f);
        // this.zombie_WaveStart_Music.setPlay();
        itemBullets = new ArrayList<Item_Bullet>();
        medKits = new ArrayList<Item_Bullet>();
        if(mapType == 1){
            setMap1(this.spaceGame, this.mapPath);
        }
        else if(mapType == 2){
            setMap2(this.spaceGame, this.mapPath);
        }
    }
    private void setMap1(SpaceGame spaceGame, String mapPath){
    // set vị trí item xuất hiện
        // bang dan
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 28 * tile_Size, 94 * tile_Size));
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 95 * tile_Size, 95 * tile_Size));
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 34 * tile_Size, 6 * tile_Size));
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 84 * tile_Size, 6 * tile_Size));
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 84 * tile_Size, 39 * tile_Size));
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 84 * tile_Size, 27 * tile_Size));


        //medkit
        medKits.add(new Item_Bullet("basic/item/medkit.png",this, 47 * tile_Size, 21 * tile_Size));
        medKits.add(new Item_Bullet("basic/item/medkit.png",this, 40 * tile_Size, 31 * tile_Size));
        medKits.add(new Item_Bullet("basic/item/medkit.png",this, 84 * tile_Size, 33 * tile_Size));
        medKits.add(new Item_Bullet("basic/item/medkit.png",this, 6 * tile_Size, 80 * tile_Size));

    }
    private void setMap2(SpaceGame spaceGame, String mapPath){
    // set vị trí item xuất hiện
        // bang dan
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 10 * tile_Size, 70 * tile_Size));
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 35 * tile_Size, 70 * tile_Size));
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 70 * tile_Size, 70 * tile_Size));
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 73 * tile_Size, 36 * tile_Size));
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 7 * tile_Size, 7 * tile_Size));
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 42 * tile_Size, 54 * tile_Size));
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 42 * tile_Size, 51 * tile_Size));
        itemBullets.add(new Item_Bullet("basic/item/rifleAmmo.png",this, 42 * tile_Size, 48 * tile_Size));

        //medkit
        medKits.add(new Item_Bullet("basic/item/medkit.png",this, 35 * tile_Size, 45 * tile_Size));
        medKits.add(new Item_Bullet("basic/item/medkit.png",this, 38 * tile_Size, 30 * tile_Size));
        medKits.add(new Item_Bullet("basic/item/medkit.png",this, 53 * tile_Size, 46 * tile_Size));
        medKits.add(new Item_Bullet("basic/item/medkit.png",this, 68 * tile_Size, 10 * tile_Size));
    }
    @Override
    public void show() {
        if(this.newGame) {
            loader = new TmxMapLoader();
            map = loader.load(this.mapPath);
            renderer = new OrthogonalTiledMapRenderer(map);
            camera = new OrthographicCamera();
            //     camera.zoom = .8f;

            this.collsionLayer = (TiledMapTileLayer) map.getLayers().get(1);
            // System.out.println(collsionLayer.getName());
            this.speed = 250;
            this.knight = new Knight(this, tile_Size * 35, tile_Size * 50, this.speed, collsionLayer);
            monsters = new ArrayList<Monster>();
            timeGenBabyMonster = (Long) TimeUtils.nanoTime();
            this.statusUI = new Status_UI(this);
            this.newGame = false;
        }
    }

    @Override
    public void render(float delta) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        timePlayed += deltaTime;


        Gdx.gl.glClearColor(0.113f, 0.102f, 0.16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            isPaused = true;
        }
        if(isPaused) setPauseGame();

        camera.position.x = knight.getX();
        camera.position.y = knight.getY();
        camera.update();

        renderer.setView(camera);
        renderer.render();

        //update
            knight.update();

            time_s = (int) this.timePlayed;
            time_m = (int) time_s / 60;
            time_s = time_s % 60;

            if(time_m == timeEndGodWave_m && time_s == timeEndGodWave_s && godMod == true){
                godMod = false;
            }


            if(time_m % 2 == 0 && time_s == 0 && time_m != 0){
                timeEndGodWave_m = time_m;
                timeEndGodWave_s = 30;
                MusicGame zombie_WaveStart_Music = new MusicGame(this.musicHandler.zombie_WaveStart, false);
                zombie_WaveStart_Music.setVolumeMusic(0.7f);
                zombie_WaveStart_Music.setPlay();
                godMod = true;
            }

            if (TimeUtils.nanoTime() - timeGenBabyMonster >= 2000000000 && knight.currentHp > 0 && monsters.size() < 25) {
                Monster monster = new Monster(collsionLayer, this, "vertical", knight.getX(), knight.getY(), numberMap, godMod);
                //   this.zombie_WaveStart_Music.setPlay();
                monsters.add(monster);
                timeGenBabyMonster = (Long) TimeUtils.nanoTime();
            }
            statusUI.update();

            for (Monster monster : monsters) {
                monster.update();
            }
            stateTime += delta;


        //draw , shape trc, batch sau.
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);//Filled
        batch.begin();
        batch.draw(new Texture("button/GameScreen/Text.png"),-5,750,128*2,32*2);
        for(Item_Bullet it: this.itemBullets){
            it.update();
            if(it.alive){
                it.render(batch, shapeRenderer);
            }
        }
        for(Item_Bullet med : this.medKits){
            med.update();
            if(med.alive){
                med.render(batch, shapeRenderer);
            }
        }
        if(knight.attackStatus == Attack_Status.SHOOT){
            for(Bullet bullet: knight.bullets){
                bullet.render(batch, shapeRenderer);
            }
        }
        knight.draw(batch, stateTime, shapeRenderer);
        for(Monster monster : monsters){
            monster.draw(batch, stateTime, shapeRenderer);
        }
        statusUI.draw(batch,shapeRenderer);
        batch.end();
        shapeRenderer.end();
        if(knight.currentHp <= 0){
            knight.status = Entity_Status.DEATH;
            monsters.clear();
        }
    }
    public void setPauseGame(){
        this.dispose();
//        knight.setSpeed_Cross(0);
//        knight.setSpeed_Stright(0);
//        for(Monster monster : monsters) {
//            monster.setSpeed_Cross(0);
//            monster.setSpeed_Stright(0);
//        }
        this.spaceGame.setScreen(new PauseGameScreen(this.spaceGame,this));
    }
    public void setEndGame_Screen(int point, int rank){
        this.dispose();
        this.background_Game_Music.setStop();
        this.spaceGame.setScreen(new EndGameScreen(this.spaceGame, point, rank));
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
       // batch.dispose();
       // shapeRenderer.dispose();
    }
}
