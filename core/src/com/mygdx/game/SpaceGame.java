package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.MenuScreen;
import com.mygdx.game.setting.Setting_MenuScreen;

public class SpaceGame extends Game {
	SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Setting_MenuScreen.generateSkin();
		this.setScreen(new MenuScreen(this));
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
