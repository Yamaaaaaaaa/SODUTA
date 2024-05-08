package com.mygdx.game.setting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Media_Menu {
    public Texture imgMenu;

    public void loadAssets(){
        this.imgMenu = new Texture("ui/figs/menu.jpg");
    }
    public void disposeAssets(){
        this.imgMenu.dispose();
    }
}
