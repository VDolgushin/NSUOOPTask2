package ru.nsu.dolgushin.lab3game.model.gameobjects.gameutility;

import java.util.Objects;

public class Point implements Comparable<Point>{
    private int x;
    private int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public int compareTo(Point p) {
        if(x == p.x && y == p.y){
            return 0;
        }
        if(x*x+y*y > p.x*p.x+p.y*p.y ){
            return 1;
        }
        if(x*x+y*y == p.x*p.x+p.y*p.y){
            if(x > p.x ){
                return 1;
            }
            else if (x < p.x){
                return -1;
            }
            else if(y >p.y){
                return 1;
            }
            else{
                return -1;
            }
        }
        return -1;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
