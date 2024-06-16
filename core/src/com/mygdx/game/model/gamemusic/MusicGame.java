package com.mygdx.game.model.gamemusic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicGame {
    String musicPath;
    Music music;
    boolean looping;

    public MusicGame(){}
    public MusicGame(String musicPath, boolean looping) {
        this.musicPath = musicPath;
        this.music = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
        this.music.setLooping(looping);
        this.music.setVolume(1.0f);
    }

    public void setVolumeMusic(float volume){
        this.music.setVolume(volume);
    }
    public void setPause(){
        this.music.pause();
    }
    public void setPlay(){
        this.music.play();
    }
    public void setStop(){
        this.music.stop();
    }
    public void dispose(){
        this.music.dispose();
    }
}
