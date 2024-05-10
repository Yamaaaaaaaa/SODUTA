package com.mygdx.game.entity.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.entity.model.Entity;
import com.mygdx.game.entity.model.Monster;

public class Activity {
    private Entity entity;

    public boolean upKey, downKey, leftKey, rightKey;
    public boolean attack;
    public boolean changeWeapon;
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
    private CheckCollision checkCollision;
    public Activity(Entity entity) {
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

                //  Vector2 newPosition = new Vector2(x, y);
                entity.setPosision(x, y);
                checkCollision.check(Gdx.graphics.getDeltaTime(), oldX, oldY);
            }
        }
        else if(typeEntity == 1) // Monster
        {
            if(entity.direction_Static.equals("vertical")){
                float oldX, oldY, x, y;
                oldX = x = entity.getX();
                oldY = y = entity.getY();

                if(entity.direction == Direction.UP){
                    if(y >= entity.yMax){
                        entity.direction = Direction.DOWN;
                    }
                    else{
                        y += entity.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
                    }
                }
                else if(entity.direction == Direction.DOWN){
                    ;
                    if(y <= entity.yMin){
                        entity.direction = Direction.UP;
                    }
                    else{
                        y -= entity.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
                    }
                }

                entity.setPosision(x, y);
            }
            else if(entity.direction_Static.equals("horizontal")){
                float oldX, oldY, x, y;
                oldX = x = entity.getX();
                oldY = y = entity.getY();
                if(entity.direction == Direction.RIGHT){
                    if(x >= entity.xMax){
                        entity.direction = Direction.LEFT;
                    }
                    else{
                        x += entity.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
                    }
                }
                else if(entity.direction == Direction.LEFT){
                    if(x <= entity.xMin){
                        entity.direction = Direction.RIGHT;
                    }
                    else{
                        x -= entity.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
                    }
                }
                entity.setPosision(x, y);
            }
        }
    }
}
