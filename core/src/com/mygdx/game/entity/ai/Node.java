package com.mygdx.game.entity.ai;

public class Node {
    Node parent;
    public int col, row, gCost, hCost, fCost;
    boolean solid, open, checked;
    public Node(int col, int row){
        this.col = col;
        this.row = row;
    }
}
