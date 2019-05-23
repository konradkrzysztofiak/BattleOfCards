package com.codecool.klondike;

import com.codecool.klondike.Dao.CardsDaoXml;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.File;


public class Main extends Application {

    private static final double WINDOW_WIDTH = 1400;
    private static final double WINDOW_HEIGHT = 900;

    Stage window;
    Scene menuScene, gameScene;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        /*String musicFile = "got.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();*/
        // MENU --------------------------------------------------------

        Button buttonPlay = new Button("Play");
        buttonPlay.setOnAction(e -> {
            window.setScene(gameScene);
          //  mediaPlayer.stop();
        });

        Label label1 = new Label("Name:");
        TextField textField = new TextField ();
        label1.setPadding(new Insets(50, 10, 10, 10));
        textField.setPrefWidth(80);

        VBox layout = new VBox(50);

        layout.getChildren().addAll(buttonPlay, label1, textField);
        menuScene = new Scene(layout, 640, 480);

        // GAME --------------------------------------------------------
        Card card = new Card();
        card.loadCardImages();
        Game game = new Game();
        game.setTableBackground(new Image("/table/background.gif"));

        Button reset = new Button("Reset");
        Button nextRound = new Button("Next Round");

        Main main = new Main();
        reset.setOnAction(e -> {
            try {
                main.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        gameScene = new Scene(game, WINDOW_WIDTH, WINDOW_HEIGHT);

        game.getChildren().addAll(reset);
        window.setTitle("Battle of Cards");
        window.setScene(menuScene);
        window.show();
    }

}
