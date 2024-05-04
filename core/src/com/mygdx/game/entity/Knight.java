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

public class Knight extends Sprite implements InputProcessor {

    //movement velocity
    private Vector2 velocity = new Vector2();
    private float speed;
    private TiledMapTileLayer collisionLayer;
    private String blockedKey = "blocked";
    private int targetX;
    private int targetY;
    public Animation[] rolls;
    public int roll;
    private Setting_Knight settingKnight;
    public Knight (Sprite sprite, TiledMapTileLayer collisionLayer, Setting_Knight settingKnight) {
        super(sprite);
        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("basic/character/Walk.png"),32,32);
        rolls = new Animation[15];
        roll = 0;
        rolls[0] = new Animation(0.1f,rollSpriteSheet[0]);
        rolls[1] = new Animation(0.1f,rollSpriteSheet[1]);
        rolls[2] = new Animation(0.1f,rollSpriteSheet[2]);
        rolls[3] = new Animation(0.1f,rollSpriteSheet[3]);

        this.settingKnight = settingKnight;
        this.speed = this.settingKnight.SPEED;
        this.collisionLayer = collisionLayer;
        setSize(settingKnight.WIDTH/3, settingKnight.HEIGHT/3);
    }


    public void drawAnimation(Batch spriteBatch,float stateTime,float x,float y) {
        update(Gdx.graphics.getDeltaTime());
        //super.draw(spriteBatch);

        spriteBatch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime,true),x,y,64,64);
    }

    public void update(float delta) {

        //Collision.
        // This keeps track of the previous position.
        float oldX = getX();
        float oldY = getY();
        float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();

        boolean collideX = false, collideY = false;

        // move on X
        setX(getX() + velocity.x * delta);
        if (velocity.x < 0) { // left hay vector của nó âm thì nó đi từ phải sang trái
            collideX = collidesLeft();
        } else if (velocity.x > 0) { //right
            collideX = collidesRight();
        }

        if (collideX) {
            setX(oldX);
            velocity.x = 0;
        }

        // move on Y
        setY(getY() + velocity.y * delta);

        if (velocity.y < 0) { // going down
            collideY = collidesBottom();

        } else if (velocity.y > 0) { // going up
            collideY = collidesTop();
        }

        if (collideY) {
            setY(oldY);
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
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(getX() + getWidth(), getY() + step))
                return true;
        return false;
    }

    public boolean collidesLeft() {
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(getX(), getY() + step))
                return true;
        return false;
    }

    public boolean collidesTop() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY() + getHeight()))
                return true;
        return false;
    }

    public boolean collidesBottom() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY()))
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
            case  Input.Keys.SPACE:{
                roll = 8;
                break;
            }

        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.S:{
                //roll = 0;
                velocity.y = 0;
                break;
            }

            case Input.Keys.A:
            case Input.Keys.D:{
                //roll = 0;
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
