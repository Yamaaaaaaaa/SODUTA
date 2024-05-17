package com.mygdx.game.controller;

import com.mygdx.game.model.Entity;
import com.mygdx.game.view.GameScreen;

public interface Movement {
    void move(Entity entity, GameScreen gameScreen);
}
