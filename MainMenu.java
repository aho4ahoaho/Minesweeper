import javax.swing.*;
import java.awt.*;

public class MainMenu {
    JPanel menuPanel;
    GameHandler gameHandler;

    MainMenu(JPanel menuPanel, GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        this.menuPanel = menuPanel;
        // レイアウトの設定
        menuPanel.setLayout(new GridLayout(11, 1));

        // タイトルの作成
        JLabel title = new JLabel("MineSweeper");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Sans", Font.BOLD, 50));

        // レイアウトの調整
        menuPanel.add(new JLabel());
        menuPanel.add(title);
        for (int i = 0; i < 3; i++) {
            menuPanel.add(new JLabel());
        }
        boardSizeSetView();
        difficultySetView();
        menuPanel.add(new JLabel());
        setStartButton();
    }
    // ボードサイズ選択
    private void boardSizeSetView() {
        // ベースとなる文字列
        final String boardSize = "Select Board Size:";
        // ラベルの作成
        JLabel boardSizeLabel = new JLabel(boardSize + "Medium");
        boardSizeLabel.setHorizontalAlignment(JLabel.CENTER);
        menuPanel.add(boardSizeLabel);

        // ボタンの作成
        JButton[] boardSizeButtons = new JButton[3];
        boardSizeButtons[0] = new JButton("Small");
        boardSizeButtons[1] = new JButton("Medium");
        boardSizeButtons[2] = new JButton("Large");
        boardSizeButtons[0].addActionListener(e -> {
            gameHandler.setBoardSize(10);
            boardSizeLabel.setText(boardSize + "Small");
        });
        boardSizeButtons[1].addActionListener(e -> {
            gameHandler.setBoardSize(15);
            boardSizeLabel.setText(boardSize + "Medium");
        });
        boardSizeButtons[2].addActionListener(e -> {
            gameHandler.setBoardSize(20);
            boardSizeLabel.setText(boardSize + "Large");
        });

        // ボタンを配置するパネルの作成
        JPanel boardSizePanel = new JPanel();
        for (JButton button : boardSizeButtons) {
            boardSizePanel.add(button);
        }
        menuPanel.add(boardSizePanel);
    }

    // 難易度選択
    private void difficultySetView() {
        // ベースとなる文字列
        final String difficulty = "Select Difficulty:";
        // ラベルの作成
        JLabel difficultyLabel = new JLabel(difficulty + "Normal");
        difficultyLabel.setHorizontalAlignment(JLabel.CENTER);
        menuPanel.add(difficultyLabel);

        // ボタンの作成
        JButton[] difficultyButtons = new JButton[3];
        difficultyButtons[0] = new JButton("Easy");
        difficultyButtons[1] = new JButton("Normal");
        difficultyButtons[2] = new JButton("Hard");
        difficultyButtons[0].addActionListener(e -> {
            gameHandler.setProba(GameHandler.Difficulty.EASY);
            difficultyLabel.setText(difficulty + "Easy");
        });
        difficultyButtons[1].addActionListener(e -> {
            gameHandler.setProba(GameHandler.Difficulty.NORMAL);
            difficultyLabel.setText(difficulty + "Normal");
        });
        difficultyButtons[2].addActionListener(e -> {
            gameHandler.setProba(GameHandler.Difficulty.HARD);
            difficultyLabel.setText(difficulty + "Hard");
        });

        // ボタンを配置するパネルの作成
        JPanel difficultyPanel = new JPanel();
        for (JButton button : difficultyButtons) {
            difficultyPanel.add(button);
        }
        menuPanel.add(difficultyPanel);
    }

    private void setStartButton() {
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
        menuPanel.add(buttonPanel);
    }
}