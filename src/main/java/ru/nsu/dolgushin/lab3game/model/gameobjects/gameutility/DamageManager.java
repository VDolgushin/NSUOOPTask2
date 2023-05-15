package ru.nsu.dolgushin.lab3game.model.gameobjects.gameutility;

import ru.nsu.dolgushin.lab3game.model.gameobjects.GameObject;

public abstract class DamageManager implements IDamageManager {
    protected final int invincibilityFramesTotal;
    protected int invincibilityFramesCurrent;
    protected boolean isInvincible;
    protected int hp;
    protected final GameObject gameObject;
    public DamageManager( GameObject gameObject, int invincibilityFramesTotal, int hp){
        this.invincibilityFramesTotal = invincibilityFramesTotal;
        this.gameObject = gameObject;
        this.hp = hp;
        invincibilityFramesCurrent = 0;
        isInvincible = false;
    }
    public void getDamage(int damage){
        if(!isInvincible){
            hp -= damage;
            isInvincible = true;
            invincibilityFramesCurrent = invincibilityFramesTotal;
        }
    }
    public int getHP(){
        return hp;
    }
    public abstract void die();
    public void behaviour(){
        if(isInvincible){
            invincibilityFramesCurrent--;
            if(invincibilityFramesCurrent <= 0){
                isInvincible = false;
            }
        }
        if(hp <= 0){
            die();
        }
    }
}
