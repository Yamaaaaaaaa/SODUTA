package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class MapScreen_Tile {
    public Sprite sprite;
    public boolean collision = false; // Va chạm, Nếu true: Vật thể này có va chạm, sẽ ko thể đi qua. và ngc lại...
    public String name;
}
