package ru.nsu.dolgushin.lab3game.model.gameobjects.gameutility;

public class Hitbox {
    private Point NorthWest;
    private Point NorthEast;
    private Point SouthWest;
    private Point SouthEast;
    private int height;
    private int width;
    public Hitbox(int x,int y, int height, int width){
        NorthWest = new Point(x,y);
        NorthEast = new Point(x+width,y);
        SouthWest = new Point(x,y+height);
        SouthEast = new Point(x+width,y+height);
        this.height = height;
        this.width = width;
    }
    public Point getNorthEast() {
        return NorthEast;
    }
    public Point getNorthWest() {
        return NorthWest;
    }
    public Point getSouthWest() {
        return SouthWest;
    }
    public Point getSouthEast() {
        return SouthEast;
    }
    public Point getCenter(){
        return new Point(NorthWest.getX()+(SouthEast.getX()-NorthWest.getX())/2, NorthWest.getY()+(SouthEast.getY()-NorthWest.getY())/2);
    }
    public void setHitbox(int x, int y){
        NorthWest = new Point(x,y);
        NorthEast = new Point(x+width,y);
        SouthWest = new Point(x,y+height);
        SouthEast = new Point(x+width,y+height);
    }
    public void moveHitbox(int x, int y){
        NorthWest = new Point(NorthWest.getX()+x,NorthWest.getY()+y);
        NorthEast = new Point(NorthWest.getX()+width,NorthWest.getY());
        SouthWest = new Point(NorthWest.getX(),NorthWest.getY()+height);
        SouthEast = new Point(NorthWest.getX()+width,NorthWest.getY()+height);
    }
}
