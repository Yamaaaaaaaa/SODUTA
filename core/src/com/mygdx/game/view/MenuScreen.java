package com.mygdx.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.SpaceGame;
import com.mygdx.game.setting.Media_Menu;
import com.mygdx.game.setting.Setting_MenuScreen;

public class MenuScreen implements Screen {
    private SpaceGame game;
    public final Stage stage;
    TextButton buttonRecordFunction;
    TextButton buttonNetwork;
    TextButton buttonLevel1;
    TextButton buttonLevel2;
    TextButton buttonMenu;
    TextButton buttonNewGame;
    TextButton buttonOldGame;
    TextButton buttonExit;
    Media_Menu mediaMenu;
    public MenuScreen(final SpaceGame game){
        this.game = game;
        this.stage = new Stage(new FillViewport(Setting_MenuScreen.WINDOWS_SIZE, Setting_MenuScreen.WINDOWS_SIZE));

        mediaMenu = new Media_Menu();
        mediaMenu.loadAssets();
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        initButtons();
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(mediaMenu.imgMenu, 0,0);
        stage.getBatch().end();
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        stage.dispose();
        mediaMenu.disposeAssets();
    }
    private void initButtons() {
        buttonMenu = new TextButton("Back to Menu", Setting_MenuScreen.skin, "default");
        buttonMenu.setPosition((float) Setting_MenuScreen.WINDOWS_SIZE / 2 - 140, (float) Setting_MenuScreen.WINDOWS_SIZE / 2 + 20);
        buttonMenu.setSize(Setting_MenuScreen.BUTTON_WIDTH, Setting_MenuScreen.BUTTON_HEIGHT);
        buttonMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Xóa màn hình, thêm các nút của menu
                stage.clear();
                //stage.addActor(buttonRecordFunction);
                //stage.addActor(buttonNetwork);
                stage.addActor(buttonNewGame);
                // stage.addActor(buttonOldGame);
                stage.addActor(buttonExit);
                // mediaMenu.buttonSound.play();
            }
        });

        buttonLevel1 = new TextButton("Level 1", Setting_MenuScreen.skin, "default");
        buttonLevel1.setPosition((float) Setting_MenuScreen.WINDOWS_SIZE / 2 - 300, (float) Setting_MenuScreen.WINDOWS_SIZE / 2 + 120);
        buttonLevel1.setSize(Setting_MenuScreen.BUTTON_WIDTH, Setting_MenuScreen.BUTTON_HEIGHT);
        buttonLevel1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Chọn ải 1.");
                dispose();
                game.setScreen(new GameScreen(game));
            }
        });

//        buttonLevel2 = new TextButton("Level 2", Setting_MenuScreen.skin, "default");
//        buttonLevel2.setPosition((float) Setting_MenuScreen.WINDOWS_SIZE / 2 + 20, (float) Setting_MenuScreen.WINDOWS_SIZE / 2 + 120);
//        buttonLevel2.setSize(Setting_MenuScreen.BUTTON_WIDTH, Setting_MenuScreen.BUTTON_HEIGHT);
//        buttonLevel2.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Chọn ải 2.");
//              //  dispose();
//                //game.setScreen(new GamePlayingScreen(game, Variable.LEVEL_2));
//            }
//        });

        buttonNewGame = new TextButton("New Game", Setting_MenuScreen.skin, "default");
        buttonNewGame.setPosition((float) Setting_MenuScreen.WINDOWS_SIZE / 2 - 300, (float) Setting_MenuScreen.WINDOWS_SIZE / 2 + 120);
        buttonNewGame.setSize(Setting_MenuScreen.BUTTON_WIDTH, Setting_MenuScreen.BUTTON_HEIGHT);
        buttonNewGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Xóa màn hình, thêm các nút chọn ải.
                stage.clear();
                stage.addActor(buttonLevel1);
                //stage.addActor(buttonLevel2);
                stage.addActor(buttonMenu);
                // mediaMenu.buttonSound.play();
            }
        });

//        buttonOldGame = new TextButton("Old Game", Variable.skin, "default");
//        buttonOldGame.setPosition((float) Variable.WINDOWS_SIZE / 2 + 20, (float) Variable.WINDOWS_SIZE / 2 + 120);
//        buttonOldGame.setSize(Variable.BUTTON_WIDTH, Variable.BUTTON_HEIGHT);
//        buttonOldGame.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                stage.clear();
//                if(new File(Variable.GAME_DATA_1).exists()){
//                    TextButton buttonData1;
//                    buttonData1 = new TextButton("Save 1 :)", Variable.skin, "default");
//                    buttonData1.setPosition((float) Variable.WINDOWS_SIZE / 2 - 300, (float) Variable.WINDOWS_SIZE / 2 + 120);
//                    buttonData1.setSize(Variable.BUTTON_WIDTH, Variable.BUTTON_HEIGHT);
//                    buttonData1.addListener(new ClickListener() {
//                        @Override
//                        public void clicked(InputEvent event, float x, float y) {
//                            dispose();
//                            LoadGameDataTool loadTool = new LoadGameDataTool(game);
//                            try {
//                                loadTool.LoadFromFile(Variable.GAME_DATA_1);
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                            game.setScreen(loadTool.getGamePlayingScreen());
//                        }
//                    });
//                    stage.addActor(buttonData1);
//                }
//                else{
//                    TextButton buttonEmpty;
//                    buttonEmpty = new TextButton("Empty :(", Variable.skin, "default");
//                    buttonEmpty.setPosition((float) Variable.WINDOWS_SIZE / 2 - 300, (float) Variable.WINDOWS_SIZE / 2 + 120);
//                    buttonEmpty.setSize(Variable.BUTTON_WIDTH, Variable.BUTTON_HEIGHT);
//                    buttonEmpty.addListener(new ClickListener() {
//                        @Override
//                        public void clicked(InputEvent event, float x, float y) {
//                            MediaMenu.buttonSound.play();
//                        }
//                    });
//                    stage.addActor(buttonEmpty);
//                }
//                if(Gdx.files.internal(Variable.GAME_DATA_2).exists()){
//                    TextButton buttonData2;
//                    buttonData2 = new TextButton("Save 2 :)", Variable.skin, "default");
//                    buttonData2.setPosition((float) Variable.WINDOWS_SIZE / 2 + 20, (float) Variable.WINDOWS_SIZE / 2 + 120);
//                    buttonData2.setSize(Variable.BUTTON_WIDTH, Variable.BUTTON_HEIGHT);
//                    buttonData2.addListener(new ClickListener() {
//                        @Override
//                        public void clicked(InputEvent event, float x, float y) {
//                            dispose();
//                            LoadGameDataTool loadTool = new LoadGameDataTool(game);
//                            try {
//                                loadTool.LoadFromFile(Variable.GAME_DATA_2);
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                            game.setScreen(loadTool.getGamePlayingScreen());
//                        }
//                    });
//                    stage.addActor(buttonData2);
//                }
//                else{
//                    TextButton buttonEmpty;
//                    buttonEmpty = new TextButton("Empty :(", Variable.skin, "default");
//                    buttonEmpty.setPosition((float) Variable.WINDOWS_SIZE / 2 + 20, (float) Variable.WINDOWS_SIZE / 2 + 120);
//                    buttonEmpty.setSize(Variable.BUTTON_WIDTH, Variable.BUTTON_HEIGHT);
//                    buttonEmpty.addListener(new ClickListener() {
//                        @Override
//                        public void clicked(InputEvent event, float x, float y) {
//                            MediaMenu.buttonSound.play();
//                        }
//                    });
//                    stage.addActor(buttonEmpty);
//                }
//                stage.addActor(buttonMenu);
//                MediaMenu.buttonSound.play();
//            }
//        });

        buttonExit = new TextButton("Exit", Setting_MenuScreen.skin, "default");
        buttonExit.setPosition((float) Setting_MenuScreen.WINDOWS_SIZE /2 - 140, (float) Setting_MenuScreen.WINDOWS_SIZE / 2 + 20);
        buttonExit.setSize(Setting_MenuScreen.BUTTON_WIDTH, Setting_MenuScreen.BUTTON_HEIGHT);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //stage.addActor(buttonRecordFunction);
        //stage.addActor(buttonNetwork);
        stage.addActor(buttonNewGame);
        //stage.addActor(buttonOldGame);
        stage.addActor(buttonExit);
    }
}