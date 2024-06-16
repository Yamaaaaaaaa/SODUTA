package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.SpaceGame;
import com.mygdx.game.model.entity.Knight;
import com.mygdx.game.model.entity.Monster;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class PauseGameScreen implements Screen {
    // Trạng thái hiện tại
    SpaceGame spaceGame;
    GameScreen gameScreen;
    private OrthographicCamera camera;
    // hình ảnh
    Texture backGround;
    Texture buttonResumeHover;
    Texture buttonResumeIdle;
    Texture buttonExitHover;
    Texture buttonExitIdle;
    Texture buttonGithubIdle;
    Texture buttonGithubHover;

    public PauseGameScreen(){}
    public PauseGameScreen(SpaceGame spaceGame,GameScreen gameScreen){
        this.spaceGame = spaceGame;
        this.gameScreen = gameScreen;
        backGround = new Texture("button/PauseScreen/bgPause.png");
        buttonResumeHover = new Texture("button/PauseScreen/ResumeHover@2x.png");
        buttonResumeIdle = new Texture("button/PauseScreen/ResumeIdle@2x.png");
        buttonExitHover = new Texture("button/PauseScreen/Exit-Hover@2x.png");
        buttonExitIdle = new Texture("button/PauseScreen/Exit-Idle@2x.png");
        buttonGithubIdle = new Texture("button/inforGame/iconGithubIdle.png");
        buttonGithubHover = new Texture("button/inforGame/iconGithubHover.png");
    }
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        ScreenUtils.clear(1, 1, 1, 0);

        spaceGame.getBatch().begin();
        //
        spaceGame.getBatch().draw(backGround,0,0,800,800);
        // open local repo github
        int xGit = 220 + 88 + 40 + 88 + 40;
        int yGit = 425;
        Vector3 touchPointGit = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPointGit); // Chuyển đổi tọa độ

        boolean isTouchingButtonGit = touchPointGit.x > xGit && touchPointGit.x < xGit + 88 &&
                touchPointGit.y > yGit && touchPointGit.y < yGit + 88;

        if (isTouchingButtonGit) {
            spaceGame.getBatch().draw(buttonGithubHover, xGit, yGit, 88, 88);
            if (Gdx.input.isTouched()) {
                this.dispose();
                openlink("https://github.com/Yamaaaaaaaa/Group5_BTCK_PGC-Endless_Way.git");
            }

        } else {
            spaceGame.getBatch().draw(buttonGithubIdle, xGit, yGit, 88, 88);

        }
        // Button resume
        int xInfor = 220; // tọa độ x của nút infor
        int yInfor = 425; // tọa độ y của nút infor

        Vector3 touchPointInfor = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPointInfor); // Chuyển đổi tọa độ

        boolean isTouchingButtonInfor = touchPointInfor.x > xInfor && touchPointInfor.x < xInfor + 88 &&
                touchPointInfor.y > yInfor && touchPointInfor.y < yInfor + 88;

        if (isTouchingButtonInfor) {
            spaceGame.getBatch().draw(buttonResumeHover, xInfor, yInfor, 88, 88);
            if (Gdx.input.justTouched()) {
                this.dispose();
                this.gameScreen.isPaused = false;
//                clickButtonMusic.pause();
//                backgroundMusic.pause();
                spaceGame.setScreen(gameScreen);
            }
//            if(!checkSoundButtonInforOn && checkSoundOn){
//                checkSoundButtonInforOn = true;
//                clickButtonMusic.play();
//            }

        } else {
            spaceGame.getBatch().draw(buttonResumeIdle, xInfor, yInfor, 88, 88);
            //checkSoundButtonInforOn = false;
        }

        // Button exit
        int x = 220 + 88 + 40; // tọa độ x
        int y = 425; // tọa độ y

        Vector3 touchPointExit = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPointExit); // Chuyển đổi tọa độ

        boolean isTouchingButtonExit = touchPointInfor.x > x && touchPointInfor.x < x + 88 &&
                touchPointInfor.y > y && touchPointInfor.y < y + 88;

        if (isTouchingButtonExit) {
            spaceGame.getBatch().draw(buttonExitHover, x, y, 88, 88);
            if (Gdx.input.justTouched()) {
                this.dispose();
//                clickButtonMusic.pause();
//                backgroundMusic.pause();
                gameScreen.background_Game_Music.setStop();
                spaceGame.setScreen(this.spaceGame.menuScreen);
            }
//            if(!checkSoundButtonInforOn && checkSoundOn){
//                checkSoundButtonInforOn = true;
//                clickButtonMusic.play();
//            }

        } else {
            spaceGame.getBatch().draw(buttonExitIdle, x, y, 88, 88);
            //checkSoundButtonInforOn = false;
        }
        spaceGame.getBatch().end();
    }

    @Override
    public void resize(int i, int i1) {

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
    public void openlink(String path){
        try{
            if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
                Desktop.getDesktop().browse(new URI(path));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
}
