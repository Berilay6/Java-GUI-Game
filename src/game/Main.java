package game;
public class Main{
    public static void main(String[] args) {
        
        GamePanel gamePanel = new GamePanel();
        GameWindow gameWindow = new GameWindow(gamePanel);

        if(args.length > 0){
            String fileName = args[0];
            gamePanel.loadGame(fileName);
            gamePanel.startGame();
        }
        else{
            gamePanel.startGame();
        }
    }
}
