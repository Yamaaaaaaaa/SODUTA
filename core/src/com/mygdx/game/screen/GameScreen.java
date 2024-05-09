package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.SpaceGame;
import com.mygdx.game.entity.model.Knight;
import com.mygdx.game.entity.model.Monster;

public class GameScreen implements Screen {
    private SpaceGame spaceGame;
    private SpriteBatch batch;
    private float stateTime;
    private float tile_Size = 32;

//NHÂN VẬT:
    // DI CHUYEN NHAN VAT
    public float speed;
    private Knight knight;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    public int roll;
    // VA CHAM
    private TiledMapTileLayer collsionLayer;
    private TmxMapLoader loader;
    private TiledMap map;
// QUÁI VẬT:
    private Monster monster;
    public GameScreen(SpaceGame spaceGame) {
        this.spaceGame = spaceGame;
        batch = spaceGame.getBatch();
    }

    @Override
    public void show() {
        loader = new TmxMapLoader();
        map = loader.load("basic/map/Mini_Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
   //     camera.zoom = .8f;

        this.collsionLayer = (TiledMapTileLayer) map.getLayers().get(1);
        System.out.println(collsionLayer.getName());
        this.speed = 250;
        this.knight = new Knight(tile_Size * 3,tile_Size * 3, this.speed, collsionLayer);
        this.monster = new Monster(tile_Size * 27, tile_Size * 27, this.speed, collsionLayer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.113f, 0.102f, 0.16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += delta;
        camera.position.x = knight.getX();
        camera.position.y = knight.getY();
        camera.update();

        renderer.setView(camera);
        renderer.render();

        knight.update();
        monster.update();


        batch.begin();
        monster.draw(batch, stateTime);
        knight.draw(batch, stateTime);


        batch.end();
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

    }
}
