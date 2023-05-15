package ru.nsu.dolgushin.lab3game.model.gameobjects;

import ru.nsu.dolgushin.lab3game.model.Field;
import ru.nsu.dolgushin.lab3game.model.gameobjects.gameutility.DamageManager;

import java.awt.*;

public class Apricot extends GameObject implements ObjectThatCanFall, VisibleObject, DamageReceiver,ObjectWithBehaviour{
    private int hSpeed;
    private boolean movingLeft,movingRight,jumping;
    private final int gravity;
    private int vSpeed;
    private int reloadTimeCurrent;
    private final int reloadTimeTotal;
    private final DamageManager damageManager;
    private boolean onGround;
    public Apricot(Field field,int x,int y, int width, int height, int hp, int gravity){
        super(field,x,y,width,height);
        onGround = false;
        this.gravity = gravity;
        hSpeed = 0;
        vSpeed = 0;
        this.field = field;
        damageManager= new DamageManager(this,10,hp) {
            @Override
            public void die() {
                gameObject.getField().endGame();
            }
        };
        movingLeft=false;
        movingRight=false;
        jumping = false;
        reloadTimeCurrent = 0;
        reloadTimeTotal = 10;
        field.getModelListener().notifyPlayerHP(hp);
    }
    public void behaviour(){
            if(movingLeft){
                moveLeft();
            }
            if(movingRight){
                moveRight();
            }
            if(jumping){
                jump();
            }
            vSpeed += gravity;
            field.moveMeX(Apricot.this,hSpeed);
            field.moveMeY(Apricot.this,vSpeed);
            if(hSpeed > 0){
                hSpeed--;
            }
            if(hSpeed < 0){
                hSpeed++;
            }
            if(onGround){
                vSpeed = 0;
            }
            damageManager.behaviour();
            if(reloadTimeCurrent >= reloadTimeTotal){
                new ApricotBullet(field,field.getHitbox(this).getCenter().getX(),field.getHitbox(this).getCenter().getY(),100,40,30,MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y);
                reloadTimeCurrent = 0;
            }
            reloadTimeCurrent++;
    }
    public void moveRightPressed(){
        movingRight = true;
    }
    public void moveLeftPressed(){
        movingLeft = true;
    }
    public void jumpPressed(){
        jumping = true;
    }
    public void moveRightReleased(){
        movingRight = false;
    }
    public void moveLeftReleased(){
        movingLeft = false;
    }
    public void jumpReleased(){
        jumping = false;
    }
    public void moveRight(){
        hSpeed = 8;
    }
    public void moveLeft(){
        hSpeed = -8;
    }
    private void jump(){
        if(onGround){
            vSpeed = -25;
        }
    }
    public void setOnGround(boolean onGround){
        this.onGround = onGround;
    }
    public boolean isOnGround(boolean onGround){
        return onGround;
    }
    @Override
    public void getDamage(int damage) {
        damageManager.getDamage(damage);
        field.getModelListener().notifyPlayerHP(damageManager.getHP());
    }
    public void pausePressed(){
        field.pauseGame();
    }
}
