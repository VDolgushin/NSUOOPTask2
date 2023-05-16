package ru.nsu.dolgushin.lab3game.fxview;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.nsu.dolgushin.lab3game.controller.SimpleController;
import ru.nsu.dolgushin.lab3game.model.Model;
import ru.nsu.dolgushin.lab3game.model.modellistener.ModelListener;
import ru.nsu.dolgushin.lab3game.model.modellistener.VisibleGameObjectViewInfo;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


public class FXListenerView implements ModelListener {

    private final HashMap<Integer, ImageView> visibleObjectsMap;
    private final Text scores;
    private final Text playerHP;
    private final Text stopped;
    private final Scene oldScene;
    private final Stage stage;
    private final Pane pane;
    private final Clip clip;
    public FXListenerView(String playerName, Stage stage, Scene oldScene) {
        this.oldScene = oldScene;
        this.stage = stage;

        visibleObjectsMap = new HashMap<>();
        scores = new Text(("Score: "+0));
        scores.setFont(new Font("IMPACT",50));
        playerHP = new Text(("HP: "));
        playerHP.setFont(new Font("IMPACT",50));
        stopped = new Text("PAUSED");
        stopped.setFont(new Font("IMPACT",500));
        stopped.setVisible(false);
        URL u = this.getClass().getResource("sprites/ApricotGarden.png");
        assert u != null;
        BackgroundImage background = new BackgroundImage(new Image(u.toString()), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1600,900,false,false,false,false));

        scores.setX(10);
        scores.setY(50);
        playerHP.setX(1450);
        playerHP.setY(50);
        stopped.setX(33);
        stopped.setY(600);
        pane = new Pane(scores,stopped,playerHP);
        pane.setBackground(new Background(background));

        Scene scene = new Scene(pane);
        stage.setScene(scene);

        stage.setWidth(1600);
        stage.setHeight(900);

        Model model = new Model(this,playerName);
        SimpleController sc = new SimpleController(model);
        scene.setOnKeyPressed(sc::keyPressed);
        scene.setOnKeyReleased(sc::keyReleased);

        u = this.getClass().getResource("sound/SoundTrack.wav");
        try {
            assert u != null;
            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(u)){
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
            }
        }
        catch (LineUnavailableException ex) {
            throw new RuntimeException();
        }catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        model.execute();
        stage.show();
    }

    @Override
    public void addObject(VisibleGameObjectViewInfo obj) {
        URL u =this.getClass().getResource("sprites/" + obj.getClassName() + ".png");
        assert u != null;
        ImageView objView = new ImageView(new Image(u.toString(),obj.getMaskWidth(),obj.getMaskHeight(),false,false));
        Platform.runLater(() -> {
        objView.setX(obj.getX()-100);
        objView.setY(obj.getY()-100);
        visibleObjectsMap.put(obj.getId(), objView);
        pane.getChildren().add(objView);
        });
    }

    @Override
    public void removeObject(int id) {
        Platform.runLater(() -> {
            if(visibleObjectsMap.get(id) == null){
                return;
            }
            visibleObjectsMap.get(id).setVisible(false);
            visibleObjectsMap.remove(id);
        });
    }

    @Override
    public void notifyCords(int id, int x,int y) {
        Platform.runLater(() -> {
            if(visibleObjectsMap.get(id) == null){
                return;
            }
            visibleObjectsMap.get(id).setX(x-100);
            visibleObjectsMap.get(id).setY(y - 100);
        });
    }

    @Override
    public void GameEnded() {
        if(clip != null){
            clip.stop();
            clip.close();
        }
        Platform.runLater(() -> stage.setScene(oldScene));
    }

    @Override
    public void notifyScore(int score) {
        scores.setText("Score: "+score);
    }

    @Override
    public void gameIsPaused() {
        stopped.setVisible(true);
        clip.stop();
    }

    @Override
    public void gameIsUnPaused() {
        stopped.setVisible(false);
        clip.start();
    }

    @Override
    public void notifyPlayerHP(int hp) {
        playerHP.setText("HP: "+hp);
    }

}
