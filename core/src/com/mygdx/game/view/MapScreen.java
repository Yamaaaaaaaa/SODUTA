package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.SpaceGame;
import com.mygdx.game.model.gamemusic.MusicGame;

public class MapScreen implements Screen {
    // KÍCH THƯỚC MAP
    private int SIZE_BUTTON_WIDTH = 88;
    private int SIZE_BUTTON_HEIGHT = 88;
    private int SIZE_BUTTON_MUSIC_WIDTH = SIZE_BUTTON_WIDTH/2;
    private int SIZE_BUTTON_MUSIC_HEIGHT = SIZE_BUTTON_HEIGHT/2;
    private int SIZE_MAP_1_WIDTH = 300;
    private int SIZE_MAP_1_HEIGHT = 215;
    private int SIZE_MAP_2_WIDTH = 300;
    private int SIZE_MAP_2_HEIGHT = 208;

    // CÁC CHỨC NĂNG KHÁC
    SpaceGame spaceGame;
    private OrthographicCamera camera;

    // ÂM THANH
    private MusicGame backgroundMusic;
    private MusicGame clickButtonMusic;

    // HÌNH ẢNH
    Texture background;
    Texture buttonMusicOnIdle;
    Texture buttonMusicOnHover;
    Texture buttonMusicOffIdle;
    Texture buttonMusicOffHover;
    Texture buttonMap1Idle;
    Texture buttonMap1Hover;
    Texture buttonMap2Idle;
    Texture buttonMap2Hover;
    Texture buttonHomeIdle;
    Texture buttonHomeHover;

    // CHỨC NĂNG CHECK ÂM THANH
    boolean checkSoundButtonMusicOffOn = false;
    boolean isCheckSoundButtonMap1On = false;
    boolean isCheckSoundButtonMap2On = false;
    boolean isCheckSoundButtonHomeOn = false;
    static boolean checkSoundOn = true;

    public MapScreen(){}
    public MapScreen(SpaceGame spaceGame){
        this.spaceGame = spaceGame;
        backgroundMusic = new MusicGame("music/Menu_Music/halloween-happy-background-168842.mp3", true);
        clickButtonMusic = new MusicGame("music/Menu_Music/clickButton.mp3", false);
        background = new Texture("button/MapScreen/BG.png");
        buttonMusicOnIdle = new Texture("button/Music-On-Idle.png");
        buttonMusicOnHover = new Texture("button/Music-On-Hover.png");
        buttonMusicOffIdle = new Texture("button/Music-Off-Idle.png");
        buttonMusicOffHover = new Texture("button/Music-Off-Hover.png");

        buttonMap1Hover = new Texture("button/MapScreen/map1Hover.png");
        buttonMap1Idle = new Texture("button/MapScreen/map1Idle.png");
        buttonMap2Hover = new Texture("button/MapScreen/map2Hover.png");
        buttonMap2Idle = new Texture("button/MapScreen/map2Idle.png");

        buttonHomeHover = new Texture("button/inforGame/Home-Hover.png");
        buttonHomeIdle = new Texture("button/inforGame/Home-Idle.png");
    }
    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        if(checkSoundOn) backgroundMusic.setPlay();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        ScreenUtils.clear(1, 1, 1, 0);

        spaceGame.getBatch().begin();
        spaceGame.getBatch().draw(background, 0, 0, 800, 800);
        // Button go back
        int xHome = 340 ; // tọa độ x của nút home
        int yHome = 25; // tọa độ y của nút home

        Vector3 touchPointHome = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPointHome); // Chuyển đổi tọa độ

        boolean isTouchingButtonHome = touchPointHome.x > xHome && touchPointHome.x < xHome + SIZE_BUTTON_WIDTH &&
                touchPointHome.y > yHome && touchPointHome.y < yHome + SIZE_BUTTON_HEIGHT;

        if (isTouchingButtonHome) {
            spaceGame.getBatch().draw(buttonHomeHover, xHome, yHome, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                backgroundMusic.setPause();
                clickButtonMusic.setPause();
                spaceGame.setScreen(new MenuScreen(spaceGame));
            }
            if(!isCheckSoundButtonHomeOn && checkSoundOn){
                isCheckSoundButtonHomeOn = true;
                clickButtonMusic.setPlay();
            }

        } else {
            spaceGame.getBatch().draw(buttonHomeIdle, xHome, yHome, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            isCheckSoundButtonHomeOn = false;
        }
        // Text
        spaceGame.getBatch().draw(new Texture("button/MapScreen/TextMap2.png"),450,340,128*2,32*2);
        spaceGame.getBatch().draw(new Texture("button/MapScreen/TextMap1.png"),70,340,128*2,32*2);
        spaceGame.getBatch().draw(new Texture("button/MapScreen/Text.png"),200,200,128*3,32*3 );
        // button Map1
        int x1 = 50 ; // tọa độ x
        int y1 = 400; // tọa độ y

        Vector3 touchPoint1 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPoint1); // Chuyển đổi tọa độ

        boolean isTouchingButtonPlay = touchPoint1.x > x1 && touchPoint1.x < x1 + SIZE_MAP_1_WIDTH &&
                touchPoint1.y > y1 && touchPoint1.y < y1 + SIZE_MAP_1_HEIGHT;

        if (isTouchingButtonPlay) {
            spaceGame.getBatch().draw(buttonMap1Hover, x1, y1, SIZE_MAP_1_WIDTH, SIZE_MAP_1_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                backgroundMusic.setPause();
                clickButtonMusic.setPause();
                String mapPath1 = "basic/map1/Medium_Map.tmx";
                spaceGame.setScreen(new GameScreen(spaceGame, mapPath1,1));
            }
            if(!isCheckSoundButtonMap1On && checkSoundOn){
                isCheckSoundButtonMap1On = true;
                clickButtonMusic.setPlay();
            }

        } else {
            spaceGame.getBatch().draw(buttonMap1Idle, x1, y1, SIZE_MAP_1_WIDTH, SIZE_MAP_1_HEIGHT);
            isCheckSoundButtonMap1On = false;
        }

        // button Map2
        int x2 = 75 + SIZE_MAP_1_WIDTH + 50 ; // tọa độ x
        int y2 = 400; // tọa độ y

        Vector3 touchPoint2 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPoint2); // Chuyển đổi tọa độ

        boolean isTouchingButtonPlay2 = touchPoint1.x > x2 && touchPoint1.x < x2 + SIZE_MAP_2_WIDTH &&
                touchPoint1.y > y2 && touchPoint1.y < y2 + SIZE_MAP_2_HEIGHT;

        if (isTouchingButtonPlay2) {
            spaceGame.getBatch().draw(buttonMap2Hover, x2, y2, SIZE_MAP_2_WIDTH, SIZE_MAP_2_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                backgroundMusic.setPause();
                clickButtonMusic.setPlay();
                String mapPath2 = "basic/map2/mediumMap.tmx";
                spaceGame.setScreen(new GameScreen(spaceGame, mapPath2,2));
            }
            if(!isCheckSoundButtonMap2On && checkSoundOn){
                isCheckSoundButtonMap2On = true;
                clickButtonMusic.setPlay();
            }

        } else {
            spaceGame.getBatch().draw(buttonMap2Idle, x2, y2, SIZE_MAP_2_WIDTH, SIZE_MAP_2_HEIGHT);
            isCheckSoundButtonMap2On = false;
        }
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
            if (Gdx.input.isTouched()) {
                this.dispose();

                if(checkSoundOn) {

                    //spaceGame.getBatch().draw(buttonMusicOffHover, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
                    checkSoundOn = false;
                    backgroundMusic.setPause();
                }
                else {
                    //spaceGame.getBatch().draw(buttonMusicOnHover, x, y, SIZE_BUTTON_MUSIC_WIDTH, SIZE_BUTTON_MUSIC_HEIGHT);
                    backgroundMusic.setPlay();
                    checkSoundOn = true;
                }
            }
            if(checkSoundOn){
                if(!checkSoundButtonMusicOffOn){
                    clickButtonMusic.setPlay();
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
}
