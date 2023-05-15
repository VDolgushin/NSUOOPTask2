package ru.nsu.dolgushin.lab3game.model.gameobjects;

import ru.nsu.dolgushin.lab3game.model.Field;

public interface IGameObject {
    int getHeight();
    int getWidth();
    int getId();
    Field getField();
    void setDead();
    boolean isDead();
}
