import javax.swing.*;
import java.awt.*;

public class ResultMenu {
    JPanel resultPanel;
    JLabel resultLabel;
    JLabel timeLabel;
    int score = 0;
    int time = 0;
    State[][] board;
    BoardView boardView;

    ResultMenu(JPanel resultPanel) {
        this.resultPanel = resultPanel;
        resultPanel.setLayout(new FlowLayout());

        // スコアの表示
        resultLabel = new JLabel("Score: " + score);
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 30));

        // タイムの表示
        timeLabel = new JLabel("Time: " + time + "s");
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 30));

        // メニューに戻るボタン
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) resultPanel.getParent().getLayout();
            cardLayout.show(resultPanel.getParent(), "menu");
        });

        // 盤面の表示
        boardView = new BoardView();
        boardView.setIsGameOver(true);

        resultPanel.add(resultLabel);
        resultPanel.add(timeLabel);
        resultPanel.add(boardView);
        resultPanel.add(backButton);
    }

    // リザルト表示に使用する盤面を設定
    public void setBoard(State[][] board) {
        this.board = board;
        boardView.updateBoard(board);
    }

    // スコアを設定
    public void setScore(int score) {
        this.score = score;
        resultLabel.setText("Score: " + score);
    }

    // タイムを設定
    public void setTime(int time) {
        this.time = time;
        long minute = time / 60;
        long second = time % 60;
        timeLabel.setText(String.format("%02d:%02d", minute, second));
    }
}
