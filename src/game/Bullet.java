package game;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Bullet extends Entity{

    private final int width = 5;
    private final int height = 3;
    private final int speed = 5;
    private int dir; // Direction the bullet moves same as the lastDirection in Player class
    private GamePanel gp;

    public Bullet(int x, int y, Color color, int direction, GamePanel gamePanel) {
        super(x,y);
        setColor(color);;
        this.dir = direction;
        gp = gamePanel;
       setSolidArea(new Rectangle(x,y, width, height));
    }

    public void update() {
        gp.colChecker.checkTileForBullets(this);
        setX(getX() + speed * dir);
        setSolidArea(new Rectangle(getX(),getY(), width, height));
        
    }

    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), width, height);
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getSpeed() {
        return speed;
    }
    public int getDir() {
        return dir;
    }
    public void setDir(int dir) {
        this.dir = dir;
    }
}
