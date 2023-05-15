package ru.nsu.dolgushin.lab3game.model.modellistener;


public interface ModelListener {
    void addObject(VisibleGameObjectViewInfo obj);
    void removeObject(int id);
    void notifyCords(int id,int x,int y);
    void GameEnded();
    void notifyScore(int score);
    void gameIsPaused();
    void gameIsUnPaused();
    void notifyPlayerHP(int hp);
}
