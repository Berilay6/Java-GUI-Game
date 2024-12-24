package game;
import java.awt.Color;
import java.util.List;
import java.util.Random;


public class EnemySpawner {

    private List<Enemy> enemies; 
    private Random random;
    private int spawnInterval;   
    private GamePanel gp;

    public EnemySpawner(GamePanel gp, List<Enemy> enemies) {
        this.gp=gp;
        spawnInterval = 0; 
        this.enemies = enemies;
        this.random = new Random();
    }

    public void update() {
    //yaklaşık 10 saniyede bir canavar üretecek
       if(spawnInterval<500) spawnInterval++;

       else if(spawnInterval>=500){
            spawnEnemy();
            spawnInterval=0;
       } 

    }

    private void spawnEnemy() {

        int startX = 888;
        int startY = 72;
        int speed = random.nextInt(3) + 1; // Hız 1 ile 3 arasında rastgele bir değer olacak.
        int colorRandom=random.nextInt(3); //Rengi rastgele olacak
        Color color;
        switch (colorRandom){
            case 0:
                color=Color.RED;
                break;
            case 1:
                color=Color.BLUE;
                break;
            default:
                color=Color.ORANGE;
                break;
        }

        enemies.add(new Enemy(startX, startY,color,-1, speed, false,gp));
    }

}
