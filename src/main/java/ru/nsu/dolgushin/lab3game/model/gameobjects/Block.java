package ru.nsu.dolgushin.lab3game.model.gameobjects;

import ru.nsu.dolgushin.lab3game.model.Field;

public class Block extends GameObject implements ObjectWithCollision,VisibleObject{
    public Block(Field field,int x, int y,int width, int height){
        super(field,x,y,width,height);
    }
}
