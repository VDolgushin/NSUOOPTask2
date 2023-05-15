package ru.nsu.dolgushin.lab3game.model;

import ru.nsu.dolgushin.lab3game.model.gameobjects.*;
import ru.nsu.dolgushin.lab3game.model.gameobjects.gameutility.Hitbox;
import ru.nsu.dolgushin.lab3game.model.gameobjects.gameutility.Point;
import ru.nsu.dolgushin.lab3game.model.modellistener.ModelListener;
import ru.nsu.dolgushin.lab3game.model.modellistener.VisibleGameObjectViewInfo;

import java.util.*;

public class Field {
    private final ModelListener ls;
    private final Map<Point, GameObject> collisionLayer= new TreeMap<>();
    private final Map<IGameObject,Hitbox> objectCords  = new HashMap<>();
    private final List<ObjectWithBehaviour> objectsWithBehaviour = new ArrayList<>();
    private final Model model;
    private boolean isGameEnded;
    public Field(Model model,ModelListener ls){
        this.ls = ls;
        this.model = model;
        isGameEnded = false;
    }
    public void addObjectToField(IGameObject owc,int x, int y){
        if (VisibleObject.class.isAssignableFrom(owc.getClass())) {
            ls.addObject(new VisibleGameObjectViewInfo(owc,x,y));
            if(!ObjectWithCollision.class.isAssignableFrom(owc.getClass())){
                Hitbox hb = new Hitbox(x,y, owc.getHeight(), owc.getWidth());
                objectCords.put(owc,hb);
            }
        }
        if(ObjectWithCollision.class.isAssignableFrom(owc.getClass())){
            addObjectWithCollision( (ObjectWithCollision)owc,x,y);
        }
        if(ObjectWithBehaviour.class.isAssignableFrom(owc.getClass())){
            objectsWithBehaviour.add((ObjectWithBehaviour)owc);
            if(!VisibleObject.class.isAssignableFrom(owc.getClass())){
                Hitbox hb = new Hitbox(x,y, owc.getHeight(), owc.getWidth());
                objectCords.put(owc,hb);
            }
        }
    }
    public void removeObjectFromField(IGameObject owc){
        if(VisibleObject.class.isAssignableFrom(owc.getClass())){
            ls.removeObject(owc.getId());
            if(!ObjectWithCollision.class.isAssignableFrom(owc.getClass())){
                objectCords.remove(owc);
            }
        }
        if(ObjectWithCollision.class.isAssignableFrom(owc.getClass())){
            removeObjectFromCollisionLayer(objectCords.get(owc),(ObjectWithCollision) owc);
        }
        if(ObjectWithBehaviour.class.isAssignableFrom(owc.getClass())){
            owc.setDead();
        }
    }
    private void addObjectWithCollision(ObjectWithCollision owc,int x, int y){
        Hitbox hb = new Hitbox(x,y, owc.getHeight(), owc.getWidth());
        collisionLayer.put(hb.getNorthEast(),(GameObject) owc);
        collisionLayer.put(hb.getNorthWest(),(GameObject) owc );
        collisionLayer.put(hb.getSouthEast(),(GameObject) owc);
        collisionLayer.put(hb.getSouthWest(),(GameObject) owc);
        objectCords.put(owc,hb);
    }
    public Hitbox getHitbox(IGameObject o){
        return objectCords.get(o);
    }
    private void removeObjectFromCollisionLayer(Hitbox hb,ObjectWithCollision owc){
        collisionLayer.remove(hb.getNorthWest());
        collisionLayer.remove(hb.getSouthWest());
        collisionLayer.remove(hb.getNorthEast());
        collisionLayer.remove(hb.getSouthEast());
        objectCords.remove(owc);
    }
    private void dealCollisionDamage(DamageReceiver receiver, DamageDealer dealer){
        receiver.getDamage(dealer.giveDamage());
    }
    private int checkAndDealCollisionDamage(GameObject a, GameObject b){
        if(Enemy.class.isAssignableFrom(a.getClass()) && !Enemy.class.isAssignableFrom(b.getClass()) || !Enemy.class.isAssignableFrom(a.getClass()) && Enemy.class.isAssignableFrom(b.getClass())) {
            if (DamageReceiver.class.isAssignableFrom(a.getClass()) && DamageDealer.class.isAssignableFrom(b.getClass())) {
                dealCollisionDamage((DamageReceiver) a, (DamageDealer) b);
                removeObjectFromField(b);
                return 1;
            }
            if (DamageDealer.class.isAssignableFrom(a.getClass()) && DamageReceiver.class.isAssignableFrom(b.getClass())) {
                dealCollisionDamage((DamageReceiver) b, (DamageDealer) a);
                return -1;
            }
        }
        if(!(Enemy.class.isAssignableFrom(a.getClass()) && Enemy.class.isAssignableFrom(b.getClass()))) {
            if (DamageDealer.class.isAssignableFrom(a.getClass()) && FragileProjectile.class.isAssignableFrom(a.getClass()) && !Projectile.class.isAssignableFrom(b.getClass())) {
                return -1;
            }
            if (DamageDealer.class.isAssignableFrom(b.getClass()) && FragileProjectile.class.isAssignableFrom(b.getClass()) && !Projectile.class.isAssignableFrom(a.getClass())) {
                return -2;
            }
        }
        return 0;
    }

    public int checkCollisionsX(ObjectWithCollision owc, Hitbox hb, int x, int xNear){
        for (Point p : collisionLayer.keySet()){
            if(p.getX() >= hb.getNorthWest().getX()+x && p.getX() <= hb.getSouthEast().getX()+x && p.getY() >= hb.getNorthWest().getY() && p.getY() <= hb.getSouthEast().getY()){
                int r =checkAndDealCollisionDamage((GameObject) owc, collisionLayer.get(p));
                if(r == 1){
                    return checkCollisionsX(owc,hb, x, xNear);
                }
                else if(r == -1){
                    addObjectWithCollision(owc,0,0);
                    removeObjectFromField(owc);
                    return -1;
                }
                else if(r == -2){
                    removeObjectFromField(collisionLayer.get(p));
                    return checkCollisionsX(owc,hb, x, xNear);
                }
                if(Ghost.class.isAssignableFrom(collisionLayer.get(p).getClass()) || Ghost.class.isAssignableFrom(owc.getClass())){
                    continue;
                }
                addObjectWithCollision(owc, hb.getNorthWest().getX() + p.getX()-xNear, hb.getNorthWest().getY());
                if (VisibleObject.class.isAssignableFrom(owc.getClass())) {
                    ls.notifyCords(owc.getId(), hb.getNorthWest().getX()+ p.getX()-xNear, hb.getNorthWest().getY());
                }
                return -1;
            }
        }
        return 1;
    }
    public int checkCollisionsY(ObjectWithCollision owc, Hitbox hb, int y, int yNear){
        for (Point p : collisionLayer.keySet()){
            if(p.getY() >= hb.getNorthWest().getY()+y && p.getY() <= hb.getSouthEast().getY()+y && p.getX() >= hb.getNorthWest().getX() && p.getX() <= hb.getSouthEast().getX()){
                int r= checkAndDealCollisionDamage((GameObject) owc, collisionLayer.get(p));
                if(r == 1){
                    return checkCollisionsY(owc,hb, y, yNear);
                }
                else if(r == -1){
                    addObjectWithCollision(owc,0,0);
                    removeObjectFromField(owc);
                    return -1;
                }
                if(Ghost.class.isAssignableFrom(collisionLayer.get(p).getClass()) || Ghost.class.isAssignableFrom(owc.getClass())){
                    continue;
                }
                addObjectWithCollision(owc, hb.getNorthWest().getX(), hb.getNorthWest().getY() + p.getY() - yNear);
                if (VisibleObject.class.isAssignableFrom(owc.getClass())) {
                    ls.notifyCords(owc.getId(), hb.getNorthWest().getX(), hb.getNorthWest().getY() + p.getY() - yNear);
                }
                return -1;
            }
        }
        return 1;
    }
    public int moveMeX(ObjectWithCollision owc, int x){
        if(x == 0||owc.isDead()){
            return 0;
        }
        Hitbox hb = objectCords.get(owc);
        int xNear;
        if(x > 0){
            xNear = hb.getNorthEast().getX()+1;
        } else{
            xNear = hb.getNorthWest().getX()-1;
        }
        removeObjectFromCollisionLayer(hb,owc);
        if(checkCollisionsX(owc,hb,x,xNear) == -1){
            return -1;
        }
        addObjectWithCollision(owc,hb.getNorthWest().getX()+x, hb.getNorthWest().getY());
        if (VisibleObject.class.isAssignableFrom(owc.getClass())) {
            ls.notifyCords(owc.getId(),hb.getNorthWest().getX()+x, hb.getNorthWest().getY());
        }
        return 1;
    }
    public int moveY(ObjectWithCollision owc, int y){
        if(y == 0||owc.isDead()){
            return 0;
        }
        Hitbox hb = objectCords.get(owc);
        int yNear;
        if(y > 0){
            yNear = hb.getSouthWest().getY()+1;
        } else{
            yNear = hb.getNorthWest().getY()-1;
        }
        removeObjectFromCollisionLayer(hb,owc);
        if(checkCollisionsY(owc,hb,y,yNear) == -1){
            return -1;
        }
        addObjectWithCollision(owc,hb.getNorthWest().getX(), hb.getNorthWest().getY()+y);
        if (VisibleObject.class.isAssignableFrom(owc.getClass())) {
            ls.notifyCords(owc.getId(),hb.getNorthWest().getX(), hb.getNorthWest().getY()+y);
        }
        return 1;
    }
    public int moveMeX(IGameObject owc, int x){
        objectCords.get(owc).moveHitbox(x,0);
        ls.notifyCords(owc.getId(),objectCords.get(owc).getNorthWest().getX(),objectCords.get(owc).getNorthWest().getY());
        return 1;
    }
    public int moveMeY(IGameObject owc, int y){
        objectCords.get(owc).moveHitbox(0,y);
        ls.notifyCords(owc.getId(),objectCords.get(owc).getNorthWest().getX(),objectCords.get(owc).getNorthWest().getY());
        return 1;
    }
    public int moveMeY(ObjectWithCollision owc, int y){
        if (ObjectThatCanFall.class.isAssignableFrom(owc.getClass())) {
            return moveYFalling((GameObject &ObjectThatCanFall) owc,y);
        }
        return moveY(owc,y);
    }
    public int moveYFalling(ObjectThatCanFall owc, int y){
        int r = moveY(owc,y);
        if (r == -1){
            owc.setOnGround(true);
            return r;
        }
        owc.setOnGround(false);
        return r;
    }

    public void tick(){
        ArrayList<ObjectWithBehaviour> a = new ArrayList<>(objectsWithBehaviour);
        for(ObjectWithBehaviour owb : a){
            if(!owb.isDead()){
                owb.behaviour();
            }
        }
        if(isGameEnded){
           return;
        }
        for(int i = 0; i < objectsWithBehaviour.size(); i++){
            if(objectsWithBehaviour.get(i).isDead()){
                objectsWithBehaviour.remove(i);
                i--;
            }
            if(i <0){
                i++;
            }
            if(Math.abs(objectCords.get(objectsWithBehaviour.get(i)).getCenter().getX()) > 3000 || Math.abs(objectCords.get(objectsWithBehaviour.get(i)).getCenter().getY()) > 3000 ){
                removeObjectFromField(objectsWithBehaviour.get(i));
            }
            if(objectsWithBehaviour.get(i).isDead()){
                objectsWithBehaviour.remove(i);
                i--;
            }
            if(i <0){
                i++;
            }
        }
    }
    public void endGame(){
        isGameEnded = true;
        for(ObjectWithBehaviour owb :objectsWithBehaviour){
            owb.setDead();
        }
        model.endGame();
    }
    public Set<IGameObject> getMaterialObjectsList(){
        return objectCords.keySet();
    }
    public void pauseGame(){
        model.pauseGame();
    }
    public ModelListener getModelListener(){
        return ls;
    }
}
