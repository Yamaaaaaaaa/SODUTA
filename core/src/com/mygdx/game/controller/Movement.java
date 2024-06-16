package com.mygdx.game.controller;

import com.mygdx.game.model.entity.Entity;
import com.mygdx.game.view.GameScreen;

public interface Movement {
    void move(Entity entity, GameScreen gameScreen, CheckCollision checkCollision);
}
