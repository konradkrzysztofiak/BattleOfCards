package com.codecool.klondike;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;


public class Main extends Application {

    private static final double WINDOW_WIDTH = 1280;
    private static final double WINDOW_HEIGHT = 720;

    private Stage window;
    private Scene menuScene, gameScene;

    Game game = new Game();


    public static void main(String[] args) {
        launch(args);
    }

    public static Label player1Label(List stats, Label cardStats)
    {
        cardStats.setText(stats.get(0) + "\n" + stats.get(1) + "\n" + stats.get(2) + "\n" + stats.get(3));

        return cardStats;
    }

    public static Label player2Label(List stats, Label card2Stats)
    {
        card2Stats.setText(stats.get(0) + "\n" + stats.get(1) + "\n" + stats.get(2) + "\n" + stats.get(3));

        return card2Stats;
    }

    public static Label playerTurnColor(Label labelPlayer, String color)
    {
        labelPlayer.setTextFill(Color.web(color));
        return labelPlayer;
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        SoundPlayer soundPlayer = new SoundPlayer("got.mp3");
        soundPlayer.Play();

        double MIN_WIDTH = 100;
        // MENU --------------------------------------------------------
        Button newGameButton = new Button("New game!");
        newGameButton.setMinWidth(MIN_WIDTH);
        newGameButton.setTranslateX(100);
        newGameButton.setTranslateY(120);

        Button exitButton = new Button("Exit");
        exitButton.setMinWidth(MIN_WIDTH);
        exitButton.setTranslateX(100);
        exitButton.setTranslateY(180);

        newGameButton.setOnAction(e -> {
            window.setScene(gameScene);
            soundPlayer.Stop();
            soundPlayer.setFilePath("gamemusic.mp3");
            soundPlayer.Play();
        });

        exitButton.setOnAction(e -> { primaryStage.close();});

        // player 1
        Label labelPlayer1 = new Label("Player 1");
        labelPlayer1.setLayoutX(110);
        labelPlayer1.setLayoutY(350);
        labelPlayer1.setFont(new Font(40));
        labelPlayer1.setTextFill(Color.web("#FFFFFF"));

        //player 2
        Label labelPlayer2 = new Label("Player 2");
        labelPlayer2.setLayoutX(1000);
        labelPlayer2.setLayoutY(350);
        labelPlayer2.setFont(new Font(40));
        labelPlayer2.setTextFill(Color.web("#FFFFFF"));

        // VS label
        Label versus = new Label("VS");
        versus.setLayoutX((WINDOW_WIDTH / 2) - 25);
        versus.setLayoutY(WINDOW_HEIGHT / 5);
        versus.setFont(new Font(50));
        versus.setTextFill(Color.web("#FFFFFF"));

        // Statistics label
        Label stats = new Label("Statistics");
        stats.setLayoutX((WINDOW_WIDTH / 2) - 100);
        stats.setLayoutY((WINDOW_HEIGHT / 2) + 20);
        stats.setFont(new Font(40));
        stats.setTextFill(Color.web("#FFFFFF"));

        // stats label
        Label str = new Label("Strength (Q)\nSkills (W)\nMoney (E)\nInfluence (R)");
        str.setLayoutX((WINDOW_WIDTH / 2) - 80);
        str.setLayoutY((WINDOW_HEIGHT / 2) + 110);
        str.setFont(new Font(30));
        str.setTextFill(Color.web("#FFFFFF"));

        //stats player1's card
        Label cardStats = new Label("0\n0\n0\n0");
        cardStats.setLayoutX((WINDOW_WIDTH / 2) - 200);
        cardStats.setLayoutY((WINDOW_HEIGHT / 2) + 110);
        cardStats.setFont(new Font(30));
        cardStats.setTextFill(Color.web("#FFFFFF"));

        //stats player2's card
        Label card2Stats = new Label("0\n0\n0\n0");
        card2Stats.setLayoutX((WINDOW_WIDTH / 2) + 180);
        card2Stats.setLayoutY((WINDOW_HEIGHT / 2) + 110);
        card2Stats.setFont(new Font(30));
        card2Stats.setTextFill(Color.web("#FFFFFF"));


        VBox layout = new VBox(50);
        BackgroundImage myBI= new BackgroundImage(new Image("/table/menubackground.jpg",640,480, false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        layout.setBackground(new Background(myBI));

        layout.getChildren().addAll(newGameButton, exitButton);
        menuScene = new Scene(layout, 640, 480);

        // GAME --------------------------------------------------------
        Card card = new Card();
        card.loadCardImages();

        primaryStage.setResizable(false);
        // ------------- HUD --------------------------------------------

        Game game = new Game();
        gameScene = new Scene(game, WINDOW_WIDTH, WINDOW_HEIGHT);

        game.setTableBackground(new Image("/table/background.gif"));
        game.getChildren().addAll(labelPlayer1, labelPlayer2, versus, stats, str, cardStats, card2Stats);
        game.addMouseEventHandlers(card);

        game.setGameScene(gameScene);
        game.setGameLabelPlayer1(cardStats);
        game.setGameLabelPlayer2(card2Stats);
        game.setLabelColorPlayer1(labelPlayer1);
        game.setLabelColorPlayer2(labelPlayer2);

        window.setTitle("Battle of Cards");
        window.setScene(menuScene);
        window.show();
    }
}
