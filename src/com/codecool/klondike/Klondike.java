package com.codecool.klondike;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import com.codecool.klondike.Game;


public class Klondike extends Application {

    private static final double WINDOW_WIDTH = 1400;
    private static final double WINDOW_HEIGHT = 900;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        Image image = new Image(getClass().getResourceAsStream("/table/reset.png"));
        Button btn = new Button();
        Button btn2 = new Button("Restart game");
        btn.setGraphic(new ImageView(image));

        Popup popup = new Popup();
        Label label = new Label("POPUP");
        label.setStyle(" -fx-background-color: magenta;");
        popup.getContent().add(label);
        popup.getContent().add(btn2);

        popup.setX(900);
        popup.setY(800);


        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                start(primaryStage);
            }
        });

        btn2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                start(primaryStage);
            }
        });

        Card.loadCardImages();
        Game game = new Game();
        game.setTableBackground(new Image("/table/green.png"));
        game.getChildren().add(btn);

        primaryStage.setTitle("Klondike Solitaire");
        primaryStage.setScene(new Scene(game, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();

//        if (game.isGameWon()) {
//            System.out.println("ugabuga");
//            popup.show(primaryStage);
//        }
    }

}
