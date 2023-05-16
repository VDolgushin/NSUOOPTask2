package ru.nsu.dolgushin.lab3game.fxview;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AboutWindow {
    private final Stage stage;
    private final Scene oldScene;
    private Scene newScene;
    private boolean flagFirstStart;
    public AboutWindow(Stage stage,Scene oldScene ){
        this.stage = stage;
        this.oldScene = oldScene;
        flagFirstStart = true;
    }
    public void showAbout(){
        if(!flagFirstStart){
            stage.setScene(newScene);
            return;
        }
        flagFirstStart = false;
        Text text1 = new Text("Apricot Garden... Such beautiful and peaceful place...");
        Text text2 = new Text("But today death and murder have come to this land!");
        Text text3 = new Text("Souls of the dead should not desecrate this glorious space!");
        Text text4 = new Text("Raise your holy glock and send them back to limbo!");
        Text textControls = new Text("\n\n\nControls: move and jump by arrows, aim by mouse");
        text1.setFont(new Font("IMPACT",65));
        text2.setFont(new Font("IMPACT",65));
        text3.setFont(new Font("IMPACT",65));
        text4.setFont(new Font("IMPACT",65));
        textControls.setFont(new Font("IMPACT",65));
        Button backButton = new Button("Back");
        backButton.setFont(new Font(60));
        VBox vBoxABout = new VBox(text1,text2,text3,text4,textControls,backButton);
        newScene = new Scene(vBoxABout);
        stage.setScene(newScene);
        backButton.setOnAction(actionEvent -> stage.setScene(oldScene));
    }
}
