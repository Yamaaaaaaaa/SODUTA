package com.mygdx.game.entity;

import java.awt.*;

public class Entity {
    public float worldX, worldY; // Vị trí của nó ở trong bản đồ, ta sẽ để nó cố định.
    public String direction;
    public int speed;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false; // Biến này để check trên đường đi nó có bị va chạm hay ko. Nếu có thì là true => Ko cho đi nữa, và ng lại.
}
