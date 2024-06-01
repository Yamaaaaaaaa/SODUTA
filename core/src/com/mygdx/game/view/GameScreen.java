package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private ShapeRenderer shapeRenderer;
    public MusicHandler musicHandler;
    private float stateTime = 0;
    private float tile_Size = 32;

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

// MUSIC:
    public MusicGame background_Game_Music, zombie_WaveStart_Music;
    public GameScreen(SpaceGame spaceGame) {
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
    }


    @Override
    public void show() {
        loader = new TmxMapLoader();
        map = loader.load("basic/map/Medium_Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
   //     camera.zoom = .8f;

        this.collsionLayer = (TiledMapTileLayer) map.getLayers().get(1);
       // System.out.println(collsionLayer.getName());
        this.speed = 250;
        this.knight = new Knight(this,tile_Size * 13,tile_Size * 80, this.speed, collsionLayer);
        monsters = new ArrayList<Monster>();
        Monster monster = new Monster(collsionLayer, this,"vertical");
        monsters.add(monster);
        timeGenBabyMonster = (Long)TimeUtils.nanoTime();
        this.statusUI = new Status_UI(this);
    }

    float cnt = 0;
    public static boolean isPaused = false;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.113f, 0.102f, 0.16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
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
            //monsters.size() < 3
            if (TimeUtils.nanoTime() - timeGenBabyMonster >= 2000000000 && knight.currentHp > 0) {
                Monster monster = new Monster(collsionLayer, this, "vertical");
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
        knight.setSpeed_Cross(0);
        knight.setSpeed_Stright(0);
        for(Monster monster : monsters) {
            monster.setSpeed_Cross(0);
            monster.setSpeed_Stright(0);
        }
        this.spaceGame.setScreen(new PauseGameScreen(this.spaceGame,this,this.knight,this.monsters));
    }
    public void setEndGame_Screen(){
        this.dispose();
        this.background_Game_Music.setStop();
        this.spaceGame.setScreen(new EndGameScreen(this.spaceGame));
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
