package com.codecool.klondike;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundPlayer {

    String filePath = "gamemusic.mp3";

    Media sound = new Media(new File(filePath).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);

    public void Stop()
    {
        mediaPlayer.stop();
    }

    public void Play(String filePath)
    {
        sound = new Media(new File(filePath).toURI().toString());
        mediaPlayer.play();
    }

}
