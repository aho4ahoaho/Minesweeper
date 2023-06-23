import javax.swing.*;
import java.time.*;

public class TimerView extends JLabel implements Runnable{
    private LocalDateTime startTime;
    private Boolean isRunning = false;
    TimerView(){
        startTime = LocalDateTime.now();
        setText("00:00");
    }

    // 現在を開始時間に設定
    public void setTime(){
        setTime(LocalDateTime.now());
    }

    // 任意の開始時間を設定
    public void setTime(LocalDateTime startTime){
        this.startTime = startTime;
    }

    // 経過時間を取得
    public long getTime(){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(startTime, now);
        long time = duration.getSeconds();
        return time;
    }

    // タイマーを止める
    public void stop(){
        isRunning = false;
    }

    @Override
    public void run() {
        // タイマーを開始
        isRunning = true;
        setText("00:00");

        // フラグが立っている間は1秒ごとに経過時間を表示
        while(isRunning){
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            long time = getTime();
            long minute = time / 60;
            long second = time % 60;
            setText(String.format("%02d:%02d", minute, second));
        }
    }
}
