import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.*;

enum State {
    CLOSED, OPENED, FLAG, BOMB, BOMB_FLAG
}

public class GameHandler {
    JPanel gamePanel;
    JLabel scoreLabel;
    BoardView boardView;
    ResultMenu resultMenu;
    State[][] board = new State[15][15];
    Random rand = new Random();
    int score = 0;
    TimerView timerView = new TimerView();
    Difficulty proba = Difficulty.NORMAL;

    public static enum Difficulty {
        EASY, NORMAL, HARD
    }

    GameHandler(JPanel gamePanel, ResultMenu resultMenu) {
        this.gamePanel = gamePanel;
        this.resultMenu = resultMenu;

        gamePanel.setLayout(new FlowLayout());

        // 盤面の表示
        boardView = new BoardView();
        boardView.addMouseListener(new MouseProc());
        // boardView.setIsGameOver(true);

        // スコアの表示
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 30));

        // タイマーの表示
        timerView.setHorizontalAlignment(JLabel.CENTER);
        timerView.setFont(new Font("Arial", Font.PLAIN, 30));

        // ゲーム画面に追加
        gamePanel.add(scoreLabel);
        gamePanel.add(timerView);
        gamePanel.add(boardView);
    }

    // 難易度の設定
    public void setProba(Difficulty proba) {
        this.proba = proba;
    }

    // 盤面のサイズを設定
    public void setBoardSize(int size) {
        board = new State[size][size];
    }

    // 盤面を表示
    void boardView() {
        boardView.updateBoard(board);
    }

    // ゲームの初期化
    void initialize() {
        // スコアの初期化
        setScore(0);
        // 盤面の初期化
        randomize();
        // 盤面の表示
        boardView();
        // タイマーの初期化
        timerView.setTime();
        // タイマーの開始
        new Thread(timerView).start();
    }

    // 盤面の初期化
    void randomize() {
        int proba;
        // 難易度によって爆弾の出現確率を変更
        switch (this.proba) {
            case EASY:
                proba = 12;
                break;
            case HARD:
                proba = 4;
                break;
            default:
                proba = 7;
                break;
        }
        // 盤面を埋める
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // 1/Nの確率で爆弾を配置
                if (rand.nextInt(proba) == 0) {
                    board[i][j] = State.BOMB;
                } else {
                    board[i][j] = State.CLOSED;
                }
            }
        }
    }

    // ゲームの終了判定
    Boolean check() {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] == State.CLOSED) {
                    return false;
                }
            }
        }
        resultView();
        return true;
    }

    // ゲーム終了時の処理
    void resultView() {
        timerView.stop();
        resultMenu.setScore(score);
        resultMenu.setBoard(board);
        resultMenu.setTime((int)timerView.getTime());
        System.out.println("Game Over");
        System.out.println("Score: " + score);
        System.out.println("Time: " + timerView.getTime());
        CardLayout cardLayout = (CardLayout) gamePanel.getParent().getLayout();
        cardLayout.show(gamePanel.getParent(), "result");
    }

    // スコアの設定
    void setScore(int score) {
        this.score = score;
        scoreLabel.setText("Score: " + score);
    }

    // 安全なマスを自動で開く
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
                if ((board[tx][ty] == State.CLOSED)) {
                    board[tx][ty] = State.OPENED;
                    setScore(score + 1);
                    if (bomb == 0) {
                        autoOpen(tx, ty);

                    }
                }
            }
        }
    }

    class MouseProc implements MouseListener {

        @Override
        // マウスのクリックを検知
        public void mouseClicked(MouseEvent e) {
            // 盤面のサイズを取得
            Dimension viewSize = boardView.getSize();
            int w = viewSize.width / board.length;
            int h = viewSize.height / board[0].length;
            // クリックしたマスの座標を取得
            int x = e.getX() / w;
            int y = e.getY() / h;
            // 盤面外をクリックした場合は何もしない
            if (x < 0 || x >= board.length || y < 0 || y >= board[0].length)
                return;
            // 右クリックで旗を立てる
            if (e.getButton() == MouseEvent.BUTTON3) {
                switch (board[x][y]) {
                    // 通常マスに旗を立てる
                    case CLOSED:
                        board[x][y] = State.FLAG;
                        break;
                    // 旗ありマスを通常マスに
                    case FLAG:
                        board[x][y] = State.CLOSED;
                        break;
                    // 旗なし爆弾を旗あり爆弾に
                    case BOMB:
                        board[x][y] = State.BOMB_FLAG;
                        break;
                    // 旗あり爆弾を旗なし爆弾に
                    case BOMB_FLAG:
                        board[x][y] = State.BOMB;
                        break;
                    default:
                        break;
                }
                // 左クリックでマスを開く
            } else if (e.getButton() == MouseEvent.BUTTON1) {
                switch (board[x][y]) {
                    // 通常マスを開く
                    case CLOSED:
                        board[x][y] = State.OPENED;
                        setScore(score + 1);
                        if (boardView.searchNeighbor(x, y) == 0) {
                            autoOpen(x, y);
                        }
                        break;
                    // 爆弾を踏んだらゲームオーバー
                    case BOMB:
                        resultMenu.setScore(score);
                        resultView();
                        return;
                    default:
                        break;
                }
            }
            check();
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

// ボードの表示を行うクラス
class BoardView extends JPanel {
    // [x][y] 左上が原点
    State board[][] = new State[15][15];
    Font font = new Font("Arial", Font.PLAIN, 20);
    // ボムの表示を変えるためのフラグ
    Boolean isGameOver = false;

    BoardView() {
        super();
        // パネルのサイズを設定
        setPreferredSize(new Dimension(500, 500));
        // ダブルバッファリングを有効にする
        setDoubleBuffered(true);
    }

    // ゲームオーバー時にボムを表示するためのセッター
    public void setIsGameOver(Boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 盤面のサイズを取得
        Dimension viewSize = getSize();
        // マスのサイズを計算
        int w = viewSize.width / board.length;
        int h = viewSize.height / board[0].length;

        g.setFont(font);

        // 盤面を描画
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                switch (board[x][y]) {
                    case OPENED:
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(x * w, y * h, w, h);
                        g.setColor(Color.BLACK);
                        String bomb = String.valueOf(searchNeighbor(x, y));
                        g.drawString(bomb, x * w, (y + 1) * h);
                        break;
                    case BOMB:
                        // ゲームオーバー時にボムを表示
                        if (isGameOver) {
                            g.setColor(Color.RED);

                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(x * w, y * h, w, h);
                        break;
                    case BOMB_FLAG:
                    case FLAG:
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

    // 周囲の爆弾の数を数える
    int searchNeighbor(int x, int y) {
        int result = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int tx = x + i;
                int ty = y + j;
                // 盤面外を参照した場合は何もしない
                if (tx < 0 || tx >= board.length || ty < 0 || ty >= board[0].length) {
                    continue;
                }
                if (board[tx][ty] == State.BOMB_FLAG || board[tx][ty] == State.BOMB) {
                    result++;
                }
            }
        }
        return result;
    }

    // 盤面の更新
    void updateBoard(State[][] board) {
        this.board = board;
        repaint();
    }
}