import javax.swing.*;
import java.awt.*;

public class MainMenu{
    JPanel menuPanel;
    GameHandler gameHandler;
    MainMenu(JPanel menuPanel,GameHandler gameHandler){
        this.gameHandler = gameHandler;
        this.menuPanel = menuPanel;

        menuPanel.setLayout(new BorderLayout());
        JButton startButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");
        startButton.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) menuPanel.getParent().getLayout();
            cardLayout.show(menuPanel.getParent(), "game");
            gameHandler.initialize();
        });
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        menuPanel.add(buttonPanel, BorderLayout.SOUTH); 
    }
    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }
}