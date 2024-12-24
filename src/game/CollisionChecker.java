package game;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class CollisionChecker {
    
    private GamePanel gp;
    private int chLeftX, chRightX, chTopY, chBottomY;
    private int chLeftCol, chRightCol, chTopRow, chBottomRow;
    private Rectangle teleport;
    private Rectangle finish;
    private List<Rectangle> blueWalls = new ArrayList<>();
    private List<Rectangle> redWalls = new ArrayList<>();

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
        teleport = new Rectangle(0,96,gp.getTileSize()+5,gp.getTileSize());
        finish = new Rectangle(432,144,gp.getTileSize(),gp.getTileSize());
        addWalls();
    }

    public void addWalls(){
        blueWalls.add(new Rectangle(189,285,51,24));
        blueWalls.add(new Rectangle(717,189, 102, 24));
        blueWalls.add(new Rectangle(237, 429, 102, 24));
        blueWalls.add(new Rectangle(525, 525, 198, 24));

        redWalls.add(new Rectangle(334,285,51,24));
        redWalls.add(new Rectangle(573,429,102,24));
        redWalls.add(new Rectangle(189,525,198,24));
    }

    public void assignValues(Entity ch) {
        chLeftX = ch.getX();
        chRightX = ch.getX() + ch.getSolidArea().width-1;
        chTopY = ch.getY() ;
        chBottomY = ch.getY() + ch.getSolidArea().height-1;

        chLeftCol = chLeftX / gp.getTileSize();
        chRightCol = chRightX / gp.getTileSize();
        chTopRow = chTopY / gp.getTileSize();
        chBottomRow = chBottomY / gp.getTileSize();
    }


    public void checkTile(Entity ch) {
        
        assignValues(ch);
    
        switch (ch.getDirection()) {
            case "up":
                collisionTop(ch);
                break;
            

            case "left":
                collisionLeft(ch);
                break;

            case "right":
                collisionRight(ch);
                break;
        }

        collisionBottom(ch);

        
        //wild card: teleporting
         if(ch.getSolidArea().intersects(teleport)){
            ch.setX(7*48+1);
            ch.setY(4*48-17);
        }

        //wild card: key
        if(ch.getSolidArea().intersects(gp.getKey().getBounds())){

            gp.getEnemies().removeAll(gp.getKeyEnemies());
            gp.setTouchedKey(true);
           
        }

        //finish
        if(ch.getSolidArea().intersects(finish)){
            gp.pauseGame();
            gp.getKeyInput().setPause(true);
            gp.showPopup(gp.getPopupDialog());
        }


        for(Rectangle wall : blueWalls){
            if(ch.getSolidArea().intersects(wall) && (ch.getColor()==Color.red || ch.getColor()==Color.magenta)){
                gp.startAgain();
            }
        }

        for(Rectangle wall : redWalls){
            if(ch.getSolidArea().intersects(wall)&& (ch.getColor()==Color.blue || ch.getColor()==Color.magenta)){
                gp.startAgain();
            }
        }

    }

    public void checkTileForBullets(Bullet bullet){

        assignValues(bullet);

        switch (bullet.getDir()) {
            case 1:
               collisionRight(bullet);
                break;
            
            case -1:
               collisionLeft(bullet);
                break;
        }

        List<Enemy> en =  gp.getEnemies();   //enemy-bullet collision
        for(int i=0; i<en.size(); i++){

                if(bullet.getSolidArea().intersects(en.get(i).getSolidArea())){
                    if(bullet.getColor() != en.get(i).getColor() && en.get(i).getColor()!= Color.orange){
                        bullet.setCollisionOn(true);
                        int life= en.get(i).getLife();
                        en.get(i).setLife(life-1);
                    }
                    else if((bullet.getColor() == en.get(i).getColor())&& (en.get(i).getColor()!= Color.orange)) {
                        bullet.setCollisionOn(true);
                        int life=en.get(i).getLife();
                        en.get(i).setLife(life+1);
                    }
                }

        }
    }

    public void checkPointCollision(Player ch, List<Points> points) {
        Rectangle playerB = ch.getSolidArea();
            for (Points point : points) {
                if (playerB.intersects(point.getBounds())) {
                    point.setCollisionOn(true);
                    break; 
                }
            }
    }

    public void checkTileForEnemies(Enemy e){

        assignValues(e);

        switch (e.getDir()) {
            case 1:
                collisionRight(e);
            break;

            case -1:
                collisionLeft(e);
            break;
        }

            collisionBottom(e);

    }

    public void collisionRight(Entity ch){
        int tileNum1,tileNum2;
        chRightCol = (chRightX + ch.getVelX()) / gp.getTileSize();
                tileNum1 = gp.getTileManager().getMapTileNum()[chRightCol][chTopRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[chRightCol][chBottomRow];
                if (gp.getTileManager().getTile()[tileNum1].isCollision() || gp.getTileManager().getTile()[tileNum2].isCollision() ||ch.getX()>gp.getScreenWidth()-gp.getOriginalTileSize()) {
                    ch.setCollisionOn(true);
                    ch.setCollisionRight(true);
                    if(gp.getTileManager().getMapTileNum()[chLeftCol][chBottomRow]!=5) ch.setX(ch.getX()-1);
                }
                else{
                    ch.setCollisionOn(false);
                    ch.setCollisionRight(false);
                } 
    }

    public void collisionLeft(Entity ch){
        int tileNum1,tileNum2;
        chLeftCol = (chLeftX - ch.getVelX()) / gp.getTileSize();
                tileNum1 = gp.getTileManager().getMapTileNum()[chLeftCol][chTopRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[chLeftCol][chBottomRow];
                if (gp.getTileManager().getTile()[tileNum1].isCollision() || gp.getTileManager().getTile()[tileNum2].isCollision() || ch.getX()<0) {
                    ch.setCollisionOn(true);
                    ch.setCollisionLeft(true);
                    if(gp.getTileManager().getMapTileNum()[chLeftCol][chBottomRow]!=4) ch.setX(ch.getX()+1);
                }
                else{
                    ch.setCollisionOn(false);
                    ch.setCollisionLeft(false);
                } 
    }

    public void collisionTop(Entity ch){
        int tileNum1,tileNum2;

        chTopRow = ((chTopY - ch.getVelX()) / gp.getTileSize());
                tileNum1 = gp.getTileManager().getMapTileNum()[chLeftCol][chTopRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[chRightCol][chTopRow];


                if (gp.getTileManager().getTile()[tileNum1].isCollision() || gp.getTileManager().getTile()[tileNum2].isCollision()) {
                    ch.setCollisionOn(true);
                    ch.setCollisionTop(true);
            
                 }
                else if(gp.getTileManager().getTile()[tileNum1].isCollision() == false && gp.getTileManager().getTile()[tileNum2].isCollision() && chLeftX % 20 != 0) {
                    ch.setCollisionOn(true);
                    ch.setCollisionTop(true);
            
                }
                 else if(gp.getTileManager().getTile()[tileNum1].isCollision() && gp.getTileManager().getTile()[tileNum2].isCollision() == false && chLeftX % 20 != 0) {
                    ch.setCollisionOn(true);
                    ch.setCollisionTop(true);
                }
                else{
                    ch.setCollisionOn(false);
                    ch.setCollisionTop(false);
                } 
    }

    public void collisionBottom(Entity ch){
        int tileNum1,tileNum2;

        chBottomRow = ((chBottomY + ch.getVelX()) / gp.getTileSize());
        tileNum1 = gp.getTileManager().getMapTileNum()[chLeftCol][chBottomRow];
        tileNum2 = gp.getTileManager().getMapTileNum()[chRightCol][chBottomRow];
        if (gp.getTileManager().getTile()[tileNum1].isCollision() || gp.getTileManager().getTile()[tileNum2].isCollision()) {
            ch.setCollisionOn(true);
            ch.setCollisionBottom(true);
            ch.setY((chBottomRow)*48-1-ch.getSize());
    
        }
        else{
            ch.setCollisionOn(false);
            ch.setCollisionBottom(false);
        } 

        
    }
}
