package game;

import javax.swing.JFrame;

public class GameWindow {
    
    private JFrame window;

    public GameWindow(GamePanel gamePanel) {
       
        window = new JFrame("Ateş ve Su Klonu");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(gamePanel);
        window.pack();
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }


}
