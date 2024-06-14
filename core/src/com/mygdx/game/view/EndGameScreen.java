package com.mygdx.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.SpaceGame;

public class EndGameScreen implements Screen {
    SpaceGame spaceGame;
    private OrthographicCamera camera;
    Texture background;
    Texture continueButtonIdle;
    Texture continueButtonHover;

    public EndGameScreen(SpaceGame spaceGame){
        this.spaceGame = spaceGame;
        background = new Texture("button/backgroundEndGame.png");
        continueButtonIdle = new Texture("button/continueImgHover.png");
        continueButtonHover = new Texture("button/continueImgIdle.png");
    }
    public void show(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
    }

    public void render(float var1){
        Gdx.gl.glClearColor(1, 1, 1, 0);
        ScreenUtils.clear(1, 1, 1, 0);
        int x = 320,  y = 100, h = 80, w = 200;
        spaceGame.getBatch().begin();
        spaceGame.getBatch().draw(background, 0, 0, 800, 800);

        Vector3 touchPointMusic = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPointMusic); // chuyển tỏa độ
        boolean isTouchingButtonPlay = touchPointMusic.x > x && touchPointMusic.x < x + w &&
                touchPointMusic.y > y && touchPointMusic.y < y + h;
        if (isTouchingButtonPlay) {
            spaceGame.getBatch().draw(continueButtonHover, x, y, w, h);
            if (Gdx.input.isTouched()) {
                this.dispose();
                spaceGame.setScreen(new MenuScreen(spaceGame));
            }
        }else{
            spaceGame.getBatch().draw(continueButtonIdle, x, y, w, h);
        }
        spaceGame.getBatch().end();
    }

    public void resize(int var1, int var2){

    }

    public void pause(){

    }

    public void resume(){

    }

    public void hide(){

    }

    public void dispose(){

    }
}