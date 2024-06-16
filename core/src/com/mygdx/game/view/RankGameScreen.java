package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.SpaceGame;

public class RankGameScreen implements Screen {
    private final FreeTypeFontGenerator generator;
    private final FreeTypeFontGenerator.FreeTypeFontParameter parameter;
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
    Texture rankTable;
    Texture buttonHomeIdle;
    Texture buttonHomeHover;

    // CHỨC NĂNG CHECK ÂM THANH
    boolean checkSoundButtonMusicOffOn = false;
    boolean isCheckSoundButtonHomeOn = false;
    static boolean checkSoundOn = true;
    private BitmapFont font;
    public RankGameScreen(SpaceGame spaceGame){
        this.spaceGame = spaceGame;
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Menu_Music/halloween-happy-background-168842.mp3"));
        clickButtonMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Menu_Music/clickButton.mp3"));
        background = new Texture("button/Background.png");
        buttonMusicOnIdle = new Texture("button/Music-On-Idle.png");
        buttonMusicOnHover = new Texture("button/Music-On-Hover.png");
        buttonMusicOffIdle = new Texture("button/Music-Off-Idle.png");
        buttonMusicOffHover = new Texture("button/Music-Off-Hover.png");
        rankTable = new Texture("button/inforGame/RankTable.png");
        buttonHomeHover = new Texture("button/inforGame/Home-Hover.png");
        buttonHomeIdle = new Texture("button/inforGame/Home-Idle.png");


        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SegoeUI-Black.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
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


        // Button go back
        int xHome = 350 ; // tọa độ x của nút home
        int yHome = 0; // tọa độ y của nút home

        Vector3 touchPointHome = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPointHome); // Chuyển đổi tọa độ

        boolean isTouchingButtonPlay = touchPointHome.x > xHome && touchPointHome.x < xHome + SIZE_BUTTON_WIDTH &&
                touchPointHome.y > yHome && touchPointHome.y < yHome + SIZE_BUTTON_HEIGHT;

        if (isTouchingButtonPlay) {
            spaceGame.getBatch().draw(buttonHomeHover, xHome, yHome, SIZE_BUTTON_WIDTH, SIZE_BUTTON_HEIGHT);
            if (Gdx.input.justTouched()) {
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
        // Rank table
        spaceGame.getBatch().draw(rankTable,30,100,750,700);
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

                if (checkSoundOn) {

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

        //Draw Ranking:
        showRanking();

        spaceGame.getBatch().end();
    }


    public int ranking_Columns1_X = 210, ranking_Columns2_X = 300, ranking_Columns3_X = 550;
    public int ranking_Row_Y = 570;
    public void showRanking(){
        this.parameter.size = 30;
        this.parameter.color = Color.BLACK;
        this.font = generator.generateFont(parameter);
        this.font.draw(spaceGame.getBatch(),"Top", ranking_Columns1_X, ranking_Row_Y);
        this.font.draw(spaceGame.getBatch(),"Name", ranking_Columns2_X, ranking_Row_Y);
        this.font.draw(spaceGame.getBatch(),"Point", ranking_Columns3_X, ranking_Row_Y);

        this.parameter.size = 20;
        this.parameter.color = Color.BLACK;
        this.font = generator.generateFont(parameter);

        if(this.spaceGame.fileHandler.List_Ranking.size() < 7){
            for(int i = 0 ; i < this.spaceGame.fileHandler.List_Ranking.size(); i++){
                int row_y = ranking_Row_Y - (i + 1) * 30 - 20;
                int col1_y = ranking_Columns1_X;
                int col2_y = ranking_Columns2_X;
                int col3_y = ranking_Columns3_X;
                this.font.draw(spaceGame.getBatch(),(i + 1) + "", col1_y, row_y);
                this.font.draw(spaceGame.getBatch(),this.spaceGame.fileHandler.List_Ranking.get(i).getName() + "", col2_y, row_y);
                this.font.draw(spaceGame.getBatch(),this.spaceGame.fileHandler.List_Ranking.get(i).getPoint() + "", col3_y, row_y);
            }
        }
        else{
            for(int i = 0 ; i < 7; i++){
                int row_y = ranking_Row_Y - (i + 1) * 30;
                int col1_y = ranking_Columns1_X;
                int col2_y = ranking_Columns2_X;
                int col3_y = ranking_Columns3_X;
                this.font.draw(spaceGame.getBatch(),(i + 1) + "", col1_y, row_y);
                this.font.draw(spaceGame.getBatch(),this.spaceGame.fileHandler.List_Ranking.get(i).getName() + "", col2_y, row_y);
                this.font.draw(spaceGame.getBatch(),this.spaceGame.fileHandler.List_Ranking.get(i).getPoint() + "", col3_y, row_y);
            }
        }
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
