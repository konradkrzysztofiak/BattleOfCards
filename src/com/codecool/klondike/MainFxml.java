package com.codecool.klondike;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class MainFxml extends Application {


    private static final double WINDOW_WIDTH = 1280;
    private static final double WINDOW_HEIGHT = 720;

    Stage window;
    Scene menuScene, gameScene;
    Label labelPlayer1 = new Label("Player 1");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Xml/main.fxml"));
        primaryStage.setTitle("Battle of Cards");
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH,WINDOW_HEIGHT));
        primaryStage.show();
    }
}