package ru.nsu.dolgushin.lab3game.model.gameobjects;

public interface DamageReceiver extends ObjectWithCollision {
    void getDamage(int damage);
}
