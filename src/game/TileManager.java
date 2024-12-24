package game;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;


public class TileManager {

    private GamePanel gp;
    private Tile[] tile;
    private int[][] mapTileNum ;

    TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.getMaxScreenCol()+1][gp.getMaxScreenRow()+1];
        loadMap();
        getTileImage();
        
    }
    public void setMapTileNum(int col, int row, int num){
        mapTileNum[col][row] = num;
    }
    public int[][] getMapTileNum(){
        return mapTileNum;
    }
    public Tile[] getTile() {
        return tile;
    }
    public void setTile(Tile[] tile) {
        this.tile = tile;
    }

    public void loadMap() {

        try {
           InputStream in = getClass().getResourceAsStream("/game/resources/map/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int col=0;
            int row=0;

            while(col<gp.getMaxScreenCol() && row<gp.getMaxScreenRow()){
                String line = br.readLine();
                
                while(col<gp.getMaxScreenCol()){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col==gp.getMaxScreenCol()){
                    col=0;
                    row++;
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile(ImageIO.read(getClass().getResource("/game/resources/tiles/back.png")), false);
            
            tile[1] = new Tile(ImageIO.read(getClass().getResource("/game/resources/tiles/wall.png")),true);
        
            tile[2] = new Tile( ImageIO.read(getClass().getResource("/game/resources/tiles/red.png")),true);

            tile[3] = new Tile(ImageIO.read(getClass().getResource("/game/resources/tiles/blue.png")), true);

            tile[4] = new Tile(ImageIO.read(getClass().getResource("/game/resources/tiles/teleport.png")),true);

            tile[5] = new Tile(ImageIO.read(getClass().getResource("/game/resources/tiles/door.png")),false);

            tile[6] = new Tile(ImageIO.read(getClass().getResource("/game/resources/tiles/spawner.png")),true);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        int col =0;
        int row =0;
        int x=0;
        int y=0;

        while(col<gp.getMaxScreenCol() && row<gp.getMaxScreenRow()){

            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].getImage(), x, y,gp.getTileSize(), gp.getTileSize(),null);
            col++;
            x+=gp.getTileSize();

            if(col == gp.getMaxScreenCol()){
                col=0;
                x=0;
                row++;
                y+=gp.getTileSize();
            }
            
        }

    }
}
