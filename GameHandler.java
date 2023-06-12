import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.*;

public class GameHandler {
    JPanel gamePanel;
    BoardView boardView;
    ResultMenu resultMenu;
    int[][] board = new int[15][15];
    Random rand = new Random();
    int score = 0;

    GameHandler(JPanel gamePanel, ResultMenu resultMenu) {
        this.gamePanel = gamePanel;
        this.resultMenu = resultMenu;

        gamePanel.setLayout(new BorderLayout());
        boardView = new BoardView();
        boardView.setBackground(Color.WHITE);

        gamePanel.add(boardView, BorderLayout.CENTER);

        boardView.addMouseListener(new MouseProc());
        boardView.setSize(500, 500);

    }

    void boardView() {
        boardView.updateBoard(board);
    }

    void initialize() {
        score = 0;
        randomize();
        boardView();
    }

    void randomize() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (rand.nextInt(5) == 0) {
                    board[i][j] = 2;
                } else {
                    board[i][j] = 0;
                }
            }
        }
    }

    Boolean check() {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] == 0) {
                    return false;
                }
            }
        }
        resultMenu.setScore(score);
        resultView();
        return true;
    }

    void resultView() {
        CardLayout cardLayout = (CardLayout) gamePanel.getParent().getLayout();
        cardLayout.show(gamePanel.getParent(), "result");
    }

    void autoOpen(int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;
                int tx = x + i;
                int ty = y + j;
                if (tx < 0 || tx >= board.length || ty < 0 || ty >= board[0].length) {
                    continue;
                }
                int bomb = boardView.searchNeighbor(tx, ty);
                if ((board[tx][ty] == 0 || board[tx][ty] == 3)) {
                    board[tx][ty] = 1;
                    score++;
                    if (bomb == 0) {
                        autoOpen(tx, ty);

                    }
                }
            }
        }
    }

    class MouseProc implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            Dimension viewSize = boardView.getSize();
            int w = viewSize.width / board.length;
            int h = viewSize.height / board[0].length;
            int x = e.getX() / w;
            int y = e.getY() / h;
            if (x < 0 || x >= board.length || y < 0 || y >= board[0].length)
                return;
            // 右クリックで旗を立てる
            if (e.getButton() == MouseEvent.BUTTON3) {
                switch (board[x][y]) {
                    // 通常マスに旗を立てる
                    case 0:
                        board[x][y] = 3;
                        break;
                    // 旗ありマスを通常マスに
                    case 3:
                        board[x][y] = 0;
                        break;
                    // 旗なし爆弾を旗あり爆弾に
                    case 2:
                        board[x][y] = 4;
                        break;
                    // 旗あり爆弾を旗なし爆弾に
                    case 4:
                        board[x][y] = 2;
                        break;
                }
            } else if (e.getButton() == MouseEvent.BUTTON1) {
                switch (board[x][y]) {
                    case 3:
                    case 0:
                        board[x][y] = 1;
                        score++;
                        if(boardView.searchNeighbor(x, y) == 0){
                            autoOpen(x, y);
                        }
                        break;
                    case 1:
                        break;
                    case 2:
                        resultMenu.setScore(score);
                        resultView();
                        return;
                }
            }

            boardView.updateBoard(board);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }
}

class MiddleFont {
    FontMetrics fontMetrics;
    Font font;

    MiddleFont(FontMetrics fontMetrics, Font font) {
        this.fontMetrics = fontMetrics;
        this.font = font;
    }

    int xpos(int x, String str) {
        return x + fontMetrics.stringWidth(str) / 2;
    }

    int xpos(int x) {
        return xpos(x, "0");
    }

    int xpos(Double x, String str) {
        return xpos((int) Math.round(x), str);
    }

    int xpos(Double x) {
        return xpos(x, "0");
    }

    int ypos(int y) {
        return y + fontMetrics.getHeight() / 2;
    }

    int ypos(Double y) {
        return ypos((int) Math.round(y));
    }
}

class BoardView extends JPanel {
    // [x][y] 左上が原点
    // 0:開けてない 1:開けてある 2:爆弾 3:旗 4:旗あり爆弾
    int board[][] = new int[15][15];
    Font font = new Font("Arial", Font.PLAIN, 20);
    FontMetrics fontMetrics = getFontMetrics(font);
    MiddleFont middleFont = new MiddleFont(fontMetrics, font);

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        Dimension viewSize = getSize();
        int w = viewSize.width / board.length;
        int h = viewSize.height / board[0].length;
        g.setFont(font);

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                switch (board[x][y]) {
                    case 1:
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(x * w, y * h, w, h);
                        g.setColor(Color.BLACK);
                        String bomb = String.valueOf(searchNeighbor(x, y));
                        g.drawString(bomb, x * w, (y + 1) * h);
                        break;
                    /*case 2:
                        g.setColor(Color.RED);
                        g.fillRect(x * w, y * h, w, h);
                        break;*/
                    case 4:
                    case 3:
                        g.setColor(Color.BLUE);
                        g.fillRect(x * w, y * h, w, h);
                        break;
                    default:
                        g.setColor(Color.WHITE);
                        g.fillRect(x * w, y * h, w, h);
                        break;
                }
                g.setColor(Color.DARK_GRAY);
                g.drawRect(x * w, y * h, w, h);
            }
        }
    }

    int searchNeighbor(int x, int y) {
        int result = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int tx = x + i;
                int ty = y + j;
                if (tx < 0 || tx >= board.length || ty < 0 || ty >= board[0].length) {
                    continue;
                }
                if (board[tx][ty] == 2) {
                    result++;
                }
            }
        }
        return result;
    }

    void updateBoard(int[][] board) {
        this.board = board;
        repaint();
    }
}