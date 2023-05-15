package ru.nsu.dolgushin.lab3game.fxview;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MenuFX extends Application {
    public MenuFX(){

    }
    public static void mainThatIsNotMain(String[] args) {
        new MenuFX();
        launch(args);
    }
    @Override
    public void start(Stage stage){
        Button newGameButton = new Button("New Game");
        Button aboutButton = new Button("About");
        Button exitButton = new Button("Exit");
        Button highScoresGameButton = new Button("High Scores");
        newGameButton.setStyle("-fx-font-size: 100; ");
        aboutButton.setStyle("-fx-font-size: 100; ");
        exitButton.setStyle("-fx-font-size: 100; ");
        highScoresGameButton.setStyle("-fx-font-size: 100; ");

        VBox vBox = new VBox( newGameButton, aboutButton,exitButton,highScoresGameButton);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setTitle("JavaFX Application");
        stage.setWidth(1600);
        stage.setHeight(900);
        stage.show();

        AboutWindow aw = new AboutWindow(stage,scene);
        HighScoresWindow hsw = new HighScoresWindow(stage,scene);
        NewGameWindow ngw = new NewGameWindow(stage,scene);

        aboutButton.setOnAction(actionEvent -> aw.showAbout());

        exitButton.setOnAction(actionEvent -> stage.close());

        highScoresGameButton.setOnAction(actionEvent -> hsw.showHighScores());

        newGameButton.setOnAction(actionEvent -> ngw.showNewGameWindow());
    }
}
