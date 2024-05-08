package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.setting.Setting_Knight;

import java.util.Set;

public class Knight implements InputProcessor {

    //movement velocity
    Sprite sprite;
    private Vector2 velocity = new Vector2();
    private float speed;
    private TiledMapTileLayer collisionLayer;
    private String blockedKey = "blocked";

    public Animation[] rolls;
    public int roll;
    private Setting_Knight settingKnight;
    public Knight (Sprite sprite, TiledMapTileLayer collisionLayer, Setting_Knight settingKnight) {
        this.sprite = sprite;

        TextureRegion[][] rollSpriteSheetShoot = TextureRegion.split(new Texture("basic/character/Shoot.png"),32,32);
        TextureRegion[][] rollSpriteSheetCrossbow = TextureRegion.split(new Texture("basic/character/Crossbow.png"),32,32);
        TextureRegion[][] rollSpriteSheetStab = TextureRegion.split(new Texture("basic/character/Stab.png"),32,32);
        TextureRegion[][] rollSpriteSheetStand = TextureRegion.split(new Texture("basic/character/Stand.png"),32,32);
        TextureRegion[][] rollSpriteSheetWalk = TextureRegion.split(new Texture("basic/character/Walk.png"),32,32);
        rolls = new Animation[100];
        roll = 4;
        //
        rolls[0] = new Animation(0.2f,rollSpriteSheetWalk[0]);
        rolls[1] = new Animation(0.2f,rollSpriteSheetWalk[1]);
        rolls[2] = new Animation(0.2f,rollSpriteSheetWalk[2]);
        rolls[3] = new Animation(0.2f,rollSpriteSheetWalk[3]);
        //
        rolls[4] = new Animation(0.2f,rollSpriteSheetStand[0]);
        rolls[5] = new Animation(0.2f,rollSpriteSheetStand[1]);
        rolls[6] = new Animation(0.2f,rollSpriteSheetStand[2]);
        rolls[7] = new Animation(0.2f,rollSpriteSheetStand[3]);
        //
        rolls[8] = new Animation(0.1f,rollSpriteSheetStab[0]);
        rolls[9] = new Animation(0.1f,rollSpriteSheetStab[1]);
        rolls[10] = new Animation(0.1f,rollSpriteSheetStab[2]);
        rolls[11] = new Animation(0.1f,rollSpriteSheetStab[3]);
        //
        rolls[12] = new Animation(0.1f,rollSpriteSheetCrossbow[0]);
        rolls[13] = new Animation(0.1f,rollSpriteSheetCrossbow[1]);
        rolls[14] = new Animation(0.1f,rollSpriteSheetCrossbow[2]);
        rolls[15] = new Animation(0.1f,rollSpriteSheetCrossbow[3]);
        //
        rolls[16] = new Animation(0.1f,rollSpriteSheetShoot[0]);
        rolls[17] = new Animation(0.1f,rollSpriteSheetShoot[1]);
        rolls[18] = new Animation(0.1f,rollSpriteSheetShoot[2]);
        rolls[19] = new Animation(0.1f,rollSpriteSheetShoot[3]);
        this.settingKnight = settingKnight;
        this.speed = this.settingKnight.SPEED;
        this.collisionLayer = collisionLayer;
        sprite.setPosition(43*this.getCollisionLayer().getTileWidth(),102* this.getCollisionLayer().getTileHeight());
        sprite.setSize(settingKnight.WIDTH, settingKnight.HEIGHT);
    }


    public void drawAnimation(Batch spriteBatch,float stateTime,float x,float y) {
        update(Gdx.graphics.getDeltaTime());
        sprite.draw(spriteBatch);
        spriteBatch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime,true),x,y,64,48);
    }

    public void update(float delta) {

        //Collision.
        // This keeps track of the previous position.
        float oldX = sprite.getX();
        float oldY = sprite.getY();
        float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();

        boolean collideX = false, collideY = false;

        // move on X
        sprite.setX(sprite.getX() + velocity.x * delta);
        if (velocity.x < 0) { // left hay vector của nó âm thì nó đi từ phải sang trái
            collideX = collidesLeft();
        } else if (velocity.x > 0) { //right
            collideX = collidesRight();
        }

        if (collideX) {
            sprite.setX(oldX);
            velocity.x = 0;
        }

        // move on Y
        sprite.setY(sprite.getY() + velocity.y * delta);

        if (velocity.y < 0) { // going down
            collideY = collidesBottom();

        } else if (velocity.y > 0) { // going up
            collideY = collidesTop();
        }

        if (collideY) {
            sprite.setY(oldY);
            velocity.y = 0;
        }


    }


    // Cách làm: Tìm tới tệp chưa cái tile cần block, Thêm Phần code sau:
    //    <tile id="0">
    //        <properties>
    //            <property name="blocked" value=""/>
    //        </properties>
    //    </tile>
    // Explain: Cái hàm dưới, nó check cái Value của Property, nó có Key = "blocked" => Ta chỉ cần tạo cho nó 1 cái đấy là đc(vì chỉ check là null thôi mà)
    //id là cái ID của tile trong bản đồ(lúc mk tạo map bằng Tiled nó đã có, xem thêm ở file .tmx.
    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
    }

    public boolean collidesRight() {
        for(float step = 0; step < sprite.getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(sprite.getX() + sprite.getWidth(), sprite.getY() + step))
                return true;
        return false;
    }

    public boolean collidesLeft() {
        for(float step = 0; step < sprite.getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(sprite.getX(), sprite.getY() + step))
                return true;
        return false;
    }

    public boolean collidesTop() {
        for(float step = 0; step < sprite.getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(sprite.getX() + step, sprite.getY() + sprite.getHeight()))
                return true;
        return false;
    }

    public boolean collidesBottom() {
        for(float step = 0; step < sprite.getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(sprite.getX() + step, sprite.getY()))
                return true;
        return false;
    }


    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:{
                roll = 1;
                velocity.y += speed;

                break;
            }

            case Input.Keys.A:{
                roll = 3;
                velocity.x -= speed;
                break;
            }

            case Input.Keys.D:{
                roll = 2;
                velocity.x += speed;
                break;
            }

            case Input.Keys.S:{
                roll = 0;
                velocity.y -= speed;
                break;
            }
            case  Input.Keys.J:{
                // chém

                if(roll == 1 || roll == 5 || roll ==13  || roll == 17) roll = 9;
                if(roll == 2 || roll == 6 || roll == 14 || roll == 18) roll = 10;
                if(roll == 3 || roll == 7 || roll == 15 || roll == 19) roll = 11;
                if(roll == 0 || roll == 4 || roll == 12 || roll == 16) roll = 8;
                break;
            }
            case Input.Keys.K:{
                // bắn cung
                if(roll == 1 || roll == 5 || roll ==9  || roll == 17) roll = 13;
                if(roll == 2 || roll == 6 || roll ==10  || roll == 18) roll = 14;
                if(roll == 3 || roll == 7 || roll ==11  || roll == 19) roll = 15;
                if(roll == 0 || roll == 4 || roll ==8  || roll == 16) roll = 12;
                break;
            }
            case Input.Keys.L:{
                // bắn súng
                if(roll == 1 || roll == 5 || roll ==13  || roll == 9) roll = 17;
                if(roll == 2 || roll == 6 || roll ==14 || roll == 10) roll = 18;
                if(roll == 3 || roll == 7 || roll ==15 || roll == 11) roll = 19;
                if(roll == 0 || roll == 4 || roll ==12  || roll == 8) roll = 16;
                break;
            }

        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:{

                    roll = 5;
                    velocity.y = 0;
                    break;

            }
            case Input.Keys.S:{

                    roll = 4;
                    velocity.y = 0;
                    break;

            }

            case Input.Keys.A:{

                    roll = 7;
                    velocity.x = 0;
                    break;


            }
            case Input.Keys.D:{

                    roll = 6;
                    velocity.x = 0;
                    break;


            }


        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            // Some stuff
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {

            return true;
        }
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
