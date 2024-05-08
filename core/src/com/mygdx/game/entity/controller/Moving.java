package com.mygdx.game.entity.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.entity.Knight;

public class Moving {
    Knight knight;
    public boolean upKey, downKey, leftKey, rightKey;

    private void move_update_Direction(){
        upKey = Gdx.input.isKeyPressed(Input.Keys.W);
        rightKey = Gdx.input.isKeyPressed(Input.Keys.D);
        leftKey = Gdx.input.isKeyPressed(Input.Keys.A);
        downKey = Gdx.input.isKeyPressed(Input.Keys.S);

        if(!leftKey && !rightKey && !upKey && !downKey) {
            knight.status = Entity_Status.IDLE;
        }
        else {
            knight.status = Entity_Status.WALKING;
            if(leftKey && !rightKey && upKey && !downKey) {
                knight.direction = Direction.UPLEFT;
            }
            else if(leftKey && !rightKey && !upKey && downKey) {
                knight.direction = Direction.DOWNLEFT;
            }
            else if(!leftKey && rightKey && upKey && !downKey) {
                knight.direction = Direction.UPRIGHT;
            }
            else if(!leftKey && rightKey && !upKey && downKey) {
                knight.direction = Direction.DOWNRIGHT;
            }
            else if(leftKey) {
                knight.direction = Direction.LEFT;
            }
            else if(rightKey) {
                knight.direction = Direction.RIGHT;
            }
            else if(upKey) {
                knight.direction = Direction.UP;
            }
            else if(downKey){
                knight.direction = Direction.DOWN;
            }
        }
    }
    private CheckCollision checkCollision;
    public Moving(Knight knight) {
        this.knight = knight;
        this.checkCollision = new CheckCollision(this.knight);
    }
    public void move_Update_Location(){
        move_update_Direction();

        if(knight.status != Entity_Status.IDLE){
            float oldX, oldY, x, y;
            oldX = x = knight.getX();
            oldY = y = knight.getY();
            // Vector2 oldPosition = new Vector2(x, y);
            if(knight.direction == Direction.UP){
                y += knight.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
            }
            if(knight.direction == Direction.DOWN){
                y -= knight.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
            }
            if(knight.direction == Direction.LEFT){
                x -= knight.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
            }
            if(knight.direction == Direction.RIGHT){
                x += knight.getSpeed_Stright() * Gdx.graphics.getDeltaTime();
            }
            if(knight.direction == Direction.UPLEFT){
                y += knight.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                x -= knight.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
            }
            if(knight.direction == Direction.UPRIGHT){
                y += knight.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                x += knight.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
            }
            if(knight.direction == Direction.DOWNLEFT){
                y -= knight.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                x -= knight.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
            }
            if(knight.direction == Direction.DOWNRIGHT){
                y -= knight.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
                x += knight.getSpeed_Cross() * Gdx.graphics.getDeltaTime();
            }

            //  Vector2 newPosition = new Vector2(x, y);
            knight.setPosision(x, y);
            checkCollision.check(Gdx.graphics.getDeltaTime(), oldX, oldY);
        }
    }
}
