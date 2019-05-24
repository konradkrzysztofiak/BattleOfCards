package com.codecool.klondike;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundPlayer {

    private String filePath;
    private Media sound;
    private MediaPlayer mediaPlayer;

    public SoundPlayer(String filePath) {
        this.filePath = filePath;
        sound = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
    }

    public void Stop()
    {
        mediaPlayer.stop();
    }

    public String getFilePath() {
        return filePath;
    }

    public void Play()
    {
        mediaPlayer.play();
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
        sound = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
    }
}
