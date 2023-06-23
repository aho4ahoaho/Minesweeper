import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // ウィンドウの作成
        JFrame frame = new JFrame("My Drawing");

        // パネルの作成
        JPanel menuPanel = new JPanel();
        JPanel gamePanel = new JPanel();
        JPanel resultPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();

        // ウィンドウの設定
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 620);
        frame.setLayout(cardLayout);
        frame.setResizable(false);

        // パネルの追加
        frame.add(menuPanel, "menu");
        frame.add(gamePanel, "game");
        frame.add(resultPanel, "result");

        // インスタンスの作成
        ResultMenu result = new ResultMenu(resultPanel);
        GameHandler game = new GameHandler(gamePanel, result);
        new MainMenu(menuPanel, game);

        // 画面を表示
        frame.setVisible(true);
    }
}
