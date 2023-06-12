import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Drawing");
        JPanel menuPanel = new JPanel();
        JPanel gamePanel = new JPanel();
        JPanel resultPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        frame.setLayout(cardLayout);
        frame.add(menuPanel, "menu");
        frame.add(gamePanel, "game");
        frame.add(resultPanel, "result");
        ResultMenu result = new ResultMenu(resultPanel);
        GameHandler game = new GameHandler(gamePanel, result);

        MainMenu menu = new MainMenu(menuPanel, game);
        menu.setGameHandler(game);
        frame.setVisible(true);
    }
}