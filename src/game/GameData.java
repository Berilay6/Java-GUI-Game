package game;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameData implements Serializable {
	private static final long serialVersionUID = 4885435883260433170L;
    private int score;
    private PlayerData playerData;
    private List<EnemyData> enemiesData;
    private List<BulletData> bulletsData;
    private List<PointsData> pointsData;
    private boolean touchedKey;
    

    public GameData(int score, PlayerData playerData, List<EnemyData> enemiesData, List<BulletData> bulletsData, List<PointsData> pointsData, boolean touchedKey) {
        this.score = score;
        this.playerData = playerData;
        this.enemiesData = enemiesData;
        this.bulletsData = bulletsData;
        this.pointsData = pointsData;
        this.touchedKey = touchedKey;
    }

    // Getter ve setter metodları
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public PlayerData getPlayerData() {
        return playerData;
    }
    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }
    public List<EnemyData> getEnemiesData() {
        return enemiesData;
    }
    public void setEnemiesData(List<EnemyData> enemiesData) {
        this.enemiesData = enemiesData;
    }
    public List<BulletData> getBulletsData() {
        return bulletsData;
    }
    public void setBulletsData(List<BulletData> bulletsData) {
        this.bulletsData = bulletsData;
    }
    public List<PointsData> getPointsData() {
        return pointsData;
    }
    public void setPointsData(List<PointsData> pointsData) {
        this.pointsData = pointsData;
    }
    public boolean isTouchedKey() {
        return touchedKey;
    }
    public void setTouchedKey(boolean touchedKey) {
        this.touchedKey = touchedKey;
    }


    public static class PlayerData implements Serializable {
    	private static final long serialVersionUID = 7376221796466398789L;
        private int x;
        private int y;
        private Color color; 
        private String direction; // Yön bilgisi

        public PlayerData(int x, int y, Color color, String direction) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.direction = direction;
        }

        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public Color getColor() {
            return color;
        }
        public String getDirection() {
            return direction;
        }

    }

    public static class EnemyData implements Serializable {
    	private static final long serialVersionUID = 5141562603705406165L;
        private int x;
        private int y;
        private Color color; 
        private int life;
        private int dir;
        private int speed;
        private boolean isKeyEnemy;

        public EnemyData(int x, int y, Color color, int life, int dir, int speed, boolean keyEnemy) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.life = life;
            this.dir = dir;
            this.speed = speed;
            this.isKeyEnemy=keyEnemy;
        }
     
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public Color getColor() {
            return color;
        }
        public int getLife() {
            return life;
        }
        public int getDir() {
            return dir;
        }
        public int getSpeed() {
            return speed;
        }
        public boolean getIsKeyEnemy() {
        	return isKeyEnemy;
        }


    }

    public static class BulletData implements Serializable {
    	private static final long serialVersionUID = 4885435883260433170L;
        private int x;
        private int y;
        private Color color; 
        private int dir;

        public BulletData(int x, int y, Color color, int dir) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.dir = dir;
        }

        
        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
        public Color getColor() {
            return color;
        }
        public int getDir() {
            return dir;
        }
    }

    public static class PointsData implements Serializable {
    	private static final long serialVersionUID = 8082973708691545638L;
        private int x;
        private int y;
       
        public PointsData(int x, int y) {
            this.x = x;
            this.y = y;
            
        }

        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    }


}
