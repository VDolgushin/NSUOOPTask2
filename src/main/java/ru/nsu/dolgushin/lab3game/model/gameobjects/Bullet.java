package ru.nsu.dolgushin.lab3game.model.gameobjects;

import ru.nsu.dolgushin.lab3game.model.Field;

public class Bullet extends GameObject implements ObjectWithCollision,VisibleObject,ObjectWithBehaviour,DamageDealer,FragileProjectile,Enemy,Ghost{
    private final int vSpeed;
    private final int hSpeed;
    public Bullet(Field field,int x, int y,  int width, int height,double alpha, int speed){
        super(field,x,y,width, height);
        vSpeed = (int)(Math.sin(alpha)*speed);
        hSpeed = (int)(Math.cos(alpha)*speed);
    }
    public void behaviour(){
        field.moveMeX(Bullet.this,hSpeed);
        field.moveMeY(Bullet.this,vSpeed);
    }
    @Override
    public int giveDamage() {
        return 1;
    }
}
