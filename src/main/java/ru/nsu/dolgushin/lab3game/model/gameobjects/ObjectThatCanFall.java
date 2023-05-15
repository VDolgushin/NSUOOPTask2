package ru.nsu.dolgushin.lab3game.model.gameobjects;

public interface ObjectThatCanFall extends ObjectWithCollision {

    void setOnGround(boolean onGround);
    boolean isOnGround(boolean onGround);
}
