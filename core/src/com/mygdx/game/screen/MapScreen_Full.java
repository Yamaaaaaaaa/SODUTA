package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MapScreen_Full {
    public GamePlayingScreen gamePlayingScreen;
    public String fileMap_Path;
    public MapScreen_Tile[] mapScreenTiles;
    public int[][] mapScreen_Full_byNum;

    public MapScreen_Full(GamePlayingScreen gamePlayingScreen) {
        this.gamePlayingScreen = gamePlayingScreen;
        this.fileMap_Path = gamePlayingScreen.fileMap_Path;

        this.mapScreenTiles = new MapScreen_Tile[3]; // 0: wall, 1: floor1, 2: floor2;
        this.getTileSprite();
        this.LoadMap_byNum();
    }
    public void getTileSprite(){
        mapScreenTiles[0] = new MapScreen_Tile();
        mapScreenTiles[0].sprite = new Sprite(new Texture("basic/building/wall.png"));
        mapScreenTiles[0].collision = true;

        mapScreenTiles[1] = new MapScreen_Tile();
        mapScreenTiles[1].sprite = new Sprite(new Texture("basic/building/floor1.png"));

        mapScreenTiles[2] = new MapScreen_Tile();
        mapScreenTiles[2].sprite = new Sprite(new Texture("basic/building/floor2.png"));
    }
    public void LoadMap_byNum(){
        FileHandle fileHandle = Gdx.files.internal(fileMap_Path);
        String mapString = fileHandle.readString();

        int rowCount = 0;
        int[][] mapArray = null;
        String[] lines = mapString.split("\\n");
        for (String line : lines) {
            String[] values = line.trim().split(" ");
            if (mapArray == null)
                mapArray = new int[values.length][values.length];
            for (int i = 0; i < values.length; i++)
                mapArray[rowCount][i] = Integer.parseInt(values[i]);
            rowCount++;
        }
        if (mapArray != null) {
            this.mapScreen_Full_byNum = mapArray;
            gamePlayingScreen.tileInCol = mapArray.length;
            gamePlayingScreen.tileInRow = mapArray[0].length;
            System.out.println(gamePlayingScreen.tileInCol + "-" + gamePlayingScreen.tileInRow);
        }
    }
}
