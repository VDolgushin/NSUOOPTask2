package ru.nsu.dolgushin.lab3game.model.gameobjects;

import ru.nsu.dolgushin.lab3game.model.Field;
import ru.nsu.dolgushin.lab3game.model.gameobjects.gameutility.DamageManager;
import ru.nsu.dolgushin.lab3game.model.gameobjects.gameutility.IDamageManager;

public class Skull2 extends GameObject implements  VisibleObject,ObjectWithBehaviour,Ghost,Enemy,DamageReceiver,DamageDealer{//,{
    private final IDamageManager damageManager;
    private final int hSpeed;
    private final int startHP;
    public Skull2(Field field, int x, int y, int width, int height, ScoreManager scoreManager, int hp) {
        super(field, x, y, width, height);
        this.startHP = hp;
        damageManager= new DamageManager(this,2,hp){
            @Override
            public void die(){
                gameObject.getField().removeObjectFromField(gameObject);
                scoreManager.addPoints(startHP);
            }
        };
        hSpeed = -(3+(int)(Math.random()*3));
    }
    public int giveDamage() {
        return 3;
    }
    public void getDamage(int damage) {
        damageManager.getDamage(damage);
    }
    @Override
    public void behaviour() {
        field.moveMeX(this,hSpeed);
        damageManager.behaviour();
    }
}
