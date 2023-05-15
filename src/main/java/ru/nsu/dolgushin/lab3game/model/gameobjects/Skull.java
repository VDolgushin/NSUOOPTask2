package ru.nsu.dolgushin.lab3game.model.gameobjects;

import ru.nsu.dolgushin.lab3game.model.Field;
import ru.nsu.dolgushin.lab3game.model.gameobjects.gameutility.DamageManager;
import ru.nsu.dolgushin.lab3game.model.gameobjects.gameutility.IDamageManager;
import ru.nsu.dolgushin.lab3game.model.gameobjects.gameutility.Point;

import java.util.Objects;

public class Skull extends GameObject implements  VisibleObject,ObjectWithBehaviour,Ghost,Enemy,DamageReceiver,DamageDealer{//,{
    private final IDamageManager damageManager;
    private final Apricot apricot;
    private int changeTimeTotal;
    private int changeTimeCurrent;
    private int reloadTimeTotal;
    private int reloadTimeCurrent;
    private int vSpeed;
    private int hSpeed;
    private final int startHP;
    public Skull(Field field, int x, int y, int width, int height, Apricot apricot, ScoreManager scoreManager, int hp) {
        super(field, x, y, width, height);
        this.apricot = apricot;
        this.startHP = hp;
        damageManager= new DamageManager(this,20,hp){
            @Override
            public void die(){
                gameObject.getField().removeObjectFromField(gameObject);
                scoreManager.addPoints(startHP);
            }
        };
        changeTimeTotal = 10+(int)(Math.random()*40);
        changeTimeCurrent = 0;
        reloadTimeTotal = 40+(int)(Math.random()*30);
        reloadTimeCurrent = 0;
        changeSpeed();
    }

    private void changeSpeed(){
        Point c;
        c = field.getHitbox(Objects.requireNonNullElse(apricot, this)).getCenter();
        Point thisC = field.getHitbox(this).getCenter();
        double dx = c.getX()-thisC.getX() + Math.signum(Math.random()-0.5)*(0.3+Math.random())*400;
        double dy = c.getY()-thisC.getY()+ Math.signum(Math.random()-0.5)*(0.3+Math.random())*400;
        int speed = (int)(Math.random()*1+1);
        if(Math.abs(dy) <= 20){
            hSpeed = (int)(speed*Math.signum(dx));
            return;
        }
        double k = Math.abs(dx/dy);
        vSpeed = (int)(speed*Math.signum(dy));
        hSpeed = (int)(speed*k*Math.signum(dx));
    }

    public int giveDamage() {
        return 1;
    }

    public void getDamage(int damage) {
        damageManager.getDamage(damage);
    }
    private double bulletAlpha(){
        Point c = field.getHitbox(apricot).getCenter();
        Point thisC = field.getHitbox(this).getCenter();
        double dx = thisC.getX()-c.getX();
        double dy = thisC.getY()-c.getY();
        if(dx == 0){
            if(dy < 0){
                return 3.14*3/2;
            }
            else {
                return 3.14/2;
            }
        }
        if(dx < 0){
            return Math.atan(dy/dx);
        }
        else {
            return 3.14+Math.atan(dy/dx);
        }
    }
    @Override
    public void behaviour() {
        if(changeTimeCurrent >= changeTimeTotal){
            changeSpeed();
            changeTimeTotal = 20+(int)(Math.random()*80);
            changeTimeCurrent = 0;
        }
        if(reloadTimeCurrent >= reloadTimeTotal){
            new Bullet(field,field.getHitbox(this).getCenter().getX(),field.getHitbox(this).getCenter().getY(),100,40, bulletAlpha(),3+(int)(Math.random()*4));
            reloadTimeTotal = 100+(int)(Math.random()*200);
            reloadTimeCurrent = 0;
        }
        field.moveMeX(this,hSpeed);
        field.moveMeY(this,vSpeed);
        damageManager.behaviour();
        changeTimeCurrent++;
        reloadTimeCurrent++;
    }
}
