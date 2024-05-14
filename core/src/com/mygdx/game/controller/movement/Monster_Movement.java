package com.mygdx.game.controller.movement;

import com.mygdx.game.controller.Movement;
import com.mygdx.game.model.Entity;

public class Monster_Movement implements Movement {
    private static Monster_Movement instance;
    private Monster_Movement(){}
    public static Monster_Movement getInstance(){
        if(instance==null){
            instance = new Monster_Movement();
        }
        return instance;
    }
    @Override
    public void move(Entity entity) {

    }
}
