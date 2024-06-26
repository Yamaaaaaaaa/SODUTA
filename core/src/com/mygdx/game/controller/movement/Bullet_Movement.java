package com.mygdx.game.controller.movement;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.controller.CheckCollision;
import com.mygdx.game.controller.Direction;
import com.mygdx.game.controller.Movement;
import com.mygdx.game.model.entity.Entity;
import com.mygdx.game.view.GameScreen;

public class Bullet_Movement implements Movement {
    private static Bullet_Movement instance;
    private Bullet_Movement(){}
    public static Bullet_Movement getInstance(){
        if(instance==null){
            instance = new Bullet_Movement();
        }
        return instance;
    }
    private CheckCollision checkCollision_Bullet;
    @Override
    public void move(Entity entity, GameScreen gameScreen, CheckCollision checkCollision) {
        // di chuyển đaạn
        if(entity.direction == Direction.DOWN)
            entity.setY(entity.getY() - entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime());
        else if (entity.direction == Direction.UP)
            entity.setY(entity.getY() + entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime());
        else if (entity.direction == Direction.LEFT || entity.direction == Direction.UPLEFT || entity.direction == Direction.DOWNLEFT)
            entity.setX(entity.getX() - entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime());
        else
            entity.setX(entity.getX() + entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime());
        // check va cham map
        checkCollision_Bullet = checkCollision;
        float oldX = entity.getX(), oldY = entity.getY();
        if(checkCollision_Bullet.check_BulletWithMap(oldX, oldY)){
            entity.remove = true;
        }
    }
}
