import javax.swing.*;
import java.awt.*;

public class ResultMenu {
    JPanel resultPanel;
    JLabel resultLabel;
    int score = 0;

    ResultMenu(JPanel resultPanel) {
        this.resultPanel = resultPanel;
        resultPanel.setLayout(new BorderLayout());
        JButton backButton = new JButton("Back");
        resultLabel = new JLabel("Score: " + score);
        backButton.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) resultPanel.getParent().getLayout();
            cardLayout.show(resultPanel.getParent(), "menu");
        });
        resultPanel.add(backButton, BorderLayout.SOUTH);
        resultPanel.add(resultLabel, BorderLayout.CENTER);
    }

    public void setScore(int score) {
        this.score = score;
        resultLabel.setText("Score: " + score);
    }
}
