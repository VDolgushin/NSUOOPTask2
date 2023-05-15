package ru.nsu.dolgushin.lab3game.model.modellistener;

import ru.nsu.dolgushin.lab3game.model.gameobjects.IGameObject;

public class VisibleGameObjectViewInfo {
    private final int id;
    private final String className;
    private final int maskHeight;
    private final int maskWidth;
    private final int x;
    private final int y;
    public VisibleGameObjectViewInfo(IGameObject obj, int x, int y){
        this.x = x;
        this.y = y;
        id = obj.getId();
        className = obj.getClass().getSimpleName();
        maskHeight = obj.getHeight();
        maskWidth = obj.getWidth();
    }
    public int getId(){
        return id;
    }

    public String getClassName() {
        return className;
    }

    public int getMaskHeight() {
        return maskHeight;
    }

    public int getMaskWidth() {
        return maskWidth;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
