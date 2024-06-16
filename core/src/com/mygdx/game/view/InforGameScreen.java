package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.SpaceGame;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class InforGameScreen implements Screen {
    // KÍCH THƯỚC NÚT
    private int SIZE_BUTTON_WIDTH = 88;
    private int SIZE_BUTTON_HEIGHT = 88;
    private int SIZE_BUTTON_MUSIC_WIDTH = SIZE_BUTTON_WIDTH/2;
    private int SIZE_BUTTON_MUSIC_HEIGHT = SIZE_BUTTON_HEIGHT/2;

    // CÁC CHỨC NĂNG KHÁC
    SpaceGame spaceGame;
    private OrthographicCamera camera;

    // ÂM THANH
    private Music backgroundMusic;
    private Music clickButtonMusic;

    // HÌNH ẢNH
    Texture background;
    Texture buttonMusicOnIdle;
    Texture buttonMusicOnHover;
    Texture buttonMusicOffIdle;
    Texture buttonMusicOffHover;
    Texture howToPlay;
    Texture buttonHomeIdle;
    Texture buttonHomeHover;
    Texture buttonGithubIdle;
    Texture buttonGithubHover;
    // CHỨC NĂNG CHECK ÂM THANH
    boolean checkSoundButtonMusicOffOn = false;
    boolean isCheckSoundButtonHomeOn = false;
    boolean isCheckSoundButtonGithubOn = false;
    static boolean checkSoundOn = true;

    public InforGameScreen(){}
    public InforGameScreen(SpaceGame spaceGame){
        this.spaceGame = spaceGame;

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Menu_Music/halloween-happy-background-168842.mp3"));
        backgroundMusic.setLooping(true);
        clickButtonMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Menu_Music/clickButton.mp3"));
        clickButtonMusic.setLooping(false);

        background = new Texture("button/Background.png");
        buttonMusicOnIdle = new Texture("button/Music-On-Idle.png");
        buttonMusicOnHover = new Texture("button/Music-On-Hover.png");
        buttonMusicOffIdle = new Texture("button/Music-Off-Idle.png");
        buttonMusicOffHover = new Texture("button/Music-Off-Hover.png");
        howToPlay = new Texture("button/inforGame/HowToPlayGame.png");
        buttonHomeHover = new Texture("button/inforGame/Home-Hover.png");
        buttonHomeIdle = new Texture("button/inforGame/Home-Idle.png");
        buttonGithubIdle = new Texture("button/inforGame/iconGithubIdle.png");
        buttonGithubHover = new Texture("button/inforGame/iconGithubHover.png");
    }
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        if(checkSoundOn) backgroundMusic.play();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        ScreenUtils.clear(1, 1, 1, 0);

        spaceGame.getBatch().begin();
        spaceGame.getBatch().draw(background, 0, 0, 800, 800);
        // icon Github
        int xGit = 300 + SIZE_BUTTON_WIDTH + 40;
        int yGit = 0;
        Vector3 touchPointGit = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPointGit); // Chuyển đổi tọa độ

        boolean isTouchingButtonGit = touchPointGit.x > xGit && touchPointGit.x < xGit + SIZE_BUTTON_WIDTH &&
                touchPointGit.y > yGit && touchPointGit.y < yGit + SIZE_BUTTON_HEIGHT;

        if (isTouchingButtonGit) {
            spaceGame.getBatch().draw(buttonGithubHover, xGit, yGit, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                openlink("https://github.com/Yamaaaaaaaa/Group5_BTCK_PGC-Endless_Way.git");
            }
            if(!isCheckSoundButtonGithubOn && checkSoundOn){
                isCheckSoundButtonGithubOn = true;
                clickButtonMusic.play();
            }

        } else {
            spaceGame.getBatch().draw(buttonGithubIdle, xGit, yGit, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            isCheckSoundButtonGithubOn = false;
        }
        // Button go back
        int xHome = 300 ; // tọa độ x của nút home
        int yHome = 0; // tọa độ y của nút home

        Vector3 touchPointHome = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPointHome); // Chuyển đổi tọa độ

        boolean isTouchingButtonPlay = touchPointHome.x > xHome && touchPointHome.x < xHome + SIZE_BUTTON_WIDTH &&
                touchPointHome.y > yHome && touchPointHome.y < yHome + SIZE_BUTTON_HEIGHT;

        if (isTouchingButtonPlay) {
            spaceGame.getBatch().draw(buttonHomeHover, xHome, yHome, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                backgroundMusic.pause();
                clickButtonMusic.pause();
                spaceGame.setScreen(new MenuScreen(spaceGame));
            }
            if(!isCheckSoundButtonHomeOn && checkSoundOn){
                isCheckSoundButtonHomeOn = true;
                clickButtonMusic.play();
            }

        } else {
            spaceGame.getBatch().draw(buttonHomeIdle, xHome, yHome, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            isCheckSoundButtonHomeOn = false;
        }
        // how to play
        spaceGame.getBatch().draw(howToPlay,125,100,550,600);
        // button music
        int x = 0;
        int y = 0;

        Vector3 touchPointMusic = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPointMusic); // chuyển tỏa độ
        boolean isTouchingButtonMusic = touchPointMusic.x > x && touchPointMusic.x < x + SIZE_BUTTON_MUSIC_WIDTH &&
                touchPointMusic.y > y && touchPointMusic.y < y + SIZE_BUTTON_MUSIC_HEIGHT;
        if (isTouchingButtonMusic) {
            if(checkSoundOn) spaceGame.getBatch().draw(buttonMusicOnHover, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
            else spaceGame.getBatch().draw(buttonMusicOffHover, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
            if (Gdx.input.justTouched()) {
                this.dispose();

                if(checkSoundOn) {

                    //spaceGame.getBatch().draw(buttonMusicOffHover, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
                    checkSoundOn = false;
                    backgroundMusic.pause();
                }
                else {
                    //spaceGame.getBatch().draw(buttonMusicOnHover, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
                    backgroundMusic.play();
                    checkSoundOn = true;
                }
            }
            if(checkSoundOn){
                if(!checkSoundButtonMusicOffOn){
                    clickButtonMusic.play();
                    checkSoundButtonMusicOffOn = true;
                }
            }

        } else {
            if(checkSoundOn) spaceGame.getBatch().draw(buttonMusicOnIdle, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
            else spaceGame.getBatch().draw(buttonMusicOffIdle, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
            checkSoundButtonMusicOffOn = false;


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
