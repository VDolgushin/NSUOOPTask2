package ru.nsu.dolgushin.lab3game.controller;

import javafx.scene.input.KeyCode;
import ru.nsu.dolgushin.lab3game.model.Model;

public class JavaFXController{
    Model model;
    public JavaFXController(Model model){
        this.model = model;
    }

    public void keyPressed(javafx.scene.input.KeyEvent e) {
        KeyCode kc = e.getCode();
        switch (kc) {
            case LEFT -> model.moveLeftPressed();
            case RIGHT -> model.moveRightPressed();
            case UP -> model.jumpPressed();
            case P -> model.pausePressed();
        }
    }
    public void keyReleased(javafx.scene.input.KeyEvent e) {
        KeyCode kc = e.getCode();
        switch (kc) {
            case LEFT -> model.moveLeftReleased();
            case RIGHT -> model.moveRightReleased();
            case UP -> model.jumpReleased();
        }
    }
}
