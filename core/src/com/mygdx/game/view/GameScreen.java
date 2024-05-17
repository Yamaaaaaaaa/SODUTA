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
import com.mygdx.game.controller.CheckCollision;
import com.mygdx.game.controller.Direction;
import com.mygdx.game.model.Attack_Status;
import com.mygdx.game.model.Bullet;
import com.mygdx.game.model.Knight;
import com.mygdx.game.model.Monster;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Iterator;


public class GameScreen implements Screen {
    private SpaceGame spaceGame;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
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
    private ArrayList<Monster> monsters ;
    private int sprites_Counting = 0;
    private int sprites_Num = 1;
    private long timeGenBabyMonster;

    private Status_UI statusUI;
    public GameScreen(SpaceGame spaceGame) {
        this.spaceGame = spaceGame;
        batch = spaceGame.getBatch();
        shapeRenderer = spaceGame.shapeRenderer;
    }

    @Override
    public void show() {
        loader = new TmxMapLoader();
        map = loader.load("basic/map/Mini_Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
   //     camera.zoom = .8f;

        this.collsionLayer = (TiledMapTileLayer) map.getLayers().get(1);
       // System.out.println(collsionLayer.getName());
        this.speed = 250;
        this.knight = new Knight(tile_Size * 3,tile_Size * 3, this.speed, collsionLayer);
        monsters = new ArrayList<>();
        Monster monster = new Monster( collsionLayer, this,"vertical");
        monsters.add(monster);
        timeGenBabyMonster = (Long)TimeUtils.nanoTime();
        this.statusUI = new Status_UI(this);
    }

    float cnt = 0;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.113f, 0.102f, 0.16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && knight.attackStatus == Attack_Status.SHOOT){
            if(knight.direction == Direction.DOWN) knight.bullets.add(new Bullet(knight.screenX + 20,knight.screenY - 20,500,knight.direction, collsionLayer));
            else if(knight.direction == Direction.UP) knight.bullets.add(new Bullet(knight.screenX + 20,knight.screenY + 50,500,knight.direction, collsionLayer));
            else if(knight.direction == Direction.RIGHT || knight.direction == Direction.DOWNRIGHT || knight.direction == Direction.UPRIGHT) knight.bullets.add(new Bullet(knight.screenX + 50,knight.screenY + 10,500,knight.direction, collsionLayer));
            else if(knight.direction == Direction.LEFT || knight.direction == Direction.DOWNLEFT || knight.direction == Direction.UPLEFT) knight.bullets.add(new Bullet(knight.screenX - 20,knight.screenY + 10,500,knight.direction, collsionLayer));
        }

        // update Bullet cho sung
        if(knight.attackStatus == Attack_Status.SHOOT){
            ArrayList<Bullet> bulletToRemove = new ArrayList<Bullet>();
            for(Bullet bullet : knight.bullets){
                bullet.update(delta);
                if(bullet.remove) bulletToRemove.add(bullet);
            }
            knight.bullets.removeAll(bulletToRemove);
        }
        camera.position.x = knight.getX();
        camera.position.y = knight.getY();
        camera.update();

        renderer.setView(camera);
        renderer.render();

        //update
        knight.update();
        //TimeUtils.nanoTime() - timeGenBabyMonster >= 2000000000
        if (monsters.size() < 3){
            Monster monster = new Monster(  collsionLayer, this,"vertical");
            monsters.add(monster);
            timeGenBabyMonster = (Long)TimeUtils.nanoTime();
        }
        statusUI.update();

        for(Monster monster : monsters){
            monster.update(knight.getX(), knight.getY());
        }
        stateTime += delta;
        // check va cham đạn và monster
        ArrayList<Monster> rejMons = new ArrayList<Monster>();
        ArrayList<Bullet> rejBullet = new ArrayList<Bullet>();
        for(Monster monster : monsters){
            for(Bullet bullet : knight.bullets){
                if(monster.getRectangle().overlaps( bullet.rectangle)){
                    rejMons.add(monster);
                    rejBullet.add(bullet);
                }
            }
        }
        //System.out.println(rejMons.size() + "  " + rejBullet.size());
        monsters.removeAll(rejMons);
        knight.bullets.removeAll(rejBullet);
        /*ArrayList<Bullet> reBullet = new ArrayList<Bullet>();
        for(Bullet bullet : knight.bullets){
            CheckCollision checkBullet = new CheckCollision(bullet);
            if(checkBullet.checkCollisionBulletWithMap()){
                reBullet.add(bullet);
            }
        }
        knight.bullets.removeAll(reBullet);*/
        //draw , shape trc, batch sau.
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        batch.begin();
        if(knight.attackStatus == Attack_Status.SHOOT){
            for(Bullet bullet: knight.bullets){
                bullet.render(batch);
            }
        }
        knight.draw(batch, stateTime, shapeRenderer);
        for(Monster monster : monsters){
            monster.draw(batch, stateTime, shapeRenderer);
        }

        statusUI.draw(batch,shapeRenderer);
        batch.end();
        shapeRenderer.end();

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
        batch.dispose();
    }


}
