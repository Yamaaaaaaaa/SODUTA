package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.GameProcessing.Check_Collision;
import com.mygdx.game.SpaceGame;
import com.mygdx.game.entity.KeyHandler;
import com.mygdx.game.entity.Player;
import com.mygdx.game.setting.Variable;

import java.awt.*;

public class GamePlayingScreen implements Screen {

    public SpaceGame game;


    // Player:
    public Player player;


    // Map:
        // Vẽ Map:
        public String fileMap_Path;
        public int tileInCol;
        public int tileInRow;
        public MapScreen_Full mapScreenFull;
        // Kiểm tra va chạm:
        public Check_Collision checkCollision = new Check_Collision(this);

    public KeyHandler keyHandler;
    public GamePlayingScreen(SpaceGame game) {
        keyHandler = new KeyHandler();
        this.player = new Player( this, this.keyHandler);
    }
    public GamePlayingScreen(SpaceGame game, String fileMap_Path) {
        keyHandler = new KeyHandler();
        this.player = new Player( this, keyHandler);
        this.fileMap_Path = fileMap_Path;
        this.mapScreenFull = new MapScreen_Full(this);
        Gdx.input.setInputProcessor(keyHandler);
    }
    //
    public SpriteBatch batch;

    @Override
    public void show() {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        player.update();

        Gdx.gl.glClearColor(0,0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // VẼ BẢN ĐỒ, BẢN ĐỒ PHẢI DI CHUYEN THEO NHÂN VẬT
        int worldCol = 0, worldRow = 0;
        while (worldCol < this.tileInCol && worldRow < this.tileInRow) {
            int tileNum = this.mapScreenFull.mapScreen_Full_byNum[worldCol][worldRow];

            int worldX = worldCol * Variable.TILE_SIZE;
            int worldY = worldRow * Variable.TILE_SIZE;

            float screenX_map = worldX - player.worldX + player.screenX;
            float screenY_map = worldY - player.worldY + player.screenY;

            mapScreenFull.mapScreenTiles[tileNum].sprite.setSize(Variable.TILE_SIZE, Variable.TILE_SIZE);
            mapScreenFull.mapScreenTiles[tileNum].sprite.setPosition(screenX_map, screenY_map);
            mapScreenFull.mapScreenTiles[tileNum].sprite.draw(batch);
            worldCol++;
            if (worldCol == this.tileInCol) {
                worldRow++;
                worldCol = 0;
            }
        }

        // VẼ NHÂN VẬT, NHÂN VẬT ĐỨNG YÊN TẠI 1 VỊ TRÍ CỐ ĐỊNH TRONG BẢN ĐỒ.
        player.currentSprite.setPosition(player.screenX + Variable.TILE_SIZE,player.screenY + Variable.TILE_SIZE);
        player.currentSprite.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
