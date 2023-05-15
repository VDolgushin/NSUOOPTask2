package ru.nsu.dolgushin.lab3game.controller;

import javafx.scene.input.KeyCode;
import ru.nsu.dolgushin.lab3game.model.Model;

import java.awt.event.*;

public class SimpleController implements KeyListener {
    Model model;
    public SimpleController(Model model){
        this.model = model;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> model.moveLeftPressed();
            case KeyEvent.VK_RIGHT -> model.moveRightPressed();
            case KeyEvent.VK_UP -> model.jumpPressed();
            case KeyEvent.VK_P -> model.pausePressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> model.moveLeftReleased();
            case KeyEvent.VK_RIGHT -> model.moveRightReleased();
            case KeyEvent.VK_UP -> model.jumpReleased();
        }
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
