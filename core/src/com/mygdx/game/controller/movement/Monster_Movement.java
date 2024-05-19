package com.mygdx.game.controller.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.controller.CheckCollision;
import com.mygdx.game.controller.Direction;
import com.mygdx.game.controller.Movement;
import com.mygdx.game.model.Entity;
import com.mygdx.game.view.GameScreen;

public class Monster_Movement implements Movement {
    private static Monster_Movement instance;
    private Monster_Movement(){}
    public static Monster_Movement getInstance(){
        if(instance==null){
            instance = new Monster_Movement();
        }
        return instance;
    }
    private CheckCollision checkCollision;

    @Override
    public void move(Entity entity, GameScreen gameScreen) {
        checkCollision = new CheckCollision(entity);
        movingMonster(entity, gameScreen.knight.getX(), gameScreen.knight.getY());
    }
    public void movingMonster(Entity entity, float targetX, float targetY){
        float oldX = entity.getX(), oldY = entity.getY();
        Vector2 res = new Vector2();
        Vector2 targetVector = new Vector2(targetX - entity.getX(), targetY - entity.getY());
        if(targetVector.x > 0){
            if(targetVector.y > 0){
                entity.direction = Direction.UPRIGHT;
            }
            else if(targetVector.y == 0){
                entity.direction = Direction.RIGHT;
            }
            else if(targetVector.y < 0){
                entity.direction = Direction.DOWNRIGHT;
            }
        }
        else if(targetVector.x == 0){
            if(targetVector.y > 0){
                entity.direction = Direction.UP;
            }
            else if(targetVector.y == 0){
                entity.direction = Direction.DOWN;
            }
            else if(targetVector.y < 0){
                entity.direction = Direction.DOWN;
            }
        }
        else if(targetVector.x < 0){
            if(targetVector.y > 0){
                entity.direction = Direction.UPLEFT;
            }
            else if(targetVector.y == 0){
                entity.direction = Direction.LEFT;
            }
            else if(targetVector.y < 0){
                entity.direction = Direction.DOWNLEFT;
            }
        }
        res.set(targetVector).nor().scl(entity.getSpeed_Stright());
        entity.setPosision(entity.getX() + res.x* Gdx.graphics.getDeltaTime(), entity.getY() + res.y* Gdx.graphics.getDeltaTime());
        checkCollision.check_MonsterWithMap(oldX, oldY);
    }
}
