package com.mygdx.game.entity;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class KeyHandler implements InputProcessor {

    public boolean upKey,downKey,rightKey, leftKey;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.W:{
                upKey = true;
                break;
            }
            case Input.Keys.A:{
                leftKey = true;
                break;
            }
            case Input.Keys.S:{
                downKey = true;
                break;
            }
            case Input.Keys.D:{
                rightKey = true;
                break;
            }
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.W:{
                upKey = false;
                break;
            }
            case Input.Keys.A:{
                leftKey = false;
                break;
            }
            case Input.Keys.S:{
                downKey = false;
                break;
            }
            case Input.Keys.D:{
                rightKey = false;
                break;
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
