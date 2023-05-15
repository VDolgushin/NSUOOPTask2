package ru.nsu.dolgushin.lab3game.fxview;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NewGameWindow {
    private final Stage stage;
    private final Scene oldScene;

    public NewGameWindow(Stage stage,Scene oldScene ){
        this.stage = stage;
        this.oldScene = oldScene;
    }
    public void showNewGameWindow(){
        TextField tf = new TextField();
        Button enterButton = new Button("Enter");
        tf.setPrefWidth(1400);
        tf.setPrefHeight(200);
        tf.setFont(new Font("IMPACT",120));
        enterButton.setPrefWidth(200);
        enterButton.setPrefHeight(226);
        enterButton.setFont(new Font("IMPACT",40));
        HBox hBox = new HBox(tf,enterButton);
        Scene newScene = new Scene(hBox);
        stage.setScene(newScene);
        enterButton.setOnAction(actionEvent -> {
            String s = tf.getText();
            new FXListenerView(s,stage, oldScene);
        });
    }
}
