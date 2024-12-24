package game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy extends Entity {

    private int life;
    private boolean isAlive;
    private int dir; //-1 left 1 right
    private boolean isKeyEnemy;
    private GamePanel gp;

    public Enemy(int x, int y, Color color, int dir, int velX, boolean keyEnemy, GamePanel gp) {
        super(x, y);
        setSize(24);
        setColor(color);
        this.isAlive = true;
        this.dir = dir;
       setVelX(velX);
        this.gp = gp;
       setSolidArea(new Rectangle(x,y,getSize(), getSize()));
       this.isKeyEnemy=keyEnemy;

        if(color == Color.RED || color == Color.BLUE) {
            this.life = 3;
        }
        else {
            this.life = 1; //for orange but it will never decrease
        }

        if(dir==-1){
           setDirection("left");
        }
        else{
            setDirection("right");
        }
    }

   
    public int getLife() {
        return life;
    }
    public void setLife(int life) {
        this.life = life;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    public int getDir() {
        return dir;
    }
    public void setDir(int dir) {
        this.dir = dir;
    }
    
    public boolean getIsKeyEnemy() {
    	return isKeyEnemy;
    }
   
   public void setIsKeyEnemy(boolean keyEnemy) {
	   isKeyEnemy=keyEnemy; 
   }

    public void update() {

        gp.colChecker.checkTileForEnemies(this);

        if(!isCollisionLeft() && dir==-1){
            setX(getX()-getVelX());
        }
        else if(!isCollisionRight() && dir==1){
            setX(getX()+getVelX());
        }
        if(isCollisionLeft() || isCollisionRight()){
            dir*=-1;
            setCollisionLeft(false);
            setCollisionRight(false);
        }

        if(!isCollisionBottom()){
            applyGravity();
        }
        else{
            setVelY(0);
        }

        setSolidArea(new Rectangle(getX(),getY(),getSize(),getSize()));

        if(life==0){
            isAlive=false;
        }
    }

    public void draw(Graphics g){
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getSize(), getSize());
    }

}
