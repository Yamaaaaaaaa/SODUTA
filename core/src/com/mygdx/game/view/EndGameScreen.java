package com.mygdx.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.SpaceGame;

public class EndGameScreen implements Screen {
    private ShapeRenderer shapeRenderer;
    SpaceGame spaceGame;
    private OrthographicCamera camera;
    Texture background, backPoint;
    Sprite backPointBG;
    Texture continueButtonIdle;
    Texture continueButtonHover;
    BitmapFont font1, font2, font3, font4;
    int point, rank;
    public EndGameScreen(SpaceGame spaceGame, int point, int rank){
        this.spaceGame = spaceGame;
        background = new Texture("button/backgroundEndGame.png");
        continueButtonIdle = new Texture("button/continueImgHover.png");
        continueButtonHover = new Texture("button/continueImgIdle.png");
        this.backPoint = new Texture("basic/hinh-nen-den-hoa-tiet-vai_021355204.png");
        this.backPointBG = new Sprite(this.backPoint);
        this.backPointBG.setAlpha(0.8f);

        shapeRenderer = spaceGame.shapeRenderer;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        parameter.color = Color.WHITE;
        font1 = generator.generateFont(parameter);

        parameter.size = 25;
        parameter.color = Color.WHITE;
        font2 = generator.generateFont(parameter);

        parameter.size = 20;
        parameter.color = Color.WHITE;
        font3 = generator.generateFont(parameter);

        parameter.size = 120;
        parameter.color = Color.WHITE;
        font4 = generator.generateFont(parameter);
        this.point = point;
        this.rank = rank;
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

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        spaceGame.getBatch().begin();

        //BackGround
        spaceGame.getBatch().draw(background, 0, 0, 800, 800);
        this.backPointBG.setPosition(150,200);
        this.backPointBG.draw(spaceGame.getBatch());

        this.font1.draw(spaceGame.getBatch(), "DEFEAT !!!", 290, 650);

        //Thông tin
        if(rank > 0){
            this.font3.draw(spaceGame.getBatch(), "NEW RANKING RECORD", 300, 570);
            //this.font4.draw(spaceGame.getBatch(), "#" + this.rank, 400, 450);
        }
        else{
        //    this.font4.draw(spaceGame.getBatch(), "GÀ", 400, 450);
        }

        // VẼ NHÂN VẬT THEO MẪU SAU: VỊ TRÍ THÌ THAY ĐÔI CHO PHÙ HỢP
        //spaceGame.getBatch().draw(background, 0, 0, 800, 800);
        spaceGame.getBatch().draw(new Texture("button/character.png"),400,300,96*2 + 32,96*2 + 32);
        this.font2.draw(spaceGame.getBatch(), "Your Point: " + this.point, 200, 500);
        this.font2.draw(spaceGame.getBatch(), "Time: " + "...", 200, 450);
        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Menu_Music/halloween-comedy-121626.mp3"));
        backgroundMusic.play();
        //Bảng Rank

      //  shapeRenderer.line(250, 500, 700, 500);

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
        shapeRenderer.end();
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