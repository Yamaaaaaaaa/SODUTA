package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.model.entity.Attack_Status;

public class Status_UI {
    private BitmapFont font, font2;
    // Bao gom: Vu khi, HP, So Luong dan
    GameScreen gameScreen;

    Texture[] background_Equipment;
    Texture selector;
    int background_Equipment_Size;
    int index_Equipment;
    int[] screen_Equipment_X, screen_Equipment_Y;

    Texture background_BulletCounter;
    int background_BulletCounter_Size_Width, background_BulletCounter_Size_Height;
    int screen_BulletCounter_X, screen_BulletCounter_Y;
    int bulletCounter, bulletMax;

    // HP:
    Texture hpBar;
    int hp, hpMax;
    int screen_HPBar_X, screen_HPBar_Y;
    int HPBar_size_Width, HPBar_size_Height;

    // Point - Counter:
    Texture background_PointCounter;
    int point;
    int screen_PointCounter_X, screen_PointCounter_Y;
    int screen_PointCounter_Width, screen_PointCounter_Height;


    public Status_UI(GameScreen gameScreen){
        this.gameScreen = gameScreen;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 33;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);

        parameter.size = 15;
        parameter.color = Color.WHITE;
        font2 = generator.generateFont(parameter);
        // equipment

        this.background_Equipment = new Texture[4];
        this.background_Equipment[0] = new Texture("basic/statusbar/equipment/handgun.png");
        this.background_Equipment[1] = new Texture("basic/statusbar/equipment/knife.png");
        this.background_Equipment[2] = new Texture("basic/statusbar/equipment/rifleAmmo.png");
        this.background_Equipment[3] = new Texture("basic/statusbar/equipment/medkit.png");
        this.selector = new Texture("basic/statusbar/equipment/selector.png");
        this.background_Equipment_Size = 46;
        this.screen_Equipment_X = new int[4]; this.screen_Equipment_X[0] = 570;
        this.screen_Equipment_Y = new int[4]; this.screen_Equipment_Y[0] = 100;
        this.index_Equipment = 0;
        for(int i = 1; i < 4; i++){
            screen_Equipment_X[i] = screen_Equipment_X[i - 1] + this.background_Equipment_Size + 10;
            screen_Equipment_Y[i] = screen_Equipment_Y[i - 1];
        }
            // selector sẽ ở vị trí cùng X, Y -= 10 với equipment.

        //bulletcounter
        this.background_BulletCounter = new Texture("basic/statusbar/counterBullet/background-large.png");
        this.background_BulletCounter_Size_Height = 250;
        this.background_BulletCounter_Size_Width = 50;
        this.screen_BulletCounter_X = 570;
        this.screen_BulletCounter_Y = 30;
        this.bulletCounter = gameScreen.knight.bulletCounter;
        this.bulletMax = gameScreen.knight.bulletMax;
        


        // HP bar.
        this.hpBar = new Texture("basic/statusbar/healthbar/greenbar (2).png");
        this.screen_HPBar_Y = 15;
        this.screen_HPBar_X = 570;

        this.hp = gameScreen.knight.currentHp;
        this.hpMax = gameScreen.knight.maxHP;
        this.HPBar_size_Width = 214;
        this.HPBar_size_Height = 10;

        // Point - Counter:
        this.background_PointCounter = new Texture("basic/statusbar/equipment/background.png");
        this.screen_PointCounter_X = this.screen_PointCounter_Y = 740;
        this.screen_PointCounter_Width = this.screen_PointCounter_Height = 46;
    }
    public void update(){
        if(gameScreen.knight.attackStatus == Attack_Status.STAB){
            index_Equipment = 1;
        }
        else if(gameScreen.knight.attackStatus == Attack_Status.SHOOT){
            index_Equipment = 0;
        }
        this.bulletCounter = gameScreen.knight.bulletCounter;
        this.hp = gameScreen.knight.currentHp;
        this.point = this.gameScreen.knight.point_Counter;
    }
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer){
        for(int i = 0 ;i < 4; i++){
            batch.draw(background_Equipment[i], screen_Equipment_X[i], screen_Equipment_Y[i]);
            if(index_Equipment == i){
                batch.draw(selector, screen_Equipment_X[i], screen_Equipment_Y[i] - 10);
            }
        }


        batch.draw(background_BulletCounter, screen_BulletCounter_X, screen_BulletCounter_Y);
        this.font.draw(batch, this.gameScreen.knight.bulletCounter + " / " + this.gameScreen.knight.bulletMax, screen_BulletCounter_X + 50, screen_BulletCounter_Y + 40);

        this.font2.draw(batch, "x" +this.gameScreen.knight.counter_ItemBullet, 718, 110);
        this.font2.draw(batch, "x" +this.gameScreen.knight.counter_MedKit, 774, 110);

        int hpBarWidth = this.HPBar_size_Width * this.hp / this.hpMax;
        System.out.println(hpBarWidth);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(screen_HPBar_X, screen_HPBar_Y, hpBarWidth, this.HPBar_size_Height);


        batch.draw(this.background_PointCounter, screen_PointCounter_X, screen_PointCounter_Y);
        this.font.draw(batch, point + "", screen_PointCounter_X + 10, screen_PointCounter_Y + 30);
    }
}
