package com.mygdx.game.setting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Setting_MenuScreen {
    public static int TILE_SIZE = 40;
    public static int BUTTON_WIDTH = 280;
    public static int BUTTON_HEIGHT = 60;

    public static int WINDOWS_SIZE = 800;


    // giống kiểu css trên fx :))
    public static BitmapFont font24;
    public static Skin skin;
    public static void generateSkin(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 24;
        params.color = Color.BLACK;
        font24 = generator.generateFont(params);

        skin = new Skin();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("ui/uiSkin.atlas")));
        skin.add("default-font", font24);
        skin.load(Gdx.files.internal("ui/uiSkin.json"));
    }
}
