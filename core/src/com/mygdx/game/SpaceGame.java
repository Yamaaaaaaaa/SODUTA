package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.view.MenuScreen;

public class SpaceGame extends Game {
	SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

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
