package com.mimo.messengerclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        Scene scene = new Scene(root, 1200, 900, Color.rgb(26, 26, 26));
        stage.setResizable(false);
        stage.setTitle("Messenger - Client");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/MessengerIcon.png")));
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
