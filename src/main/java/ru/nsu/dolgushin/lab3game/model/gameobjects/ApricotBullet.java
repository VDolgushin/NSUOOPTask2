package ru.nsu.dolgushin.lab3game.model.gameobjects;

import ru.nsu.dolgushin.lab3game.model.Field;

public class ApricotBullet extends GameObject implements VisibleObject,ObjectWithBehaviour, Projectile,Ghost{
    private final int vSpeed;
    private final int hSpeed;
    public ApricotBullet(Field field, int x, int y, int width, int height,int speed,int xDest, int yDest) {
        super(field, x-width/2, y-height/2, width, height);
        double dx = xDest-x;
        double dy = yDest-y;
        if(Math.abs(dy) <= 10){
            hSpeed = (int)(speed*Math.signum(dx));
            vSpeed = 0;
            return;
        }
        double k = Math.abs(dx/dy);
        double newSpeed = Math.sqrt(Math.pow(speed,2)/((Math.pow(k,2)+1)));
        if((int)newSpeed == 0){
            newSpeed = 1;
        }
        vSpeed = (int)((int)newSpeed*Math.signum(dy));
        hSpeed = (int)((int)newSpeed*k*Math.signum(dx));
    }

    @Override
    public void behaviour() {
        field.moveMeX(this,hSpeed);
        field.moveMeY(this,vSpeed);
    }

    @Override
    public int giveDamage() {
        return 1;
    }
}
