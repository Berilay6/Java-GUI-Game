package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Player extends Entity{
    
    private GamePanel gamePanel;
    private KeyInput keyInput;
    private int lastDirection = 1; // 1 for right, -1 for left
    private List<Bullet> bullets = new ArrayList<>();
    private List<Points> points;
    private List<Enemy> enemies;

     

    public Player(GamePanel gamePanel, KeyInput keyInput, List<Points> points, List<Enemy> enemies){
        this.gamePanel = gamePanel;
        this.keyInput = keyInput;
        this.points=points;
        this.enemies=enemies;
        setDefaultValues();
    }

    public void setDefaultValues(){
        setX(20);
        setY(607);
        setSize(16);
        setSolidArea(new Rectangle(getX(), getY(),16, 16));
        setColor(Color.red);
        setVelX(5);
        setVelY(0);
        setDirection("");
        setCollisionTop(false);
        setCollisionBottom(true);
    }
    

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<Points> getPoints() {
        return points;
    }

    public void update(){
        if(keyInput.getUp()) {
            if(isCollisionBottom())
                setDirection("up");
        }
        else if(keyInput.getLeft()) {
            if(isCollisionBottom())
                setDirection("left");
            lastDirection = -1;
        }
        else if(keyInput.getRight()) {
            if(isCollisionBottom())
                setDirection("right");
            lastDirection = 1;
        }

        //CHECHING TILE COLLISION
        setCollisionOn(false);
        gamePanel.colChecker.checkTile((Entity)this);

        
        if (isCollisionBottom()) {setVelY(0); }// Yerçekimi etkisini durdur
        if(isCollisionTop()) setVelY(0);

        

            if(getDirection().equals("up")&& keyInput.getUp() && isCollisionBottom() && !isCollisionLeft() && !isCollisionRight()){

                if(getColor()==Color.magenta){
                    setVelY(-20);
                }
                else{
                    setVelY(-15);
                }

                    setY(getY() + getVelY());

                if(getVelY() != 0) {
                    setCollisionBottom(false);
                }

            }
                
            
            else if(getDirection().equals("left")&&keyInput.getLeft()&& !isCollisionLeft() && isCollisionBottom()){
                setVelX(5);
                if(getX()>0)
                    setX(getX() - getVelX());
                else   
                    setX(0);
               
            }
            else if(getDirection().equals("right")&&keyInput.getRight() && !isCollisionRight() && isCollisionBottom()){
                setVelX(5);
                if(getX()<gamePanel.getScreenWidth()-gamePanel.getOriginalTileSize())
                    setX(getX() + getVelX());
                else
                    setX(gamePanel.getScreenWidth()-gamePanel.getOriginalTileSize());
            
            }

            if(!isCollisionBottom()){
                applyGravity();
                setVelX(5);

                if(keyInput.getRight()){

                    if(getX()<gamePanel.getScreenWidth()-gamePanel.getOriginalTileSize())
                        setX(getX() + getVelX());
                    else
                        setX(gamePanel.getScreenWidth()-gamePanel.getOriginalTileSize());
                }
                else if(keyInput.getLeft()){

                     if(getX()>0)
                        setX(getX() - getVelX());
                    else   
                        setX(0);
                }

                if(isCollisionRight() || isCollisionLeft())
                    setVelX(0);

            }

             

        if(keyInput.getShoot()){
            if (getColor() != Color.MAGENTA) {
                bullets.add(new Bullet(getX() + 16 / 2, getY(), getColor(), lastDirection,gamePanel));
                keyInput.setShoot(false);
            }
        }

            Iterator<Points> pointIter= points.iterator();
            while(pointIter.hasNext()) {
            	gamePanel.colChecker.checkPointCollision(this,points);
            	Points point = pointIter.next();
            	if(point.isCollisionOn()){
                   pointIter.remove();
                    gamePanel.increaseScore();
                    break;
                }
            }
            
        
        bullets.forEach(Bullet::update);

        bullets.removeIf(bullet -> bullet.getX() > gamePanel.getScreenWidth() || bullet.getX() < 0 || bullet.isCollisionOn()); 
        
        setSolidArea(new Rectangle(getX(), getY(), 16, 16));


        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            enemy.update();
            if (getSolidArea().intersects(enemy.getSolidArea())) {
                gamePanel.startAgain();
                break;
            }

            if(!enemy.isAlive()){iterator.remove();; gamePanel.setScore(gamePanel.getScore()+10);}
        }

    }

     
        

    public Color inputColor(){
		
        if(keyInput.getBlue()){
            setColor(Color.blue);
        }
        else if(keyInput.getMagenta()){
            setColor(Color.magenta);
        }
        else if(keyInput.getRed()){
            setColor(Color.red);
        }
        return getColor();
    }

    public void draw(Graphics2D g2){
       Color color = inputColor();
        g2.setColor(color);
        g2.fillRect(getX(), getY(), gamePanel.getOriginalTileSize(), gamePanel.getOriginalTileSize());

        bullets.forEach(bullet -> bullet.draw(g2));
        
    }
}
