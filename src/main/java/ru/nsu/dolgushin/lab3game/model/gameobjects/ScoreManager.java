package ru.nsu.dolgushin.lab3game.model.gameobjects;

import ru.nsu.dolgushin.lab3game.model.Field;
import ru.nsu.dolgushin.lab3game.model.modellistener.ModelListener;

public class ScoreManager extends GameObject{
    private int scores;
    private final ModelListener ls;
    public ScoreManager(Field field, int x, int y, int width, int height, ModelListener ls) {
        super(field, x, y, width, height);
        scores = 0;
        this.ls = ls;
    }

    public int getScores() {
        return scores;
    }

    public void addPoints(int p){
        scores+=p;
        ls.notifyScore(scores);
    }
}
