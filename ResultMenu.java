import javax.swing.*;
import java.awt.*;

public class ResultMenu {
    JPanel resultPanel;
    JLabel resultLabel;
    int score = 0;
    State[][] board;
    BoardView boardView;

    ResultMenu(JPanel resultPanel) {
        this.resultPanel = resultPanel;
        resultPanel.setLayout(new FlowLayout());
        JButton backButton = new JButton("Back");
        resultLabel = new JLabel("Score: " + score);
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        backButton.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) resultPanel.getParent().getLayout();
            cardLayout.show(resultPanel.getParent(), "menu");
        });
        boardView = new BoardView();
        boardView.setIsGameOver(true);
        resultPanel.add(resultLabel);
        resultPanel.add(boardView);
        resultPanel.add(backButton);
    }

    public void setBoard(State[][] board) {
        this.board = board;
        boardView.updateBoard(board);
    }

    public void setScore(int score) {
        this.score = score;
        resultLabel.setText("Score: " + score);
    }
}
