package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Key extends Entity{
    

    public Key(int x, int y, int size) {
        super(x,y);
        setSize(size);
        setColor( Color.GREEN);
    }

    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getX(), getY(), getSize(), getSize());
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getSize(), getSize());
    }
}
