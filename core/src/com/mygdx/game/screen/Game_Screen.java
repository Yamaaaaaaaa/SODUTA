package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.SpaceGame;
import com.mygdx.game.entity.Knight;
import com.mygdx.game.setting.Setting_Knight;

public class Game_Screen implements Screen{
    private SpaceGame game;
    private Setting_Knight settingKnight = new Setting_Knight();

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Knight knight;
    public float stateTime;

    public Game_Screen(SpaceGame game){
        this.game = game;
    }
    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("basic/map/Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        knight = new Knight(new Sprite(new TextureRegion(new Texture("basic/character/Walk.png"))), (TiledMapTileLayer) map.getLayers().get(1), this.settingKnight);
        knight.setPosition(15 * knight.getCollisionLayer().getTileWidth(), 15 * knight.getCollisionLayer().getTileHeight());
        camera.zoom = .8f;

        Gdx.input.setInputProcessor(knight);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(knight.getX() + knight.getWidth() / 2 + 100, knight.getY() + knight.getHeight() / 2 + 100, 0);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        stateTime+=delta;
        renderer.getBatch().begin();
        knight.drawAnimation(renderer.getBatch(),stateTime, knight.getX()+ knight.getWidth() / 2 + 100 ,knight.getY()+ knight.getHeight() / 2 + 100 );
        renderer.getBatch().end();
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
        map.dispose();
        renderer.dispose();
    }
}
