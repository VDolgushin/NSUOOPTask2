package ru.nsu.dolgushin.lab3game.fxview;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.nsu.dolgushin.lab3game.model.Model;

import java.io.*;

public class HighScoresWindow {
    private final Stage stage;
    private final Scene oldScene;
    private Scene newScene;
    private boolean flagFirstStart;
    public HighScoresWindow(Stage stage, Scene oldScene ){
        this.stage = stage;
        this.oldScene = oldScene;
        flagFirstStart = true;
    }
    public void showHighScores(){
        if(!flagFirstStart){
            stage.setScene(newScene);
            return;
        }
        flagFirstStart = false;
        VBox vBoxABout = new VBox();

        File file = Model.getLeaderboard();
        try(BufferedReader bf = new BufferedReader(new FileReader(file))){
            String line = bf.readLine();
            while( line != null){
                Text text = new Text(line);
                text.setFont(new Font("IMPACT",30));
                vBoxABout.getChildren().add(text);
                line = bf.readLine();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        Button backButton = new Button("Back");
        backButton.setFont(new Font(60));
        vBoxABout.getChildren().add(backButton);

        newScene = new Scene(vBoxABout);
        stage.setScene(newScene);
        backButton.setOnAction(actionEvent -> stage.setScene(oldScene));
    }
}
