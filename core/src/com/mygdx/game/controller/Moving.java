package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.Attack_Status;
import com.mygdx.game.model.Entity;
import com.mygdx.game.model.Entity_Status;

public class Moving {
    private Entity entity;
    public boolean upKey, downKey, leftKey, rightKey;
    public boolean attack;
    public boolean changeWeapon;
    private CheckCollision checkCollision;

    private void move_update_Direction(){
        upKey = Gdx.input.isKeyPressed(Input.Keys.W);
        rightKey = Gdx.input.isKeyPressed(Input.Keys.D);
        leftKey = Gdx.input.isKeyPressed(Input.Keys.A);
        downKey = Gdx.input.isKeyPressed(Input.Keys.S);

        changeWeapon = Gdx.input.isKeyPressed(Input.Keys.J);

        attack = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        if(leftKey || rightKey || upKey || downKey) {
            entity.status = Entity_Status.WALKING;
            if(leftKey && !rightKey && upKey && !downKey) {
                entity.direction = Direction.UPLEFT;
            }
            else if(leftKey && !rightKey && !upKey && downKey) {
                entity.direction = Direction.DOWNLEFT;
            }
            else if(!leftKey && rightKey && upKey && !downKey) {
                entity.direction = Direction.UPRIGHT;
            }
            else if(!leftKey && rightKey && !upKey && downKey) {
                entity.direction = Direction.DOWNRIGHT;
            }
            else if(leftKey) {
                entity.direction = Direction.LEFT;
            }
            else if(rightKey) {
                entity.direction = Direction.RIGHT;
            }
            else if(upKey) {
                entity.direction = Direction.UP;
            }
            else if(downKey){
                entity.direction = Direction.DOWN;
            }
        } else if (attack) {
            entity.status = Entity_Status.ATTACKING;
        } else {
            entity.status = Entity_Status.IDLE;
        }

        if(changeWeapon){
           // if(knight.attackStatus == Attack_Status.NONE) knight.attackStatus = Attack_Status.STAB;
           // else
            if(entity.attackStatus == Attack_Status.STAB) entity.attackStatus = Attack_Status.SHOOT;
            else if(entity.attackStatus == Attack_Status.SHOOT) entity.attackStatus = Attack_Status.STAB;
        }
    }

    public Moving(Entity entity) {
        this.entity = entity;
        this.checkCollision = new CheckCollision(this.entity);
    }
    public void move_Update_Location(int typeEntity){
        if(typeEntity == 0){ // Player
            move_update_Direction();
            if(entity.status == Entity_Status.WALKING){
                float oldX, oldY, x, y;
                oldX = x = entity.getX();
                oldY = y = entity.getY();
                // Vector2 oldPosition = new Vector2(x, y);
                if(entity.direction == Direction.UP){
                    y += entity.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
                }
                if(entity.direction == Direction.DOWN){
                    y -= entity.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
                }
                if(entity.direction == Direction.LEFT){
                    x -= entity.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
                }
                if(entity.direction == Direction.RIGHT){
                    x += entity.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
                }
                if(entity.direction == Direction.UPLEFT){
                    y += entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                    x -= entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                }
                if(entity.direction == Direction.UPRIGHT){
                    y += entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                    x += entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                }
                if(entity.direction == Direction.DOWNLEFT){
                    y -= entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                    x -= entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                }
                if(entity.direction == Direction.DOWNRIGHT){
                    y -= entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                    x += entity.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                }
                entity.setPosision(x, y);
                checkCollision.checkCollisionKnightWithMap(oldX, oldY);
            }
        }

    }


    public void movingMonster(float targetX, float targetY){
        float oldX = entity.getX(), oldY = entity.getY();
        Vector2 res = new Vector2();
        Vector2 targetVector = new Vector2(targetX - entity.getX(), targetY - entity.getY());
        res.set(targetVector).nor().scl(entity.getSpeed_Stright());
        entity.setPosision(entity.getX() + res.x*Gdx.graphics.getDeltaTime(), entity.getY() + res.y* Gdx.graphics.getDeltaTime());
        checkCollision.checkCollisionMonsterWithMap(oldX, oldY);
    }
}
