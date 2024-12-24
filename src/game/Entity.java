package game;

import java.awt.Color;
import java.awt.Rectangle;

public class Entity{
    private int x, y;
    private int size;
    private Color color;
    private int velX=0; 
    private int velY=0;
    private String direction;
    private Rectangle solidArea;
    private boolean collisionOn = false;
    private boolean collisionTop, collisionBottom, collisionLeft, collisionRight;
    private int gravity=1;

    public Entity(){}

    public Entity(int x, int y){
        this.x=x;
        this.y=y;
    }

    public void setX(int x) {
        this.x = x;
       
    }
    public void setY(int y) {
        this.y = y;
        
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }

    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public Rectangle getSolidArea() {
        return solidArea;
    }
    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }
    public boolean isCollisionOn() {
        return collisionOn;
    }
    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }
    public boolean isCollisionTop() {
        return collisionTop;
    }
    public void setCollisionTop(boolean collisionTop) {
        this.collisionTop = collisionTop;
    }
    public boolean isCollisionBottom() {
        return collisionBottom;
    }
    public void setCollisionBottom(boolean collisionBottom) {
        this.collisionBottom = collisionBottom;
    }
    public boolean isCollisionLeft() {
        return collisionLeft;
    }
    public void setCollisionLeft(boolean collisionSide) {
        this.collisionLeft = collisionSide;
    }
    public boolean isCollisionRight() {
        return collisionRight;
    }
    public void setCollisionRight(boolean CollisionRight) {
        this.collisionRight = CollisionRight;
    }
    public float getGravity() {
        return gravity;
    }
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
    public int getVelX() {
        return velX;
    }
    public int getVelY() {
        return velY;
    }
    public void setVelX(int velX) {
        this.velX = velX;
    }
    public void setVelY(int velY) {
        this.velY = velY;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    
    public void applyGravity(){
        velY+=gravity;
        y+=velY;
    
    }
    
}
