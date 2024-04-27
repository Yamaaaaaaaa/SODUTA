package com.mygdx.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.screen.GamePlayingScreen;
import com.mygdx.game.setting.Variable;

import java.awt.*;

public class Player extends Entity{
    public GamePlayingScreen gamePlayingScreen;

    // Hình ảnh
    public Sprite spriteKnight_Left;
    public Sprite spriteKnight_Right;
    public Sprite currentSprite;

    // Vị trí
    public float screenX, screenY;


    // Di chuyển:
    public float speed;
    public String direction = "left";
    public KeyHandler keyHandler;

    public Player(GamePlayingScreen gamePlayingScreen, KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        this.gamePlayingScreen = gamePlayingScreen;
        spriteKnight_Left = new Sprite(new Texture("basic/roles/knightLeft.png"));
        spriteKnight_Left.setSize(Variable.KNIGHT_WIDTH, Variable.KNIGHT_HEIGHT);
        spriteKnight_Right = new Sprite(new Texture("basic/roles/knightRight.png"));
        spriteKnight_Right.setSize(Variable.KNIGHT_WIDTH, Variable.KNIGHT_HEIGHT);
        currentSprite = spriteKnight_Left;


        // Set vị trí của ng chơi trong màn hình screen
        screenX = (float) (Variable.WINDOWS_SIZE / 2 - Variable.TILE_SIZE / 2);
        screenY = (float) (Variable.WINDOWS_SIZE / 2 - Variable.TILE_SIZE / 2);

        // Set SolidArea để check va chạm:
        solidArea = new Rectangle(8,8,35,35);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // Set tốc độ:
        speed = Variable.KNIGHT_SPEED;
    }

    public void update(){
        if(keyHandler.downKey || keyHandler.upKey || keyHandler.leftKey || keyHandler.rightKey) {

            System.out.println(keyHandler.upKey + "- " + keyHandler.downKey + "-" + keyHandler.leftKey + "-" + keyHandler.rightKey);
            // Vòng if này để check xem có thay đổi khung hình khi đứng yên hay ko, nếu mà ta đang ko nhấn phím nào thì nó cũng ko thay đổi khung hình nữa.
            if (keyHandler.upKey) {
                direction = "up";
            } else if (keyHandler.downKey) {
                direction = "down";
            } else if (keyHandler.leftKey) {
                direction = "left";
            } else if (keyHandler.rightKey) {
                direction = "right";
            }

            // Thay đổi animation của nhân vật.
            if(direction.equals("left")){
                currentSprite = spriteKnight_Left;
            }
            else if(direction.equals("right")){
                currentSprite = spriteKnight_Right;
            }

            // Trước khi thay đổi vị trí di chuyển: ta cần kiểm tra xem bước vừa đi có cụng đầu vào đâu hay ko.

                // reset cái collison của Player về 0 đã rồi mới bật hàm check
                collisionOn = false;
             //   gamePlayingScreen.checkCollision.checkTile(this);

            //NẾU COLLISION ON = FALSE => NG CHƠI CÓ THỂ DI CHUYỂN, NG LẠI THÌ KO
           // if(collisionOn == false){
                switch (direction){
                    case "up": worldY += (int) (speed * Gdx.graphics.getDeltaTime()); break;
                    case "down": worldY -= (int) (speed * Gdx.graphics.getDeltaTime()); break;
                    case "left": worldX -= (int) (speed * Gdx.graphics.getDeltaTime()); break;
                    case "right": worldX += (int) (speed * Gdx.graphics.getDeltaTime()); break;
                }
          //  }
        }
    }
}
