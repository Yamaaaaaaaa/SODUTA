package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.model.file.FileHandler;
import com.mygdx.game.view.EndGameScreen;
import com.mygdx.game.view.MenuScreen;
import com.mygdx.game.setting.Setting_MenuScreen;

public class SpaceGame extends Game {
	SpriteBatch batch;
	public FileHandler fileHandler;
	public ShapeRenderer shapeRenderer;
	public MenuScreen menuScreen;
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		Setting_MenuScreen.generateSkin();
		this.fileHandler = new FileHandler();
		this.menuScreen = new MenuScreen(this);
		this.setScreen(this.menuScreen);
	}
	public SpriteBatch getBatch() {
		return batch;
	}
	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
