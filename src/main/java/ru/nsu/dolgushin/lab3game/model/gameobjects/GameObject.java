package ru.nsu.dolgushin.lab3game.model.gameobjects;

import ru.nsu.dolgushin.lab3game.model.Field;

public abstract class GameObject implements IGameObject{
    static int IDCount = 0;
    protected final int ID;
    protected int height;
    protected int width;
    protected Field field;
    protected boolean isDead;
    @Override
    public int getHeight() {
        return height;
    }
    @Override
    public int getWidth() {
        return width;
    }
    @Override
    public int getId(){return ID;}
    @Override
    public void setDead(){isDead = true;}
    @Override
    public boolean isDead(){return isDead;}
    public GameObject(Field field, int x, int y, int width, int height){
        this.height = height;
        this.width = width;
        this.field = field;
        ID = IDCount;
        IDCount++;
        isDead = false;
        field.addObjectToField( this, x,y);
    }
    @Override
    public Field getField(){
        return field;
    }

}
