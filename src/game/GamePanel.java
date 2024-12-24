package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class GamePanel extends JPanel implements Runnable{
    
    //SCREEN SETTINGS

    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 20;
    private final int maxScreenRow = 14;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;
    

    private Thread gameThread;
    private KeyInput keyInput;
    private TileManager tileManager;
    private final int FPS = 60;
    private boolean isGamePaused=false;

    private Player player;
    public CollisionChecker colChecker;
    private List<Points> points = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<Enemy> keyEnemies = new ArrayList<>();
    private EnemySpawner enemySpawner;
    private Key key;
    private boolean touchedKey=false;
    private GameData gameData;

    private int score;
    private String scoreText = "Score: 0";

    private JDialog popupDialog;
    private JLabel messageLabel;
   

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);

        keyInput = new KeyInput(this);
        this.addKeyListener(keyInput);
        this.setFocusable(true);

        loadEnemies();
        loadPoints();
        player=new Player(this, keyInput, points,enemies);
        tileManager = new TileManager(this);
        colChecker=new CollisionChecker(this);
        score=0;
        enemySpawner = new EnemySpawner(this, enemies);
        key = new Key((12*tileSize)-(tileSize/2), (6*tileSize)-(tileSize/2), 12);

        //POP-UP1
         popupDialog = new JDialog();
        popupDialog.setTitle("Oyun Bitti!");
        popupDialog.setSize(300, 200);
        popupDialog.setLocationRelativeTo(this); // Oyun paneline göre konumlandır
        popupDialog.setModal(true); // Kullanıcı pop-up ekranı kapamadan önce ana pencereye dönecek
        popupDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
       
        popupDialog.add(messageLabel,BorderLayout.CENTER);
        
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> {popupDialog.dispose(); startAgain(); keyInput.setPause(false);});
        popupDialog.add(okButton, BorderLayout.SOUTH);

    }

    public void startGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void showPopup(JDialog popup) {
        SwingUtilities.invokeLater(() -> {
            popup.setVisible(true);
        });
    }

    public void saveAndShowPopup() {
        saveGame();  // Oyunu dosyaya kaydet

        // Pop-up
        JOptionPane.showMessageDialog(null, "Kayıt tamamlandı!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
    }
    

    public List<Points> getPoints() {
        return points;
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }
    public List<Enemy> getKeyEnemies() {
        return keyEnemies;
    }
    public KeyInput getKeyInput(){
        return keyInput;
    }

    public Player getPlayer() {
        return player;
    }

    public Key getKey(){
        return key;
    }
    public boolean getTouchedKey(){
        return touchedKey;
    }
    public void setTouchedKey(boolean touchedKey){
        this.touchedKey=touchedKey;
    }

    public JDialog getPopupDialog() {
        return popupDialog;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getTileSize(){
        return tileSize;
    } 
    public int getScreenWidth(){
        return screenWidth;
    }
    public int getScreenHeight(){
        return screenHeight;
    }
    public int getMaxScreenCol(){
        return maxScreenCol;
    }
    public int getMaxScreenRow(){
        return maxScreenRow;
    }
    public int getOriginalTileSize(){
        return originalTileSize;
    }   
    public int getScale(){
        return scale;
    }
    public TileManager getTileManager(){
        return tileManager;
    }
    public boolean isGamePaused(){
        return isGamePaused;
    }
    public int getFPS(){
        return FPS;
    }

    @Override
    public void run() {
        
        //GAME LOOP

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(true) {

            if (!isGamePaused) {
                update();
                repaint();
            
            }
            if(keyInput.getPause()){pauseGame();}
            else{resumeGame();}

            try {
                double remainingTime = (nextDrawTime - System.nanoTime())/1000000; //nano to miliseconds
                if(remainingTime < 0) remainingTime = 0;
                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }

    public void update() {
        scoreText = "Score: " + score;
        messageLabel.setText("Kazandınız! Skorunuz: " + score );
        enemySpawner.update();
        player.update();
           
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        player.draw(g2); 
        
        try {
        	Iterator<Points> iterator = points.iterator();
        	while(iterator.hasNext()) {
        		Points point = iterator.next();
        		point.draw(g2);
        		if(point.isCollisionOn()) {
        			iterator.remove();
        		}
        	}
        } catch(Exception e) {
        	e.toString();
        }

        	enemies.forEach(enemy -> enemy.draw(g2));
        
        if(!touchedKey) key.draw(g2);

        //score 
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString(scoreText, 10, 60);

        g2.dispose();

    }

    public void loadPoints() {

        String[][] mapTileStrings = new String[maxScreenCol][maxScreenRow];
        try {
           InputStream in = getClass().getResourceAsStream("/game/resources/map/pointsMap.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int col=0;
            int row=0;

            while(col<maxScreenCol && row<maxScreenRow){
                String line = br.readLine();
                
                while(col<maxScreenCol){
                    String mapLine[] = line.split(" ");
                    String str = mapLine[col];
                    mapTileStrings[col][row] = str;
                    col++;
                }
                if(col==maxScreenCol){
                    col=0;
                    row++;
                }
            }

            br.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            for(int i=0; i<maxScreenCol; i++){
                for(int j=0; j<maxScreenRow; j++){
                    String str = mapTileStrings[i][j];
                    if(str.equals("*")){
                        points.add(new Points(((i+1)*tileSize)-(tileSize/2), ((j+1)*tileSize)-(tileSize/2)+8, 4));
                    }
                }
            }
    }

    public void loadEnemies(){
    	
            enemies.add(new Enemy(288,571, Color.blue, -1, 5,false,this));
            enemies.add(new Enemy(432,571, Color.orange, 1, 2,false,this));
            enemies.add(new Enemy(720,571, Color.orange, -1, 3,false,this));
            enemies.add(new Enemy(480,571, Color.red, 1, 4,false,this));

            Enemy e1 = new Enemy(528,283, Color.red, -1, 3,true,this);
            Enemy e2 = new Enemy(384, 283, Color.red, 1, 1,true, this);

            enemies.add(new Enemy(96,283, Color.blue, 1, 3,false,this));
            enemies.add(e1);
            enemies.add(e2);
            enemies.add(new Enemy(400,283, Color.blue, -1, 2,false,this));

            keyEnemies.add(e1);
            keyEnemies.add(e2);
    }

    public void increaseScore(){
        score+=5;
    }

    public void startAgain(){
        score=0;
        keyInput.resetKeys();
        player.setDefaultValues();
        player.setColor(Color.red);
        enemies.clear();
        points.clear();
        loadEnemies();
        loadPoints();
        touchedKey=false;
        colChecker = new CollisionChecker(this);
        
        
    }
    public void pauseGame() {
        isGamePaused = true;
    }
    
    public void resumeGame() {
        isGamePaused =false;
    }

    public void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.bin"))) {

            // Oyuncu verilerini oluştur
            GameData.PlayerData playerData = new GameData.PlayerData(
                player.getX(), player.getY(), player.getColor(), player.getDirection()
            );
    
            // Düşman verilerini oluştur
            List<GameData.EnemyData> enemiesData = new ArrayList<>();
            for (Enemy enemy : enemies) {
                GameData.EnemyData enemyData = new GameData.EnemyData(
                    enemy.getX(), enemy.getY(), enemy.getColor(), enemy.getLife(),enemy.getDir(),enemy.getVelX(),enemy.getIsKeyEnemy());
                enemiesData.add(enemyData);
            }            
    
            // Mermi verilerini oluştur
            List<GameData.BulletData> bulletsData = new ArrayList<>();
            for (Bullet bullet : player.getBullets()) {
                GameData.BulletData bulletData = new GameData.BulletData(
                    bullet.getX(), bullet.getY(), bullet.getColor(), bullet.getDir());
                bulletsData.add(bulletData);
            }
    
            // Puan verilerini oluştur
            List<GameData.PointsData> pointsData = new ArrayList<>();
            for (Points point : points) {
                GameData.PointsData pointData = new GameData.PointsData(
                    point.getX(), point.getY());
                pointsData.add(pointData);
            }
    
            // GameData nesnesini oluştur ve kaydet
            GameData gameData = new GameData(score, playerData, enemiesData, bulletsData, pointsData, touchedKey);

            oos.writeObject(gameData);
    
        } catch (Exception e) {
            System.out.println("Oyun kaydedilirken hata oluştu: " + e.getMessage());
        }
    }
    public void loadGame(String filePath) {
    	keyInput.setPause(true);
    	keyInput.setPauseCount(1);
    	
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            
            GameData loadedData = (GameData) inputStream.readObject(); 

            //player
            player.setX(loadedData.getPlayerData().getX());
            player.setY(loadedData.getPlayerData().getY());
            if(loadedData.getPlayerData().getColor().equals(Color.red)) {
            	player.setColor(Color.red);
            }
            else if(loadedData.getPlayerData().getColor().equals(Color.blue)) {
            	player.setColor(Color.blue);
            }
            else if(loadedData.getPlayerData().getColor().equals(Color.magenta)) {
            	player.setColor(Color.magenta);
            }
            player.setDirection(loadedData.getPlayerData().getDirection());
            player.setSolidArea(new Rectangle(loadedData.getPlayerData().getX(),loadedData.getPlayerData().getY(),16,16));

            //enemies
            enemies.clear();
            for (GameData.EnemyData enemyData : loadedData.getEnemiesData()) {
                Enemy enemy = new Enemy(enemyData.getX(), enemyData.getY(), enemyData.getColor(), enemyData.getDir(), enemyData.getSpeed(), enemyData.getIsKeyEnemy(), this);
                enemies.add(enemy);
                
            }
            
            //keyEnemies
            keyEnemies.clear();
            for(Enemy e : enemies) {
            	if(e.getIsKeyEnemy()){
            		keyEnemies.add(e);
            	}
            }

            //bullets
            player.getBullets().clear();
            for (GameData.BulletData bulletData : loadedData.getBulletsData()) {
                Bullet bullet = new Bullet(bulletData.getX(), bulletData.getY(), bulletData.getColor(), bulletData.getDir(), this);
                player.getBullets().add(bullet);
            }

            //points
            points.clear();
            for (GameData.PointsData pointData : loadedData.getPointsData()) {
                Points point = new Points(pointData.getX(), pointData.getY(), 4);
                points.add(point);
            }
            touchedKey=loadedData.isTouchedKey();
            
            //score
            score=loadedData.getScore();
            
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        colChecker = new CollisionChecker(this);
    }
    
}
