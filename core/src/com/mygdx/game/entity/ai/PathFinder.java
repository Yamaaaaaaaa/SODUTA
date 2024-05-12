package com.mygdx.game.entity.ai;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.entity.model.Entity;
import com.mygdx.game.screen.GameScreen;

import java.util.ArrayList;

public class PathFinder {
    GameScreen gameScreen;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;
    int maxWorldCol, maxWorldRow;
    public PathFinder(GameScreen screen){
        this.gameScreen = screen;
        this.maxWorldCol = gameScreen.collsionLayer.getWidth();
        this.maxWorldRow = gameScreen.collsionLayer.getHeight();
        InstantiateNode();
    }
    public void InstantiateNode(){
        node = new Node[maxWorldCol][maxWorldRow];
        int col = 0;
        int row = 0;
        while(col < maxWorldCol && row < maxWorldRow){
            node[col][row] = new Node(col, row);
            col ++;
            if(col == maxWorldCol){
                col = 0;
                row ++;
            }
        }
    }

    public void resetNodes(){
        int col = 0;
        int row = 0;
        while(col < maxWorldCol && row < maxWorldRow){
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col ++;
            if(col == maxWorldCol){
                col = 0;
                row ++;
            }
        }
        openList.clear();;
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity){
        resetNodes();

        // set Start and Goal Node:
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int row = 0;
        int col = 0;
        while(col < maxWorldCol && row < maxWorldRow){
            // Set Solid Node:
            // Check Tile:
            TiledMapTileLayer.Cell cell = gameScreen.collsionLayer.getCell(col, row);
            if(cell != null  && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked")){
                node[col][row].solid = true;
            }
            // check interactive tiles
            // getcose:
            getCost(node[col][row]);
            col ++;
            if(col == maxWorldCol){
                col = 0;
                row ++;
            }
        }
    }
    public void getCost(Node node){
        // G Cost:
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H cost:
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        // FCost:
        node.fCost = node.gCost + node.hCost;
    }
    public boolean search(){
        while(goalReached == false && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;

            // Check the current node:
            currentNode.checked = true;
            openList.remove(currentNode);
            // open the UP, right,.. node:
            if(row - 1 >= 0) openNode(node[col][row - 1]);
            if(col - 1 >= 0) openNode(node[col - 1][row]);
            if(row + 1 < maxWorldRow) openNode(node[col][row + 1]);
            if(col + 1 < maxWorldCol) openNode(node[col + 1][row]);

            // find besst node:
            int bestnodeID = 0;
            int bestnodeFCost = 999;
            for(int i =0 ; i < openList.size(); i++){
                if(openList.get(i).fCost < bestnodeFCost){
                    bestnodeID = i;
                    bestnodeFCost = openList.get(i).fCost;
                }
                else if(openList.get(i).fCost == bestnodeFCost){
                    if(openList.get(i).gCost < openList.get(bestnodeID).gCost){
                        bestnodeID = i;
                    }
                }
            }

            // if curr is goal:
            if(openList.size() == 0){
                break;
            }
            currentNode = openList.get(bestnodeID);
            if(currentNode == goalNode){
                goalReached = true;
                BackTrack();
            }
            step ++;
        }
        return goalReached;
    }
    public void openNode(Node node){
        if(node.open == false && node.checked == false && node.solid == false){
            node. open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    public void BackTrack(){
        Node current = goalNode;
        while(current != startNode){
            System.out.print(current.col + ":" + current.row + " -> ");
            pathList.add(0, current);
            current = current.parent;
        }
    }
}
