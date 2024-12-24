package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyInput implements KeyListener {

    private boolean up, left, right;
    private boolean red, blue, magenta;
    private boolean pause;
    private int pauseCount=0;
    private boolean shoot;
    private GamePanel gp;
    private Map<String, Boolean> defaultKeys = new HashMap<>();

    public KeyInput(GamePanel gp){
        this.gp=gp;

        defaultKeys.put("up", false);
        defaultKeys.put("left", false);
        defaultKeys.put("right", false);
        defaultKeys.put("shoot", false);
        defaultKeys.put("blue", false);
        defaultKeys.put("magenta", false);
        defaultKeys.put("red", false);
        defaultKeys.put("pause", false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
       //Kullanılmayacak
    }

    @Override
    public void keyPressed(KeyEvent e) {
       int key = e.getKeyCode();
        
       if(key == KeyEvent.VK_W) {
           up=true;
       }
       else if(key == KeyEvent.VK_A) {
           left=true;
       }
       else if(key == KeyEvent.VK_D) {
           right=true;
       }

       else if(key == KeyEvent.VK_1) {
           red=true;
           blue=false;
           magenta=false;
       }
       else if(key == KeyEvent.VK_2) {
           blue=true;
           red=false;
           magenta=false;
       }
       else if(key == KeyEvent.VK_3) {
           magenta=true;
           red=false;
           blue=false;
       }
       else if(key == KeyEvent.VK_SPACE){
                shoot=true;
        }
        else if(key == KeyEvent.VK_P){
            if(pauseCount==0){
                pause=true;
                pauseCount++;
            }

            else if(pauseCount==1){
                pause=false;
                pauseCount--;
            }

        }

        else if(key == KeyEvent.VK_K){
            if(pause){
                gp.saveAndShowPopup();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

       if(key == KeyEvent.VK_W) {
           up=false;
       }    
       else if(key == KeyEvent.VK_A) {
           left=false;
       }
       else if(key == KeyEvent.VK_D) {
           right=false;
       }
       else if(key == KeyEvent.VK_SPACE){
        shoot=false;
        }
    }

    public boolean getUp(){
        return up;
    }
    
    public boolean getLeft(){
        return left;
    }
    public boolean getRight(){
        return right;
    }
    public boolean getRed(){
        return red;
    }
    public boolean getBlue(){
        return blue;
    }
    public boolean getMagenta(){
        return magenta;
    }
    public boolean getShoot(){
        return shoot;
    }
    public void setShoot(boolean shoot){
        this.shoot=shoot;
    }
    public void setRed(boolean red){
        this.red=red;
    }
    public void setBlue(boolean blue){
        this.blue=blue;
    }
    public void setMagenta(boolean magenta){
        this.magenta=magenta;
    }
    public void setUp(boolean up){
        this.up=up;
    }
    public void setLeft(boolean left){
        this.left=left;
    }
    public void setRight(boolean right){
        this.right=right;
    }
    public boolean getPause(){
        return pause;
    }
    public void setPause(boolean pause){
        this.pause=pause;
    }
    public int getPauseCount(){
        return pauseCount;
    }
    public void setPauseCount(int pauseCount){
        this.pauseCount=pauseCount;
    }

    public void resetKeys() {
        // Tuşları varsayılan atamalarla sıfırla
        up = defaultKeys.get("up");
        left = defaultKeys.get("left");
        right = defaultKeys.get("right");
        shoot = defaultKeys.get("shoot");
        blue = defaultKeys.get("blue");
        magenta = defaultKeys.get("magenta");
        red = defaultKeys.get("red");
    }
    
}
